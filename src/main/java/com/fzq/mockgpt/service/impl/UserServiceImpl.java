package com.fzq.mockgpt.service.impl;

import com.fzq.mockgpt.entity.User;
import com.fzq.mockgpt.repository.UserRepository;
import com.fzq.mockgpt.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
