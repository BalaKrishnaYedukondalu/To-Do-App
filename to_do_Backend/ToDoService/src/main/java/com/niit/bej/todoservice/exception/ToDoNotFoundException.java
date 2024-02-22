package com.niit.bej.todoservice.exception;

public class ToDoNotFoundException extends Exception{
    public ToDoNotFoundException(String message) {
        super(message);
    }
}
