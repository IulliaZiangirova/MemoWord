package com.example.memoword.service;

import com.example.memoword.dto.CustomerUserDetails;
import com.example.memoword.dto.request.AnswerRequest;
import com.example.memoword.dto.request.StartLearningSessionRequest;
import com.example.memoword.dto.response.AnswerResponse;
import com.example.memoword.dto.response.QuestionResponse;
import com.example.memoword.dto.response.StartLearningSessionResponse;

public interface LearningService {
    StartLearningSessionResponse startSession(StartLearningSessionRequest request, CustomerUserDetails userDetails);
    QuestionResponse getNextQuestion(Long sessionId, CustomerUserDetails userDetails);
    AnswerResponse answer(Long sessionId, AnswerRequest request, CustomerUserDetails userDetails);
}
