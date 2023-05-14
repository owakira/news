package com.github.owakira.news.service;

import com.github.owakira.news.model.domain.News;
import com.github.owakira.news.model.domain.NewsSource;
import com.github.owakira.news.model.dto.CreateNewsDTO;
import com.github.owakira.news.model.dto.UpdateNewsDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NewsService {
    News createNews(CreateNewsDTO dto);

    Optional<News> getNewsById(Long id);

    News updateNewsById(Long id, UpdateNewsDTO dto);

    void deleteNewsById(Long id);

    List<News> getAllNews(int page, int limitPage);

    List<News> getNewsBySourceId(Long sourceId, int page, int pageSize);

    List<News> getNewsByTopicId(Long topicId, int page, int pageSize);

    Map<NewsSource, Integer> getNewsCountBySource();
}
