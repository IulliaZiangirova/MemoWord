package com.example.memoword.service;

import com.example.memoword.dto.request.AuthRequest;
import com.example.memoword.dto.request.RegisterRequest;
import com.example.memoword.dto.response.AuthResponse;

public interface AuthService {

    void register(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
}
