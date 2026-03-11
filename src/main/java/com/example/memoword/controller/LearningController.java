package com.example.memoword.controller;

import com.example.memoword.dto.CustomerUserDetails;
import com.example.memoword.dto.request.AnswerRequest;
import com.example.memoword.dto.request.StartLearningSessionRequest;
import com.example.memoword.dto.response.AnswerResponse;
import com.example.memoword.dto.response.QuestionResponse;
import com.example.memoword.dto.response.StartLearningSessionResponse;
import com.example.memoword.service.LearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learning")
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;

    @PostMapping("/session")
    public StartLearningSessionResponse startSession(@RequestBody StartLearningSessionRequest request,
                                                     @AuthenticationPrincipal CustomerUserDetails userDetails){
        return learningService.startSession(request, userDetails);
    }

    @GetMapping("/session/{sessionId}/question")
    public QuestionResponse getSession(@PathVariable Long sessionId, @AuthenticationPrincipal CustomerUserDetails userDetails) {
        return learningService.getNextQuestion(sessionId, userDetails);
    }

    @PostMapping("/session/{sessionId}/answer")
    public AnswerResponse answer(
            @PathVariable Long sessionId,
            @RequestBody AnswerRequest request,
            @AuthenticationPrincipal CustomerUserDetails userDetails) {
        return learningService.answer(sessionId, request, userDetails);
    }

}
