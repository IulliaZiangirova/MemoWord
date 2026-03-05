package com.example.memoword.service.impl;

import com.example.memoword.dto.request.WordRequest;
import com.example.memoword.dto.response.WordResponse;
import com.example.memoword.entity.Word;
import com.example.memoword.exception.WordAlreadyExistsException;
import com.example.memoword.mapper.WordMapper;
import com.example.memoword.repository.WordRepository;
import com.example.memoword.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final WordMapper wordMapper;

    @Override
    public void addWord(WordRequest request) {
        boolean wordAlreadyExists = false;
        List<Word> wordsByOriginalWord = wordRepository.findWordsByOriginalWord(request.getOriginalWord());
        if(!wordsByOriginalWord.isEmpty()) {
            wordAlreadyExists = wordsByOriginalWord.stream()
                    .anyMatch(word -> word.getTranslation().equals(request.getTranslation()));
        }
        if(!wordAlreadyExists) {
            Word word = wordMapper.toEntity(request);
            wordRepository.save(word);
        } else {
            throw new WordAlreadyExistsException("Word with original word '" + request.getOriginalWord() + "' and translation '" + request.getTranslation() + "' already exists.");
        }
    }

    @Override
    public List<WordResponse> getAllWords() {
        return wordRepository.findAll().stream()
                .map(wordMapper::toResponse)
                .toList();
    }
}
