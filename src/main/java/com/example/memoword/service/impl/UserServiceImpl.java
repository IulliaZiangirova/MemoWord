package com.example.memoword.service.impl;

import com.example.memoword.repository.UserRepository;
import com.example.memoword.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
}
