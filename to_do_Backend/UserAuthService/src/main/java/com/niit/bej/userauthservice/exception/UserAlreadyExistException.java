package com.niit.bej.userauthservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User Already Details present")
public class UserAlreadyExistException extends Exception {
}
