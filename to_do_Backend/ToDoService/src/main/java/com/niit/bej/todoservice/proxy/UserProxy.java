package com.niit.bej.todoservice.proxy;

import com.niit.bej.todoservice.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "UserService", url = "localhost:8085")
public interface UserProxy {
    @RequestMapping("/user/registerUser")
    ResponseEntity<?> saveUser(@RequestBody User user);
}



