package com.zunaidhossain.fyi_its_anonymous.Controller;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;
import com.zunaidhossain.fyi_its_anonymous.Entity.User;
import com.zunaidhossain.fyi_its_anonymous.Service.Impl.FeedbackServiceImpl;
import com.zunaidhossain.fyi_its_anonymous.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/home")
public class PublicController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    FeedbackServiceImpl feedbackServiceImpl;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        Map<String, String> userDetails = userServiceImpl.saveUser(user);
        if(!userDetails.isEmpty())
            return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(userDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{uniqueLink}")
    public ResponseEntity<String> findUserByUniqueLink(@PathVariable String uniqueLink) {
        String name = userServiceImpl.getNameByUniqueLink(uniqueLink);
        if (name != null) {
            return new ResponseEntity<>(name, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("User does not exists", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{uniqueLink}")
    public ResponseEntity<HttpStatus> saveFeedback(@RequestBody Feedback feedback, @PathVariable String uniqueLink) {
        if(feedbackServiceImpl.saveFeedback(feedback, uniqueLink))
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
