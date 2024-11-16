package com.zunaidhossain.fyi_its_anonymous.Repository;

import com.zunaidhossain.fyi_its_anonymous.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
    Optional<User> findByUniqueLink(String uniqueLink);
}
