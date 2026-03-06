package com.example.memoword.service.impl;

import com.example.memoword.dto.CustomerUserDetails;
import com.example.memoword.dto.request.WordRequest;
import com.example.memoword.dto.response.WordResponse;
import com.example.memoword.entity.User;
import com.example.memoword.entity.UserWord;
import com.example.memoword.entity.Word;
import com.example.memoword.exception.WordAlreadyExistsException;
import com.example.memoword.mapper.WordMapper;
import com.example.memoword.repository.UserRepository;
import com.example.memoword.repository.UserWordRepository;
import com.example.memoword.repository.WordRepository;
import com.example.memoword.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;
    private final UserRepository userRepository;
    private final WordMapper wordMapper;

    @Override
    public Word addWord(WordRequest request) {
        Word word;
        Optional<Word> wordsByOriginalWordAndTranslation = wordRepository.findWordByOriginalWordAndTranslation(request.getOriginalWord(), request.getTranslation());
        if (wordsByOriginalWordAndTranslation.isEmpty()) {
            word = wordMapper.toEntity(request);
            return wordRepository.save(word);
        } else {
            throw new WordAlreadyExistsException("Word with original word '" + request.getOriginalWord() + "' and translation '" + request.getTranslation() + "' already exists.");
        }
    }

    @Transactional
    @Override
    public void addWordForCurrentUser(WordRequest request) {
        Optional<Word> existing = wordRepository
                .findWordByOriginalWordAndTranslation(request.getOriginalWord().toLowerCase(), request.getTranslation().toLowerCase());
        Word newWord = existing.orElseGet(() -> {
            Word word = wordMapper.toEntity(request);
            return wordRepository.save(word);
        });
        User user = getCurrentUser();
        if (!userWordRepository.existsByUserAndWord(user, newWord)) {
            UserWord userWord = UserWord.builder()
                    .user(user)
                    .word(newWord)
                    .learned(false)
                    .repetitionCount(0)
                    .addedAt(LocalDateTime.now())
                    .build();
            userWordRepository.save(userWord);
        } else {
            throw new WordAlreadyExistsException("Word with original word '" + request.getOriginalWord() + "' and translation '" + request.getTranslation() + "' already added for user.");
        }
    }

    @Override
    public List<WordResponse> getAllWords() {
        return wordRepository.findAll().stream()
                .map(wordMapper::toResponse)
                .toList();
    }

    @Override
    public List<WordResponse> getAllWordsForCurrentUser() {
        User user = getCurrentUser();
        List<UserWord> userWords = userWordRepository.findAllByUser(user);
        return userWords.stream()
                .map(userWord -> wordMapper.toResponse(userWord.getWord()))
                .toList();
    }

    private User getCurrentUser() {
        CustomerUserDetails currentUser = (CustomerUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findById(currentUser.getId()).orElseThrow();
    }
}
