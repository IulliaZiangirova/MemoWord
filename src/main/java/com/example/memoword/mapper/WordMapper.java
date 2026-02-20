package com.example.memoword.mapper;

import com.example.memoword.dto.request.WordRequest;
import com.example.memoword.dto.response.WordResponse;
import com.example.memoword.entity.Word;
import org.mapstruct.Mapper;

@Mapper
public interface WordMapper {

    Word toEntity(WordRequest request);
    WordResponse toResponse(Word word);
}
