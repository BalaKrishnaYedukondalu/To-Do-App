package com.niit.bej.todoservice.controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.bej.todoservice.controller.ToDoController;
import com.niit.bej.todoservice.domain.Category;
import com.niit.bej.todoservice.domain.ToDo;
import com.niit.bej.todoservice.domain.User;
import com.niit.bej.todoservice.exception.ToDoAlreadyExistException;
import com.niit.bej.todoservice.exception.ToDoNotFoundException;
import com.niit.bej.todoservice.exception.UserAlreadyExistException;
import com.niit.bej.todoservice.service.ToDoServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class controllerTest {

    @Mock
    private ToDoServiceImpl todoService;

    @InjectMocks
    private ToDoController todoController;

    @Mock
    private HttpServletRequest httpServletRequest;


    private MockMvc mockMvc;

    private User user1, user2;
    private ToDo todo1, todo2;
    private List<ToDo> todoList;
    private List<Category> categories;

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "JSON processing error";
        }
        return result;
    }

    @BeforeEach
    void setUp() {
        todo1 = new ToDo("todo1", "todo ", "img", "10-09-23", "high", "", true, true, true, "", false, false, false);
        todo2 = new ToDo("todo2", "todo ", "xyz", "11-09-23", "high", "", true, true, true, "", false, false, false);
        todoList = Arrays.asList(todo1, todo2);
        user1 = new User("bala@gmail.com", "krishna", "bala@gmail.com", "1234567890", "profileImg.url", "", todoList,categories);
        user2 = new User("gdc@gmail.com", "chandel", "gdcchandel", "787373678", "profileImg.url", "", todoList,categories);
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @AfterEach
    void tearDown() {
        user1 = null;
        user2 = null;
    }

    @Test
    void registerUserForSuccess() throws Exception {
        when(todoService.registerUser(user1)).thenReturn(user1);
        mockMvc.perform(post("/toDo/register").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user1))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(todoService, times(1)).registerUser(any());
    }

    @Test
    void registerUserForFailure() throws Exception {
        when(todoService.registerUser(any())).thenThrow(UserAlreadyExistException.class);
        mockMvc.perform(post("/toDo/register").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user1))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(todoService, times(1)).registerUser(any());
    }

    @Test
    public void addToDoForSuccess() throws Exception {
        when(todoService.addToDo(eq(user1.getEmailId()), any(ToDo.class))).thenReturn(user1);

        mockMvc.perform(post("/toDo/user/addToDo").contentType(MediaType.APPLICATION_JSON).content(jsonToString(todo1)).requestAttr("emailId", user1.getEmailId())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(todoService, times(1)).addToDo(any(), any());
    }

    @Test
    void addToDoForFailure() throws Exception {
        when(todoService.addToDo(eq(user1.getEmailId()), any(ToDo.class))).thenThrow(ToDoAlreadyExistException.class);
        mockMvc.perform(post("/toDo/user/addToDo").contentType(MediaType.APPLICATION_JSON).content(jsonToString(todo1)).requestAttr("emailId", user1.getEmailId())).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(todoService, times(1)).addToDo(any(), any());
    }

    @Test
    void getAllToDoForSuccess() throws Exception {
        when(todoService.getAllToDo(user1.getEmailId())).thenReturn(user1.getToDoList());

        mockMvc.perform(get("/toDo/user/getAllToDo").requestAttr("emailId", user1.getEmailId())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(todoService, times(1)).getAllToDo(any());
    }

    @Test
    void getAllToDoForFailure() throws Exception {
        when(todoService.getAllToDo(user1.getEmailId())).thenThrow(ToDoNotFoundException.class);

        mockMvc.perform(get("/toDo/user/getAllToDo").requestAttr("emailId", user1.getEmailId())).andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(todoService, times(1)).getAllToDo(any());
    }


}
