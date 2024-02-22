package com.niit.bej.todoservice.service;

import com.niit.bej.todoservice.domain.Category;
import com.niit.bej.todoservice.domain.ToDo;
import com.niit.bej.todoservice.domain.User;
import com.niit.bej.todoservice.exception.ToDoAlreadyExistException;
import com.niit.bej.todoservice.exception.ToDoNotFoundException;
import com.niit.bej.todoservice.exception.UserAlreadyExistException;
import com.niit.bej.todoservice.exception.UserNotFoundException;
import com.niit.bej.todoservice.proxy.UserProxy;
import com.niit.bej.todoservice.repository.ToDoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @Mock
    private ToDoRepository toDoToDoRepository;

    @Mock
    private UserProxy proxy;

    @InjectMocks
    private ToDoServiceImpl todoService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private DirectExchange directExchange;

    private User user1, user2;
    private ToDo ToDo1, ToDo2;
    private List<ToDo> ToDoList;
    private List<Category> categories;

    @BeforeEach
    void setUp() {
        ToDo1 = new ToDo("todo1", "todo ", "img", "10-09-23", "high", "", true, true, true, "", false, false, false);
        ToDo2 = new ToDo("todo2", "todo ", "xyz", "11-09-23", "high", "", true, true, true, "", false, false, false);
        ToDoList = Arrays.asList(ToDo1, ToDo2);
        user1 = new User("bala@gmail.com", "krishna", "bala@gmail.com", "1234567890", "profileImg.url", "", ToDoList,categories);
        user2 = new User("gdc@gmail.com", "chandel", "gdcchandel", "787373678", "profileImg.url", "", ToDoList,categories);
    }

    @AfterEach
    void tearDown() {
        user1 = null;
        user2 = null;
    }

    @Test
    public void testRegisterUserSuccess() throws UserAlreadyExistException {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        when(toDoToDoRepository.save(user1)).thenReturn(user1);

//        when(proxy.saveUser(user1)).thenReturn(ResponseEntity.ofNullable(null));
        todoService = new ToDoServiceImpl(toDoToDoRepository, proxy, null);

        assertEquals(user1, todoService.registerUser(user1));
        verify(toDoToDoRepository, times(1)).save(user1);
        verify(proxy, times(1)).saveUser(user1);
    }

    @Test
    public void testRegisterUserFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.ofNullable(user1));
        assertThrows(UserAlreadyExistException.class, () -> todoService.registerUser(user1));
        verify(toDoToDoRepository, times(1)).findById(any());
        verify(toDoToDoRepository, times(0)).save(any());
    }

    @Test
    public void testAddToDoSuccess() throws ToDoAlreadyExistException, UserNotFoundException {
        String emailId = "test@example.com";
        ToDo ToDo3 = new ToDo("todo3", "todo ", "img", "10-09-23", "high", "", true, true, true, "", false, false, false);

        User existingUser = new User();
        List<ToDo> existingToDos = new ArrayList<>();
        existingToDos.add(ToDo1);
        existingUser.setToDoList(existingToDos);

        when(toDoToDoRepository.findById(emailId)).thenReturn(Optional.of(existingUser));
        when(toDoToDoRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = todoService.addToDo(emailId, ToDo3);

        assertEquals(existingUser, updatedUser);
        assertEquals(2, existingUser.getToDoList().size());
        assertTrue(existingUser.getToDoList().contains(ToDo3));
        verify(toDoToDoRepository, times(2)).findById(emailId);
        verify(toDoToDoRepository, times(1)).save(existingUser);
    }

    @Test
    public void testAddToDoFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.addToDo(user1.getEmailId(), ToDo1));
        verify(toDoToDoRepository, times(1)).findById(any());
        verify(toDoToDoRepository, times(0)).save(any());
    }


    @Test
    void testGetAllToDoFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.getAllToDo(user1.getEmailId()));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }

    @Test
    void testGetToDoByPrioritySuccess() throws ToDoNotFoundException, UserNotFoundException {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.of(user1));
        assertEquals(ToDo1, todoService.getAllToDoByPriority(user1.getEmailId(), "high").get(0));
        verify(toDoToDoRepository, times(2)).findById(user1.getEmailId());
    }

    @Test
    void testGetToDoByPriorityFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.getAllToDoByPriority(user1.getEmailId(), "low"));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }




    @Test
    void testGetToDoByCompletionStatusFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.getToDoByCompletionStatus(user1.getEmailId(), false));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }

    @Test
    void testGetToDoByTitleSuccess() throws ToDoNotFoundException, UserNotFoundException {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.of(user1));
        assertEquals(ToDo1, todoService.getToDoByTitle(user1.getEmailId(), ToDo1.getTitle()));
        verify(toDoToDoRepository, times(2)).findById(user1.getEmailId());
    }

    @Test
    void testGetToDoByTitleFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.getToDoByTitle(user1.getEmailId(), ToDo1.getTitle()));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }

    @Test
    public void testDeleteAllToDoSuccess() throws ToDoNotFoundException, UserNotFoundException {
        String emailId = "test@example.com";
        User existingUser = new User();
        List<ToDo> existingTasks = new ArrayList<>();
        existingTasks.add(ToDo1);
        existingUser.setToDoList(existingTasks);

        when(toDoToDoRepository.findById(emailId)).thenReturn(Optional.of(existingUser));
        when(toDoToDoRepository.save(any(User.class))).thenReturn(existingUser);

        boolean updatedUser = todoService.deleteAllToDo(emailId);
        assertTrue(existingUser.getToDoList().isEmpty());
    }

    @Test
    public void testDeleteAllToDoFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.deleteAllToDo(user1.getEmailId()));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }


    @Test
    public void testUpdateToDoSuccess() throws UserNotFoundException, ToDoNotFoundException {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.of(user1));
        ToDo ToDo3 = new ToDo("todo3", "todo ", "img", "1-09-23", "high", "", true, true, true, "", false, false, false);
        User updatedUser = todoService.updateToDo(user1.getEmailId(), ToDo1);
        assertEquals(user1.getToDoList().get(1).getDescription(), ToDo3.getDescription());
        assertEquals(user1.getToDoList().get(1).getPriority(), ToDo3.getPriority());
        verify(toDoToDoRepository, times(2)).findById(user1.getEmailId());
    }

    @Test
    public void testUpdateToDoFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.updateToDo(user1.getEmailId(), ToDo1));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }




    @Test
    public void testUpdateToDoAsCompletedToDoFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.updateToDoAsCompletedTask(user1.getEmailId(), ToDo1));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }

    @Test
    void testGetToDoByArchivedStatusSuccess() throws ToDoNotFoundException, UserNotFoundException {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.of(user1));
        List<ToDo> ToDoList1 = todoService.getToDoListByArchivedStatus(user1.getEmailId(), true);
        assertEquals(ToDo1, ToDoList1.get(0));
        verify(toDoToDoRepository, times(2)).findById(user1.getEmailId());
    }

    @Test
    void testGetToDoByArchivedStatusFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.getToDoListByArchivedStatus(user1.getEmailId(), false));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }

    @Test
    public void testUpdateToDoAsArchiveStatusSuccess() throws ToDoNotFoundException, UserNotFoundException {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.of(user1));
        when(toDoToDoRepository.save(user1)).thenReturn(user1);
        User updatedUser = todoService.updateTaskAsArchiveStatus(user1.getEmailId(), ToDo2);
        assertTrue(updatedUser.getToDoList().get(1).isCompleted());
        verify(toDoToDoRepository, times(2)).findById(user1.getEmailId());
    }

    @Test
    public void testUpdateToDoAsArchiveStatusFailure() {
        when(toDoToDoRepository.findById(user1.getEmailId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.updateTaskAsArchiveStatus(user1.getEmailId(), ToDo1));
        verify(toDoToDoRepository, times(1)).findById(user1.getEmailId());
    }


}
