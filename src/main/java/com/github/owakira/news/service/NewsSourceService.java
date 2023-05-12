package com.github.owakira.news.service;

import com.github.owakira.news.model.domain.NewsSource;
import com.github.owakira.news.model.dto.CreateNewsSourceDTO;
import com.github.owakira.news.model.dto.UpdateNewsSourceDTO;

import java.util.List;
import java.util.Optional;

public interface NewsSourceService {
    NewsSource createNewsSource(CreateNewsSourceDTO dto);

    List<NewsSource> getAllNewsSources();

    Optional<NewsSource> getNewsSourceById(Long id);

    NewsSource updateNewsSourceById(Long id, UpdateNewsSourceDTO dto);

    void deleteNewsSourceById(Long id);
}
