package com.zunaidhossain.fyi_its_anonymous.Service.Impl;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;
import com.zunaidhossain.fyi_its_anonymous.Entity.User;
import com.zunaidhossain.fyi_its_anonymous.Repository.FeedbackRepository;
import com.zunaidhossain.fyi_its_anonymous.Repository.UserRepository;
import com.zunaidhossain.fyi_its_anonymous.Service.FeedbackService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserServiceImpl userServiceImpl, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userServiceImpl = userServiceImpl;
        this.userRepository = userRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean saveFeedback(Feedback feedback, String unique_link) {
        Optional<User> user = userServiceImpl.getUserByUniqueLink(unique_link);

        if(user.isPresent()) {
            feedback.setSubmittedAt(LocalDateTime.now());
            feedback.setRead_status("Unread");
            feedback.setUsername(user.get().getUsername());
            Feedback savedFeedback = feedbackRepository.save(feedback);
            user.get().getFeedbacks().add(savedFeedback);
            userRepository.save(user.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Feedback> getFeedback(String username) {
        List<Feedback> feedbacks = feedbackRepository.findByUsername(username);
        List<Feedback> readFeedbacks = feedbackRepository.findByUsername(username);
        for (Feedback feedback : readFeedbacks) {
            feedback.setRead_status("Read");
            feedbackRepository.save(feedback);
        }
        return feedbacks;
    }

    @Override
    public boolean deleteFeedback(ObjectId feedbackId, String username) {
        boolean removed = false;
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if(user.isPresent()) {
                removed = user.get().getFeedbacks().removeIf(x -> x.getId().equals(feedbackId));
                if(removed) {
                    userRepository.save(user.get());
                    feedbackRepository.deleteById(feedbackId);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the entry "+e);
        }
        return removed;
    }
}
