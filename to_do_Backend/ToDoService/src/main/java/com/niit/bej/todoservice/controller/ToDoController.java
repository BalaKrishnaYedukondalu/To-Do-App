package com.niit.bej.todoservice.controller;

import com.niit.bej.todoservice.domain.Category;
import com.niit.bej.todoservice.domain.ToDo;
import com.niit.bej.todoservice.domain.User;
import com.niit.bej.todoservice.exception.*;
import com.niit.bej.todoservice.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/toDo")
public class ToDoController {
    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    //registering a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            System.out.println("user = " + user);

            return new ResponseEntity<>(toDoService.registerUser(user), HttpStatus.OK);
        } catch (UserAlreadyExistException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }


    //adding to-do for the user
    @PostMapping("/user/addToDo")
    public ResponseEntity<?> addToDo(HttpServletRequest httpServletRequest, @RequestBody ToDo toDo) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        toDo.setCreatedDateTime(date1);
        toDo.setUpdatedTask(date);
        System.out.println(toDo.getDueDate());
        String emailId = httpServletRequest.getAttribute("emailId").toString();

        try {
            List<ToDo> taskList = toDoService.addToDo(emailId, toDo).getToDoList();
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ToDoAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // getting all to-do from database
    @GetMapping("/user/getAllToDo")
    public ResponseEntity<?> getAllToDo(HttpServletRequest httpServletRequest) {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> taskList = toDoService.getAllToDo(emailId);
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //updating the to-do
    @PutMapping("/user/updateToDo")
    public ResponseEntity<?> updateTask(HttpServletRequest httpServletRequest, @RequestBody ToDo toDo) throws UserNotFoundException, ToDoNotFoundException {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        toDo.setUpdatedTask(date);
//        toDo.setCreatedDateTime(date);
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            User user = toDoService.updateToDo(emailId, toDo);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //deleting single to-do
    @DeleteMapping("/user/deleteToDoByTitle/{title}")
    public ResponseEntity<?> deleteTaskByTitle(HttpServletRequest httpServletRequest, @PathVariable String title) throws UserNotFoundException, ToDoNotFoundException {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            boolean isToDo = toDoService.deleteToDoByTitle(emailId, title);
            return new ResponseEntity<>(isToDo, HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //getting to-do by name
    @GetMapping("/user/getToDoByTitle/{title}")
    public ResponseEntity<?> getToDoByTitle(HttpServletRequest httpServletRequest, @PathVariable String title) throws UserNotFoundException, ToDoNotFoundException {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            ToDo newToDo = toDoService.getToDoByTitle(emailId, title);
            return new ResponseEntity<>(newToDo, HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    //
    @GetMapping("/user/getAllListByPriority/{priority}")
    public ResponseEntity<?> getAllListByPriority(HttpServletRequest httpServletRequest, @PathVariable String priority) throws UserNotFoundException {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> allListByPriority = toDoService.getAllToDoByPriority(emailId, priority);
            return new ResponseEntity<>(allListByPriority, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/getAllToDoByArchiveStatus/{isArchive}")
    public ResponseEntity<?> getToDoByArchiveStatus(@PathVariable Boolean isArchive, HttpServletRequest httpServletRequest) {

        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> userTaskList = toDoService.getToDoListByArchivedStatus(emailId, isArchive);
            return new ResponseEntity<>(userTaskList, HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/updateTaskArchiveStatus")
    public ResponseEntity<?> updateTaskAsArchiveTask(HttpServletRequest httpServletRequest, @RequestBody ToDo toDo) {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> toDOList = toDoService.updateTaskAsArchiveStatus(emailId, toDo).getToDoList();
            return new ResponseEntity<>(toDOList, HttpStatus.OK);
        } catch (ToDoNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/getToDoByImportantStatus/{isCompleted}")
    public ResponseEntity<?> getToDoByImportantStatus(@PathVariable Boolean isCompleted, HttpServletRequest httpServletRequest) {

        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> userTaskList = toDoService.getToDoByImportantStatus(emailId, isCompleted);
            return new ResponseEntity<>(userTaskList, HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/updateToDoAsImportantTask")
    public ResponseEntity<?> updateToDoAsImportantTask(HttpServletRequest httpServletRequest, @RequestBody ToDo toDo) {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> toDOList = toDoService.updateToDoAsImportantTask(emailId, toDo).getToDoList();
            return new ResponseEntity<>(toDOList, HttpStatus.OK);
        } catch (ToDoNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("/user/updateToDoAsCompletedTask")
    public ResponseEntity<?> updateTaskAsCompletedTask(@RequestBody ToDo todo, HttpServletRequest httpServletRequest) {

        String emailId = httpServletRequest.getAttribute("emailId").toString();

        try {
            List<ToDo> taskList = toDoService.updateToDoAsCompletedTask(emailId, todo).getToDoList();
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (ToDoNotFoundException e) {
            return new ResponseEntity<>("To-Do Not Matching", HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/getAllToDoByStatus/{isCompleted}")
    public ResponseEntity<?> getTaskByCompletionStatus(HttpServletRequest httpServletRequest, @PathVariable Boolean isCompleted) {

        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> userTaskList = toDoService.getToDoByCompletionStatus(emailId, isCompleted);
            return new ResponseEntity<>(userTaskList, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        } catch (ToDoNotFoundException todoNotFoundException) {
            return new ResponseEntity<>("ToDo Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/deleteAllToDo")
    public ResponseEntity<?> deleteAllToDo(HttpServletRequest httpServletRequest) throws UserNotFoundException, ToDoNotFoundException {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            boolean isToDo = toDoService.deleteAllToDo(emailId);
            return new ResponseEntity<>(isToDo, HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/getUserDetails")
    public ResponseEntity<?> getUserDetails(HttpServletRequest httpServletRequest) throws UserNotFoundException {

        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            return new ResponseEntity<>(toDoService.getUserDetails(emailId), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/getToDoByDate")
    public ResponseEntity<?> getToDoByDate(HttpServletRequest httpServletRequest) {

        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            return new ResponseEntity<>(toDoService.getAllToDoByDate(emailId), HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/updateToDoAsTravelTask")
    public ResponseEntity<?> updateToDoAsTravelTask(HttpServletRequest httpServletRequest, @RequestBody ToDo toDo) {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> toDOList = toDoService.updateToDoAsTravelTask(emailId, toDo).getToDoList();
            return new ResponseEntity<>(toDOList, HttpStatus.OK);
        } catch (ToDoNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/user/getToDoByTravelStatus/{isCompleted}")
    public ResponseEntity<?> getToDoByTravelStatus(@PathVariable Boolean isCompleted, HttpServletRequest httpServletRequest) {

        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> userTaskList = toDoService.getToDoByTravelStatus(emailId, isCompleted);
            return new ResponseEntity<>(userTaskList, HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/updateToDoAsPersonalTask")
    public ResponseEntity<?> updateToDoAsPersonalTask(HttpServletRequest httpServletRequest, @RequestBody ToDo toDo) {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> toDOList = toDoService.updateToDoAsPersonalTask(emailId, toDo).getToDoList();
            return new ResponseEntity<>(toDOList, HttpStatus.OK);
        } catch (ToDoNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/user/getToDoByPersonalStatus/{isCompleted}")
    public ResponseEntity<?> getToDoByPersonalStatus(@PathVariable Boolean isCompleted, HttpServletRequest httpServletRequest) {

        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<ToDo> userTaskList = toDoService.getToDoByPersonalStatus(emailId, isCompleted);
            return new ResponseEntity<>(userTaskList, HttpStatus.OK);
        } catch (UserNotFoundException | ToDoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/user/createCategory")
    public ResponseEntity<?> addCategoryToUser(@RequestBody Category category, HttpServletRequest httpServletRequest) throws UserNotFoundException, CategoryAlreadyExistException {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        System.out.println("emailId ::" + emailId);
        try {
            List<Category> categoryList = toDoService.CreateCategorylist(category, emailId).getCategories();
            return new ResponseEntity<>(categoryList, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CategoryAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("user/getAllCategory")
    public ResponseEntity<?> getAllCategory(HttpServletRequest httpServletRequest) throws UserNotFoundException, CategoryNotFoundException {
        String emailId = httpServletRequest.getAttribute("emailId").toString();
        try {
            List<Category> categoryList = toDoService.getAllCatergory(emailId);
            return new ResponseEntity<>(categoryList, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("user/addTodoToUserCategory/{title}")
    public ResponseEntity<?> addTodoToUserCategory(@PathVariable String title, HttpServletRequest request,@RequestBody ToDo toDo) throws ToDoAlreadyExistException {
        try {

            String emailId = request.getAttribute("emailId").toString();
            Category category = toDoService.saveUserTodoToCategory(title, emailId, toDo);
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        } catch (UserNotFoundException | ToDoNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @GetMapping("user/getAllCatergoryByToDoList/{title}")
    public ResponseEntity<?> getAllCatergoryByToDoList(HttpServletRequest request,@PathVariable String title) throws UserNotFoundException, CategoryNotFoundException {
        String emailId = request.getAttribute("emailId").toString();
        return new ResponseEntity<>(toDoService.getAllCatergoryByToDoList(emailId,title),HttpStatus.OK);
    }
}










