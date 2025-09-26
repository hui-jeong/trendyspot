package com.study.trendyspot.repository;

import com.study.trendyspot.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordsRepository extends JpaRepository<Keyword, Integer> {
    Optional<Keyword> findByText(String text);
}
