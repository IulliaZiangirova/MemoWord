package com.example.memoword.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_word")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    private boolean learned;
    private int repetitionCount;
    private LocalDateTime addedAt;

}
