package com.niit.bej.userauthservice.service;

import com.niit.bej.userauthservice.domain.User;
import com.niit.bej.userauthservice.exception.UserAlreadyExistException;
import com.niit.bej.userauthservice.exception.UserNotFoundException;

public interface UserService {
    User registerUser(User user) throws UserAlreadyExistException;

    User findByEmailAndPassword(String emailId, String password) throws UserNotFoundException;

}
