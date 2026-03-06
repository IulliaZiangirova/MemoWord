package com.example.memoword.controller;

import com.example.memoword.dto.request.WordRequest;
import com.example.memoword.dto.response.WordResponse;
import com.example.memoword.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @PostMapping
    public void addWord(@RequestBody WordRequest request) {
        wordService.addWord(request);
    }

    @PostMapping("/user-words")
    public void addWordForCurrentUser(@RequestBody WordRequest request) {
        wordService.addWordForCurrentUser(request);
    }

    @GetMapping
    List<WordResponse> getAllWords() {
        return wordService.getAllWords();
    }

    @GetMapping("/user-words")
    List<WordResponse> getAllWordsForCurrentUser() {
        log.info("Getting all words for current user");
        return wordService.getAllWordsForCurrentUser();
    }
}
