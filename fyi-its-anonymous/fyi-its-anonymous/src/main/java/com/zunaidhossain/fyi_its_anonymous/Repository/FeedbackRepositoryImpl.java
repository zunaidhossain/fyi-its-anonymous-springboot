package com.zunaidhossain.fyi_its_anonymous.Repository;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;
import com.zunaidhossain.fyi_its_anonymous.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class FeedbackRepositoryImpl {
    @Autowired
    MongoTemplate mongoTemplate;
    public List<Feedback> countOfUnreadMessages(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        query.addCriteria(Criteria.where("read_status").is("Unread"));
        return mongoTemplate.find(query, Feedback.class);
    }
}
