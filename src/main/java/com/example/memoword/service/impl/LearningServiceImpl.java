package com.example.memoword.service.impl;

import com.example.memoword.dto.CustomerUserDetails;
import com.example.memoword.dto.request.AnswerRequest;
import com.example.memoword.dto.request.StartLearningSessionRequest;
import com.example.memoword.dto.response.AnswerResponse;
import com.example.memoword.dto.response.QuestionResponse;
import com.example.memoword.dto.response.StartLearningSessionResponse;
import com.example.memoword.entity.LearningSession;
import com.example.memoword.entity.User;
import com.example.memoword.entity.UserWord;
import com.example.memoword.entity.Word;
import com.example.memoword.exception.LearningSessionFinishedException;
import com.example.memoword.exception.SessionDoesntExistException;
import com.example.memoword.repository.LearningSessionRepository;
import com.example.memoword.repository.UserRepository;
import com.example.memoword.repository.UserWordRepository;
import com.example.memoword.repository.WordRepository;
import com.example.memoword.service.LearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningServiceImpl implements LearningService {

    private final UserWordRepository userWordRepository;
    private final UserRepository userRepository;
    private final LearningSessionRepository learningSessionRepository;
    private final WordRepository wordRepository;

    @Override
    public StartLearningSessionResponse startSession(StartLearningSessionRequest request, CustomerUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow();
        List<UserWord> userNotLearnedWords = userWordRepository.findAllByUserAndLearned(user, false);
        Collections.shuffle(userNotLearnedWords);
        List<Long> wordIds = userNotLearnedWords.stream()
                .map(UserWord::getId)
                .toList();
        LearningSession learningSession = LearningSession.builder()
                .user(user)
                .wordIds(wordIds)
                .totalQuestions(wordIds.size())
                .build();
        learningSessionRepository.save(learningSession);

        return StartLearningSessionResponse.builder()
                .sessionId(learningSession.getId())
                .totalWords(wordIds.size())
                .build();
    }

    @Override
    public QuestionResponse getNextQuestion(Long sessionId, CustomerUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow();
        LearningSession learningSession = learningSessionRepository.findByIdAndUser(sessionId, user)
                .orElseThrow(() -> new SessionDoesntExistException("Learning session not found"));
        if (learningSession.isFinished()) {
            throw new LearningSessionFinishedException("Session is finished");
        }
        int currentQuestionIndex = learningSession.getCurrentQuestionIndex();
        Long currentWordId = learningSession.getWordIds().get(currentQuestionIndex);
        Word currentWord = wordRepository.findById(currentWordId).orElseThrow();

        List<String> options = new ArrayList<>(wordRepository.findRandomWords().stream()
                .map(Word::getTranslation)
                .toList());
        options.add(currentWord.getTranslation());
        Collections.shuffle(options);

        return QuestionResponse.builder()
                .question(currentWord.getOriginalWord())
                .options(options)
                .build();
    }

    @Override
    public AnswerResponse answer(Long sessionId, AnswerRequest request, CustomerUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow();
        LearningSession learningSession = learningSessionRepository.findByIdAndUser(sessionId, user)
                .orElseThrow(() -> new SessionDoesntExistException("Learning session not found"));
        int currentQuestionIndex = learningSession.getCurrentQuestionIndex();
        Long currentWordId = learningSession.getWordIds().get(currentQuestionIndex);
        Word currentWord = wordRepository.findById(currentWordId).orElseThrow();
        boolean isCorrect = currentWord.getTranslation().equals(request.getAnswer());

        if (isCorrect) {
            learningSession.setCorrectAnswers(learningSession.getCorrectAnswers() + 1);
        }
        learningSession.setCurrentQuestionIndex(currentQuestionIndex + 1);
        if (learningSession.getCurrentQuestionIndex() == learningSession.getTotalQuestions()) {
            learningSession.setFinished(true);
        }
        learningSessionRepository.save(learningSession);
        return AnswerResponse.builder()
                .correct(isCorrect)
                .sessionFinished(learningSession.isFinished())
                .correctAnswer(currentWord.getTranslation())
                .build();
    }
}
