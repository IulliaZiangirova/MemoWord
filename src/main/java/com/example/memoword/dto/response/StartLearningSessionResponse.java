package com.example.memoword.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StartLearningSessionResponse {
    private Long sessionId;
    private int totalWords;
}
