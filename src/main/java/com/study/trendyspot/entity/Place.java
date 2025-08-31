package com.study.trendyspot.entity;

import com.study.trendyspot.util.HashUtil;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "name_hash", length = 64, nullable = false, unique = true)
    private String nameHash;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @PrePersist @PreUpdate
    private void computeHashIfNeeded() {
        if (name != null && (nameHash == null || nameHash.isBlank())) {
            this.nameHash = HashUtil.hashPlaceName(name);
        }
    }
}
