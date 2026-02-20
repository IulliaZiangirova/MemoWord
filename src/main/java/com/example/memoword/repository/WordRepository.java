package com.example.memoword.repository;

import com.example.memoword.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class WordRepository implements JpaRepository<Word, Long> {

}
