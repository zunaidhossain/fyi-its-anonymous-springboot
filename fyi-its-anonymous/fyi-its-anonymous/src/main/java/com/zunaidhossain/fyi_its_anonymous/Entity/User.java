package com.zunaidhossain.fyi_its_anonymous.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    private String email;
    @NonNull
    private String password;
    private String name;
    private int age;
    private String uniqueLink;
    private LocalDateTime createdTimestamp;
    private LocalDateTime lastModified;
    @DBRef
    private List<Feedback> feedbacks = new ArrayList<>();
    private List<String> roles;
}
