package com.example.memoword.service;

import com.example.memoword.dto.request.WordRequest;
import com.example.memoword.dto.response.WordResponse;
import com.example.memoword.entity.Word;

import java.util.List;

public interface WordService {
    Word addWord(WordRequest request);
    void addWordForCurrentUser(WordRequest request);
    List<WordResponse> getAllWords();
    List<WordResponse> getAllWordsForCurrentUser();

}
