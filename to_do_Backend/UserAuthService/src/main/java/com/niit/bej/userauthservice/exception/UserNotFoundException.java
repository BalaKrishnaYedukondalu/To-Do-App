package com.niit.bej.userauthservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Details Not Found")
public class UserNotFoundException extends Exception {

}

