package com.study.trendyspot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "keyword_data")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200, nullable = false)
    private String text;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
