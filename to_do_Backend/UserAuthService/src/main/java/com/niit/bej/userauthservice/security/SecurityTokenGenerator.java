package com.niit.bej.userauthservice.security;

import com.niit.bej.userauthservice.domain.User;

import java.util.Map;

public interface SecurityTokenGenerator {
    Map<String, String> generateToken(User user);
}
