package com.example.notificationservice.service;

import com.example.notificationservice.config.ToDoDTO;
import com.example.notificationservice.domain.Notification;
import com.example.notificationservice.exception.UserNotFoundException;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification getAllNotification(String emailId) throws UserNotFoundException {
        if (notificationRepository.findById(emailId).isPresent()) {
            return notificationRepository.findById(emailId).get();
        } else {
            throw new UserNotFoundException("User not Found");
        }
    }

    @RabbitListener(queues = "todo_queue")
    @Override
    public void saveNotification(ToDoDTO toDoDTO) throws UserNotFoundException {
        Notification notification = new Notification();
        String emailId = toDoDTO.getJsonObject().get("emailId").toString();
        System.out.println(emailId);
        notification.setEmailId(emailId);
        notification.setMessage("notificationToDoList");
        notification.setJsonObject(toDoDTO.getJsonObject());
        notificationRepository.save(notification);
    }
}
