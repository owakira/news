package com.github.owakira.news.repository;

import com.github.owakira.news.model.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    Page<NewsEntity> findAllBySourceId(Long id, PageRequest pageRequest);

    Page<NewsEntity> findAllByTopicsId(Long topicId, PageRequest pageRequest);
}
