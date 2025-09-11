package com.study.trendyspot.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @NoArgsConstructor
@Entity
@Table(name = "keyword_data")
public class Keyword extends BaseCreatedOnly{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200, nullable = false)
    private String text;

    @Builder
    private Keyword(String text){
        this.text = text;
    }
}
