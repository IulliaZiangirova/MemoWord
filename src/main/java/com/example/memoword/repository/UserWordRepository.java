package com.example.memoword.repository;

import com.example.memoword.entity.User;
import com.example.memoword.entity.UserWord;
import com.example.memoword.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordRepository extends JpaRepository<UserWord, Long> {

    boolean existsByUserAndWord(User user, Word word);
}
