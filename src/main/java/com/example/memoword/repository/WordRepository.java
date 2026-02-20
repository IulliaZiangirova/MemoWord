package com.example.memoword.repository;

import com.example.memoword.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WordRepository extends JpaRepository<Word, Long> {

}
