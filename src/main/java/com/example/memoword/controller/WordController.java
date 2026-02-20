package com.example.memoword.controller;

import com.example.memoword.dto.request.WordRequest;
import com.example.memoword.dto.response.WordResponse;
import com.example.memoword.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @PostMapping
    public void addWord(@RequestBody WordRequest request) {
        wordService.addWord(request);
    }

    @GetMapping
    List<WordResponse> getAllWords() {
        return wordService.getAllWords();
    }
}
