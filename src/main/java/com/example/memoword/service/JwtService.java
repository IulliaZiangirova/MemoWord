package com.example.memoword.service;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String generateToken(String username) {
        return "generated-jwt-token-for-" + username;
    }
}
