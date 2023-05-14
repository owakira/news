package com.github.owakira.news.service.impl;

import com.github.owakira.news.exception.NewsSourceNotFoundException;
import com.github.owakira.news.exception.NewsTopicsNotFoundException;
import com.github.owakira.news.model.domain.News;
import com.github.owakira.news.model.domain.NewsSource;
import com.github.owakira.news.model.dto.CreateNewsDTO;
import com.github.owakira.news.model.dto.UpdateNewsDTO;
import com.github.owakira.news.model.entity.NewsEntity;
import com.github.owakira.news.model.entity.NewsSourceEntity;
import com.github.owakira.news.model.entity.NewsTopicEntity;
import com.github.owakira.news.repository.NewsRepository;
import com.github.owakira.news.repository.NewsSourceRepository;
import com.github.owakira.news.repository.NewsTopicRepository;
import com.github.owakira.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsTopicRepository newsTopicRepository;
    private final NewsSourceRepository newsSourceRepository;

    @Override
    @Transactional
    public News createNews(CreateNewsDTO dto) {
        log.info("Create news: [dto={}]", dto);
        var newsEntity = new NewsEntity();

        var topics = findTopics(dto.getTopicIds());

        var source = newsSourceRepository.findById(dto.getSourceId())
                .orElseThrow(NewsSourceNotFoundException::new);

        newsEntity.setTitle(dto.getTitle());
        newsEntity.setContent(dto.getContent());
        newsEntity.setTopics(topics);
        newsEntity.setSource(source);
        newsRepository.save(newsEntity);

        return News.fromEntity(newsEntity);
    }

    @Override
    public Optional<News> getNewsById(Long id) {
        log.info("Get news by id: [id={}]", id);
        return newsRepository.findById(id).map(News::fromEntity);
    }

    @Override
    @Transactional
    public News updateNewsById(Long id, UpdateNewsDTO dto) {
        log.info("Update news by id: [id={}, dto={}]", id, dto);

        var newsEntity = newsRepository.findById(id)
                .orElseThrow(NewsSourceNotFoundException::new);

        if (dto.getContent() != null) {
            newsEntity.setContent(dto.getContent());
        }

        if (dto.getTitle() != null) {
            newsEntity.setTitle(dto.getTitle());
        }

        if (dto.getTopicIds() != null ) {
            var topicEntities = findTopics(dto.getTopicIds());
            newsEntity.setTopics(topicEntities);
        }

        log.info("News successfully updated: [newsEntity={}]", newsEntity);

        return News.fromEntity(newsEntity);
    }

    @Override
    public void deleteNewsById(Long id) {
        log.info("Delete news by id: [id={}]", id);
        try {
            newsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }

    @Override
    public List<News> getAllNews(int page, int pageSize) {
        log.info("Get all news: [page={}, pageSize={}]", page, pageSize);
        var pageable = PageRequest.of(page, pageSize);
        Page<NewsEntity> newsPage = newsRepository.findAll(pageable);
        return newsPage.getContent().stream()
                .map(News::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<News> getNewsBySourceId(Long sourceId, int page, int pageSize) {
        log.info("Get news by source id: [sourceId={}, page={}, pageSize={}]", sourceId, page, pageSize);
        var pageable = PageRequest.of(page, pageSize);
        var newsPage = newsRepository.findAllBySourceId(sourceId, pageable);
        return newsPage.getContent().stream()
                .map(News::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<News> getNewsByTopicId(Long topicId, int page, int pageSize) {
        log.info("Get news by topic id: [topicId={}, page={}, pageSize={}]", topicId, page, pageSize);
        var pageable = PageRequest.of(page, pageSize);
        var newsPage = newsRepository.findAllByTopicsId(topicId, pageable);
        return newsPage.getContent().stream()
                .map(News::fromEntity)
                .collect(Collectors.toList());
    }

    public Map<NewsSource, Integer> getNewsCountBySource() {
        List<NewsEntity> newsList = newsRepository.findAll();
        Map<NewsSource, Integer> newsCountBySource = new HashMap<>();
        for (var news: newsList) {
            var newsSource = NewsSource.fromEntity(news.getSource());
            newsCountBySource.put(newsSource, newsCountBySource.getOrDefault(newsSource, 0) + 1);
        }
        return newsCountBySource;
    }

    private List<NewsTopicEntity> findTopics(List<Long> topicIds) {
        var topics = newsTopicRepository.findAllById(topicIds);
        if (topics.size() != topicIds.size()) {
            Set<Long> foundTopicIds = topics.stream()
                    .map(NewsTopicEntity::getId)
                    .collect(Collectors.toSet());

            Set<Long> missingTopicIds = topicIds.stream()
                    .filter(id -> !foundTopicIds.contains(id))
                    .collect(Collectors.toSet());
            log.warn("News topics not found: [missingTopicIds={}]", missingTopicIds);
            throw new NewsTopicsNotFoundException(missingTopicIds);
        }
        return topics;
    }
}
