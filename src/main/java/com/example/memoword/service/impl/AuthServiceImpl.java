package com.example.memoword.service.impl;

import com.example.memoword.dto.request.AuthRequest;
import com.example.memoword.dto.request.RegisterRequest;
import com.example.memoword.dto.response.AuthResponse;
import com.example.memoword.entity.User;
import com.example.memoword.exception.UserAlreadyExistsException;
import com.example.memoword.exception.UserNotExistException;
import com.example.memoword.exception.WrongPasswordException;
import com.example.memoword.repository.UserRepository;
import com.example.memoword.service.AuthService;
import com.example.memoword.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + registerRequest.email() + " already exists");
        }
        User user = User.builder()
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new UserNotExistException("User not found with email: " + authRequest.email()));
        if(!passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            throw new WrongPasswordException("Wrong password for email: " + authRequest.email());
        }
        String token = jwtService.generateToken(authRequest.email());
        return new AuthResponse(token);
    }
}
