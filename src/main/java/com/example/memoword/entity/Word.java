package com.example.memoword.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalWord;
    private String translation;

    @OneToMany(mappedBy = "word")
    private List<User> users = new ArrayList<>();
}
