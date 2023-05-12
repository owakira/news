package com.github.owakira.news.repository;

import com.github.owakira.news.model.entity.NewsTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTopicRepository extends JpaRepository<NewsTopicEntity, Long> {
}
