package com.zunaidhossain.fyi_its_anonymous.Scheduler;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;
import com.zunaidhossain.fyi_its_anonymous.Entity.User;
import com.zunaidhossain.fyi_its_anonymous.Repository.FeedbackRepository;
import com.zunaidhossain.fyi_its_anonymous.Repository.FeedbackRepositoryImpl;
import com.zunaidhossain.fyi_its_anonymous.Repository.UserRepositoryImpl;
import com.zunaidhossain.fyi_its_anonymous.Service.EmailService;
import jakarta.mail.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class UnreadMailScheduler {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private FeedbackRepositoryImpl feedbackRepository;

    @Autowired
    private EmailService emailService;

    // @Scheduled(cron = "0 */5 * * * *") // Trigger every 5 minutes
    public void sendUnreadMessageEmails() {
        // Replace with your logic to fetch users
        List<User> validUsers = userRepository.getUserWithValidEmail();

        for (User user : validUsers) {
            // Fetch unread messages for each user
            List<Feedback> unreadMessages = feedbackRepository.countOfUnreadMessages(user.getUsername());

            if (!unreadMessages.isEmpty()) {
                // Generate email content
                String content = emailService.createEmailContent(unreadMessages, user.getName());
                // Send email
                emailService.sendEmail(user.getEmail(),
                        unreadMessages.size()+" unread feedbacks! Check ASAP!", content);
            }
        }
    }
}
