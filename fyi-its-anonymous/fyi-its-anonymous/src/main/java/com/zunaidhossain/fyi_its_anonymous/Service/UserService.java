package com.zunaidhossain.fyi_its_anonymous.Service;

import com.zunaidhossain.fyi_its_anonymous.Entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Map<String, String> saveUser(User user);
    Map<String, String> saveAdmin(User user);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByUniqueLink(String uniqueLink);
    String getNameByUniqueLink(String uniqueLink);
    boolean updateUser(Map<String, String> newChanges, String username);
    boolean deleteUserByUsername(String username);
}
