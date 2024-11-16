package com.zunaidhossain.fyi_its_anonymous.Repository;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback, ObjectId> {
    List<Feedback> findByUsername(String username);
    void deleteByUsername(String username);
}
