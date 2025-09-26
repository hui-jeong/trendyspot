package com.study.trendyspot.repository;

import com.study.trendyspot.entity.Granularity;
import com.study.trendyspot.entity.Keyword;
import com.study.trendyspot.entity.TrendSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrendSnapshotRepository extends JpaRepository<TrendSnapshot, Integer> {
    List<TrendSnapshot> findByKeywordAndGranularityAndDateBucketBetweenOrderByDateBucket(
            Keyword keyword, Granularity granularity, LocalDate from, LocalDate to
    );
}
