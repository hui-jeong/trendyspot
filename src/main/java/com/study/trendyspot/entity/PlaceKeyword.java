package com.study.trendyspot.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @NoArgsConstructor
@Entity
@Table(
        name = "place_keyword",
        indexes = @Index(name = "idx_place_keyword_on_keys", columnList = "place_id, keyword_id")
)
public class PlaceKeyword extends BaseUpdatedOnly{

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

    @Builder
    private PlaceKeyword(Place place, Keyword keyword, float score){
        this.place = place;
        this.keyword = keyword;
        this.score = score;
    }
}
