package com.niit.bej.userauthservice.controller;

import com.niit.bej.userauthservice.domain.User;
import com.niit.bej.userauthservice.exception.UserAlreadyExistException;
import com.niit.bej.userauthservice.exception.UserNotFoundException;
import com.niit.bej.userauthservice.security.SecurityTokenGenerator;
import com.niit.bej.userauthservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;
    private final SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserServiceImpl userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }


    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistException {
        try {
            return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);
        } catch (UserAlreadyExistException e) {
            throw new UserAlreadyExistException();
        } catch (Exception e) {
            return new ResponseEntity<>("Try after sometime!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException {
        Map<String, String> userWithToken = null;
        try {
            User newUser = userService.findByEmailAndPassword(user.getEmailId(), user.getPassword());
            System.out.println(newUser.getEmailId());
            System.out.println(user.getEmailId());
            if (newUser.getEmailId().equals(user.getEmailId()) && newUser.getPassword().equals(user.getPassword())) {
                userWithToken = securityTokenGenerator.generateToken(user);
                System.out.println("=================================================");
                System.out.println(newUser.getEmailId());
                System.out.println(user.getEmailId());
            }
            return new ResponseEntity<>(userWithToken, HttpStatus.OK);

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User Detail Is Not Found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Try after sometime!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/display")
    public String display() {
        return "Hello";
    }
}