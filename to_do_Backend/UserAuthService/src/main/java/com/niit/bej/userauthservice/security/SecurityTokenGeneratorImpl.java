package com.niit.bej.userauthservice.security;

import com.niit.bej.userauthservice.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class SecurityTokenGeneratorImpl implements SecurityTokenGenerator{
    @Override
    public Map<String, String> generateToken(User user) {
        Map<String,String> userToken = new HashMap<>();
        String jwtToken = Jwts.builder().setSubject(user.getEmailId())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "todo").compact();
        userToken.put("jwtToken",jwtToken);
        userToken.put("message","User Successfully logged in");
        userToken.forEach((k,v)-> System.out.println(k+" "+v));
        return userToken;
    }
}
