package com.niit.bej.todoservice.exception;

public class ToDoAlreadyExistException extends Exception{
    public ToDoAlreadyExistException(String message) {
        super(message);
    }
}
