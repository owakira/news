package com.github.owakira.news.service.impl;

import com.github.owakira.news.exception.NewsSourceNotFoundException;
import com.github.owakira.news.model.domain.NewsSource;
import com.github.owakira.news.model.dto.CreateNewsSourceDTO;
import com.github.owakira.news.model.dto.UpdateNewsSourceDTO;
import com.github.owakira.news.model.entity.NewsSourceEntity;
import com.github.owakira.news.repository.NewsSourceRepository;
import com.github.owakira.news.service.NewsSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class NewsSourceServiceImpl implements NewsSourceService {
    private final NewsSourceRepository newsSourceRepository;

    @Override
    public NewsSource createNewsSource(CreateNewsSourceDTO dto) {
        log.info("Create news source: [dto={}]", dto);

        var newsSourceEntity = new NewsSourceEntity();
        newsSourceEntity.setName(dto.getName());
        newsSourceRepository.save(newsSourceEntity);
        log.info("News source successfully created: [newsSourceEntity={}]", newsSourceEntity);

        return NewsSource.fromEntity(newsSourceEntity);
    }

    @Override
    public List<NewsSource> getAllNewsSources() {
        log.info("Get all news sources");
        var newsSourceEntities = newsSourceRepository.findAll();
        log.info("Found news sources: [newsSourceEntities={}]", newsSourceEntities);

        return newsSourceEntities.stream()
                .map(NewsSource::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NewsSource> getNewsSourceById(Long id) {
        log.info("Get news source by id: [id={}]", id);
        return newsSourceRepository.findById(id)
                .map(NewsSource::fromEntity);
    }

    @Override
    @Transactional
    public NewsSource updateNewsSourceById(Long id, UpdateNewsSourceDTO dto) {
        log.info("Update news source by id: [id={}, dto={}]", id, dto);

        var newsSourceEntity = newsSourceRepository.findById(id).orElseThrow(() -> {
            log.info("News source not found: [id={}]", id);
            return new NewsSourceNotFoundException();
        });
        if (dto.getName() != null && !dto.getName().equals(newsSourceEntity.getName())) {
            newsSourceEntity.setName(dto.getName());
        }
        newsSourceRepository.save(newsSourceEntity);
        log.info("News source successfully updated. [newsSourceEntity={}]", newsSourceEntity);

        return NewsSource.fromEntity(newsSourceEntity);
    }

    @Override
    public void deleteNewsSourceById(Long id) {
        log.info("Delete news source by id: [id={}]", id);

        try {
            newsSourceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}
