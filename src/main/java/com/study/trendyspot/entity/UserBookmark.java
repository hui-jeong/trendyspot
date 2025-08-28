package com.study.trendyspot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
        name = "user_bookmark",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_user_place",
                columnNames = {"user_id","place_id"}
        )
)
public class UserBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_bookmark_user"))
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_bookmark_place"))
    private Place place;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createAt;
}
