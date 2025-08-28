package com.study.trendyspot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
        name = "place_keyword",
        indexes = @Index(name = "idx_place_keyword_on_keys", columnList = "place_id, keyword_id")
)
public class PlaceKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false, foreignKey = @ForeignKey(name = "fk_place_keyword_place"))
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "keyword_id", nullable = false, foreignKey = @ForeignKey(name = "fk_place_keyword_keyword"))
    private  Keyword keyword;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

}
