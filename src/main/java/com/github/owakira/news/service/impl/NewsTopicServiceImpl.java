package com.github.owakira.news.service.impl;

import com.github.owakira.news.exception.NewsTopicNotFoundException;
import com.github.owakira.news.model.domain.NewsTopic;
import com.github.owakira.news.model.dto.CreateNewsTopicDTO;
import com.github.owakira.news.model.dto.UpdateNewsTopicDTO;
import com.github.owakira.news.model.entity.NewsTopicEntity;
import com.github.owakira.news.repository.NewsTopicRepository;
import com.github.owakira.news.service.NewsTopicService;
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
public class NewsTopicServiceImpl implements NewsTopicService {
    private final NewsTopicRepository newsTopicRepository;

    @Override
    public NewsTopic createNewsTopic(CreateNewsTopicDTO dto) {
        log.info("Create news topic: [dto={}]", dto);

        var newsTopicEntity = new NewsTopicEntity();
        newsTopicEntity.setName(dto.getName());
        newsTopicRepository.save(newsTopicEntity);
        log.info("News topic successfully created: [newsTopicEntity={}]", newsTopicEntity);

        return NewsTopic.fromEntity(newsTopicEntity);
    }

    @Override
    public List<NewsTopic> getAllNewsTopics() {
        log.info("Get all news topics");
        var newsTopicEntities = newsTopicRepository.findAll();
        log.info("Found news topics: [newsTopicEntities={}]", newsTopicEntities);

        return newsTopicEntities.stream()
                .map(NewsTopic::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NewsTopic> getNewsTopicById(Long id) {
        log.info("Get news topic by id: [id={}]", id);
        return newsTopicRepository.findById(id)
                .map(NewsTopic::fromEntity);
    }

    @Override
    @Transactional
    public NewsTopic updateNewsTopicById(Long id, UpdateNewsTopicDTO dto) {
        log.info("Update news topic by id: [id={}, dto={}]", id, dto);

        var newsTopicEntity = newsTopicRepository.findById(id).orElseThrow(() -> {
            log.info("News topic not found: [id={}]", id);
            return new NewsTopicNotFoundException();
        });
        if (dto.getName() != null && !dto.getName().equals(newsTopicEntity.getName())) {
            newsTopicEntity.setName(dto.getName());
        }
        newsTopicRepository.save(newsTopicEntity);
        log.info("News topic successfully updated. [newsTopicEntity={}]", newsTopicEntity);

        return NewsTopic.fromEntity(newsTopicEntity);
    }

    @Override
    public void deleteNewsTopicById(Long id) {
        log.info("Delete news topic by id: [id={}]", id);

        try {
            newsTopicRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}
