package com.niit.bej.userauthservice.service;

import com.niit.bej.userauthservice.domain.User;
import com.niit.bej.userauthservice.exception.UserAlreadyExistException;
import com.niit.bej.userauthservice.exception.UserNotFoundException;
import com.niit.bej.userauthservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistException {
        if (userRepository.findById(user.getEmailId()).isPresent()) {
            throw new UserAlreadyExistException();
        } else {
            return userRepository.save(user);
        }
    }

    @Override
    public User findByEmailAndPassword(String emailId, String password) throws UserNotFoundException {
        User user = userRepository.findByEmailIdAndPassword(emailId, password);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException();
        }

    }
}
