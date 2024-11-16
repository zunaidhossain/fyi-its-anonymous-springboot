package com.zunaidhossain.fyi_its_anonymous.Service.Impl;

import com.zunaidhossain.fyi_its_anonymous.Config.Base62Util;
import com.zunaidhossain.fyi_its_anonymous.Entity.User;
import com.zunaidhossain.fyi_its_anonymous.Repository.FeedbackRepository;
import com.zunaidhossain.fyi_its_anonymous.Repository.UserRepository;
import com.zunaidhossain.fyi_its_anonymous.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FeedbackRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Map<String, String> saveUser(User user) {
        Map<String, String> userDetails = new HashMap<>();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            user.setUniqueLink(Base62Util.generateUniqueLink());
            user.setCreatedTimestamp(LocalDateTime.now());
            user.setLastModified(LocalDateTime.now());
            if (user.getUniqueLink().isBlank())
                throw new RuntimeException("Unique link is blank");
            User savedUser = userRepository.save(user);
            userDetails.put("name", savedUser.getName());
            userDetails.put("uniqueLink", user.getUniqueLink());
            userDetails.put("username", savedUser.getUsername());
            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return userDetails;
        }
    }

    @Override
    public Map<String, String> saveAdmin(User user) {
        Map<String, String> userDetails = new HashMap<>();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER", "ADMIN"));
            user.setUniqueLink(Base62Util.generateUniqueLink());
            user.setCreatedTimestamp(LocalDateTime.now());
            user.setLastModified(LocalDateTime.now());
            if (user.getUniqueLink().isBlank())
                throw new RuntimeException("Unique link is blank");
            User savedUser = userRepository.save(user);
            userDetails.put("name", savedUser.getName());
            userDetails.put("uniqueLink", user.getUniqueLink());
            userDetails.put("username", savedUser.getUsername());
            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return userDetails;
        }
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByUniqueLink(String uniqueLink) {
        return userRepository.findByUniqueLink(uniqueLink);
    }

    @Override
    public String getNameByUniqueLink(String uniqueLink) {
        Optional<User> user = userRepository.findByUniqueLink(uniqueLink);
        return user.map(User::getName).orElse(null);
    }

    @Override
    public boolean updateUser(Map<String, String> newChanges, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if(newChanges.containsKey("name")) {
                user.get().setName(newChanges.get("name"));
            }
            if (newChanges.containsKey("email")) {
                user.get().setEmail(newChanges.get("email"));
            }
            if (newChanges.containsKey("password")) {
                user.get().setPassword(passwordEncoder.encode(newChanges.get("password")));
            }
            if (newChanges.containsKey("age")) {
                user.get().setAge(Integer.parseInt(newChanges.get("age")));
            }
            user.get().setLastModified(LocalDateTime.now());
            userRepository.save(user.get());
        }
        return true;
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        try {
            userRepository.deleteByUsername(username);
            feedbackRepository.deleteByUsername(username);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
