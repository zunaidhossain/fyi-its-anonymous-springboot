package com.zunaidhossain.fyi_its_anonymous.Repository;


import com.zunaidhossain.fyi_its_anonymous.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> getUserWithValidEmail() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        return mongoTemplate.find(query, User.class);
    }

}
