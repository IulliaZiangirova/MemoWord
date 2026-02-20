package com.example.memoword.service;

import com.example.memoword.dto.request.WordRequest;
import com.example.memoword.dto.response.WordResponse;

import java.util.List;

public interface WordService {
    void addWord(WordRequest request);
    List<WordResponse> getAllWords();

}
