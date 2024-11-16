package com.zunaidhossain.fyi_its_anonymous.Controller;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;
import com.zunaidhossain.fyi_its_anonymous.Service.Impl.FeedbackServiceImpl;
import com.zunaidhossain.fyi_its_anonymous.Service.Impl.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    private FeedbackServiceImpl feedbackServiceImpl;

    @GetMapping("/sayhello")
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return "Hello "+username;
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUserDetails(@RequestBody Map<String, String> newChanges) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(userServiceImpl.updateUser(newChanges, username)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getFeedbacks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Feedback> feedbacks = feedbackServiceImpl.getFeedback(authentication.getName());
        if(feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @DeleteMapping("{feedbackId}")
    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable ObjectId feedbackId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(feedbackServiceImpl.deleteFeedback(feedbackId, authentication.getName())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(userServiceImpl.deleteUserByUsername(authentication.getName())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
