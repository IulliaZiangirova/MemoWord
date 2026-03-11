package com.example.memoword.repository;

import com.example.memoword.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    Optional<Word> findWordByOriginalWordAndTranslation(String originalWord, String translation);

    @Query("SELECT w FROM Word w ORDER BY RAND() LIMIT 3")
    List<Word> findRandomWords();

}
