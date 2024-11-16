package com.zunaidhossain.fyi_its_anonymous.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "feedbacks")
public class Feedback {
    @Id
    private ObjectId id;
    private String msg;
    private String username;
    private LocalDateTime submittedAt;
    private String read_status;
}
