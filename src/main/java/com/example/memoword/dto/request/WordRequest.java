package com.example.memoword.dto.request;

import lombok.Data;

@Data
public class WordRequest {

    private String originalWord;
    private String translation;
}
