package com.niit.bej.userauthservice.repository;

import com.niit.bej.userauthservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmailIdAndPassword(String emailId, String password);
}
