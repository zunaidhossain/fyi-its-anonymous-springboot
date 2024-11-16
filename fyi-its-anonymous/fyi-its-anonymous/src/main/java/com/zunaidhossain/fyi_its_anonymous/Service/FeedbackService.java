package com.zunaidhossain.fyi_its_anonymous.Service;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;
import org.bson.types.ObjectId;

import java.util.List;

public interface FeedbackService {
    boolean saveFeedback(Feedback feedback, String unique_link);
    List<Feedback> getFeedback(String username);
    boolean deleteFeedback(ObjectId feedbackId, String username);
}
