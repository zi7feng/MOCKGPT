package com.fzq.mockgpt.service;

import com.fzq.mockgpt.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
}
