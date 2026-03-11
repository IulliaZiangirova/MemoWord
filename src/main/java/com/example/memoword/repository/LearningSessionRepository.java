package com.example.memoword.repository;

import com.example.memoword.entity.LearningSession;
import com.example.memoword.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearningSessionRepository extends JpaRepository<LearningSession, Long> {
    Optional<LearningSession> findByIdAndUser(Long sessionId, User user);
}
