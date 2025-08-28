package com.study.trendyspot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
        name = "trend_snapshot",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_trend",
                columnNames = {"keyword_id","date_bucket","granularity"}
        )
)
public class TrendSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "keyword_id", nullable = false, foreignKey = @ForeignKey(name = "fk_trend_snapshot_keyword"))
    private Keyword keyword;

    @Column(name = "date_bucket")
    private LocalDate dateBucket;

    @Enumerated(EnumType.STRING)
    @Column(name = "granularity", nullable = false, length = 10)
    private Granularity granularity = Granularity.DAILY;

    @Column(name = "index_value", nullable = false)
    private Integer indexValue;

    @Column(name = "fetched_at", nullable = false)
    private LocalDate fetchedAt;
}
