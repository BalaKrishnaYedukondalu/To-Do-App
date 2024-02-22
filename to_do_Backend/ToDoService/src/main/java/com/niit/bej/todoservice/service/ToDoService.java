package com.niit.bej.todoservice.service;

import com.niit.bej.todoservice.domain.Category;
import com.niit.bej.todoservice.domain.ToDo;
import com.niit.bej.todoservice.domain.User;
import com.niit.bej.todoservice.exception.*;

import java.util.List;

public interface ToDoService {
    User registerUser(User user) throws UserAlreadyExistException;

    User addToDo(String emailId, ToDo toDo) throws UserNotFoundException, ToDoAlreadyExistException;

    List<ToDo> getAllToDo(String emailId) throws UserNotFoundException, ToDoNotFoundException;

    User updateToDo(String emailId, ToDo toDo) throws UserNotFoundException, ToDoNotFoundException;

    boolean deleteToDoByTitle(String emailId, String title) throws UserNotFoundException, ToDoNotFoundException;

    ToDo getToDoByTitle(String emailId, String title) throws UserNotFoundException, ToDoNotFoundException;

    List<ToDo> getAllToDoByPriority(String emailId, String priority) throws UserNotFoundException;

    List<ToDo> getToDoListByArchivedStatus(String emailId, boolean isArchive) throws UserNotFoundException, ToDoNotFoundException;

    User updateTaskAsArchiveStatus(String emailId, ToDo toDo) throws UserNotFoundException, ToDoNotFoundException;

    List<ToDo> getToDoByCompletionStatus(String emailId, Boolean isCompleted) throws ToDoNotFoundException, UserNotFoundException;

    User updateToDoAsCompletedTask(String emailId, ToDo toDo) throws ToDoNotFoundException, UserNotFoundException;

    List<ToDo> getToDoByImportantStatus(String emailId, Boolean isCompleted) throws ToDoNotFoundException, UserNotFoundException;

    User updateToDoAsImportantTask(String emailId, ToDo toDo) throws ToDoNotFoundException, UserNotFoundException;

    boolean deleteAllToDo(String emailId) throws UserNotFoundException, ToDoNotFoundException;

    User getUserDetails(String emailId) throws UserNotFoundException;

    List<ToDo> getAllToDoByDate(String emailId) throws UserNotFoundException, ToDoNotFoundException;

    User updateToDoAsTravelTask(String emailId, ToDo toDo) throws ToDoNotFoundException, UserNotFoundException;

    List<ToDo> getToDoByTravelStatus(String emailId, Boolean isCompleted) throws ToDoNotFoundException, UserNotFoundException;

    List<ToDo> getToDoByPersonalStatus(String emailId, Boolean isCompleted) throws ToDoNotFoundException, UserNotFoundException;

    User updateToDoAsPersonalTask(String emailId, ToDo toDo) throws ToDoNotFoundException, UserNotFoundException;

    User CreateCategorylist (Category category, String emailId) throws UserNotFoundException, CategoryAlreadyExistException;
    List<Category> getAllCatergory(String emailId) throws UserNotFoundException;

    Category saveUserTodoToCategory(String title, String emailId, ToDo toDo) throws UserNotFoundException, ToDoNotFoundException, ToDoAlreadyExistException;
    public List<Category> getAllCatergoryByToDoList(String emailId, String title)throws UserNotFoundException, CategoryNotFoundException;


}
