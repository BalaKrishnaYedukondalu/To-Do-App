package com.niit.bej.userauthservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String emailId;
    private String password;

    public User(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "email='" + emailId + '\'' + ", password='" + password + '\'' + '}';
    }
}
