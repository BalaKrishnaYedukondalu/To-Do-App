package com.niit.bej.todoservice.repository;

import com.niit.bej.todoservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends MongoRepository<User, String> {

}
