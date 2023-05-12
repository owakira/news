package com.github.owakira.news.repository;

import com.github.owakira.news.model.entity.NewsSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsSourceRepository extends JpaRepository<NewsSourceEntity, Long> {
}
