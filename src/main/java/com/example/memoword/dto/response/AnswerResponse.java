package com.example.memoword.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnswerResponse {

    private boolean correct;
    private String correctAnswer;
    private boolean sessionFinished;
}
