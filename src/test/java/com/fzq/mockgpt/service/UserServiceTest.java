package com.fzq.mockgpt.service;

import com.fzq.mockgpt.entity.User;
import com.fzq.mockgpt.repository.UserRepository;
import com.fzq.mockgpt.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setEmail("user1@example.com");
        User user2 = new User();
        user2.setEmail("user2@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1@example.com", result.get(0).getEmail());
        assertEquals("user2@example.com", result.get(1).getEmail());
        verify(userRepository, times(1)).findAll();
    }
}
