package com.example.notificationservice.controller;

import com.example.notificationservice.config.ToDoDTO;
import com.example.notificationservice.domain.Notification;
import com.example.notificationservice.exception.UserNotFoundException;
import com.example.notificationservice.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/notificationController")
public class NotificationController {
    private final NotificationServiceImpl notificationService;

    @Autowired
    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/getAllNotification")
    public ResponseEntity<?> getAllNotification(HttpServletRequest httpServletRequest) throws UserNotFoundException {
        Object emailIdObj = httpServletRequest.getAttribute("emailId");
        System.out.println("emailIdObj = " + emailIdObj);
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            return new ResponseEntity<>(notificationService.getAllNotification(emailId), HttpStatus.OK);
        } catch (UserNotFoundException e) {

            return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
        }
    }
}
