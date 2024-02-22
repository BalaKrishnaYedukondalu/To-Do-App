/*
 * Author Name : Pratyush Kumar
 * Date: 07-06-2023
 * Created With: IntelliJ IDEA Community Edition
 */
package com.niit.bej.userauthservice.service;

import com.niit.bej.userauthservice.domain.User;
import com.niit.bej.userauthservice.exception.UserAlreadyExistException;
import com.niit.bej.userauthservice.exception.UserNotFoundException;
import com.niit.bej.userauthservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1, user2;

    @BeforeEach
    void setUp() {
        user1 = new User("gdc@gmail.com", "gdc@@12");
        user2 = new User("bala@gmail.com", "bala@12");
    }

    @AfterEach
    void tearDown() {
        user1 = null;
        user2 = null;
    }

    @Test
    public void testRegisterUserSuccess() throws UserAlreadyExistException {
        when(userRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(user1, userService.registerUser(user1));
        verify(userRepository, times(1)).save(user1);
        verify(userRepository, times(1)).findById(user1.getEmailId());
    }

    @Test
    public void testRegisterUserFailure() {
        when(userRepository.findById(user1.getEmailId())).thenReturn(Optional.ofNullable(user1));
        assertThrows(UserAlreadyExistException.class, ()-> userService.registerUser(user1));
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testLoginUserSuccess() throws UserNotFoundException {
        when(userRepository.findByEmailIdAndPassword(user1.getEmailId(), user1.getPassword())).thenReturn(user1);
        assertEquals(user1, userService.findByEmailAndPassword(user1.getEmailId(), user1.getPassword()));
        verify(userRepository, times(1)).findByEmailIdAndPassword(user1.getEmailId(), user1.getPassword());
    }

    @Test
    void testLoginUserFailure() {
        when(userRepository.findByEmailIdAndPassword(user1.getEmailId(), user1.getPassword())).thenReturn(null);
        assertThrows(UserNotFoundException.class, ()-> userService.findByEmailAndPassword(user1.getEmailId(), user1.getPassword()));
        verify(userRepository, times(1)).findByEmailIdAndPassword(user1.getEmailId(), user1.getPassword());
    }
}
