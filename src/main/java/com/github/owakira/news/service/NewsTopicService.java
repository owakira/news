package com.github.owakira.news.service;

import com.github.owakira.news.model.domain.NewsTopic;
import com.github.owakira.news.model.dto.CreateNewsTopicDTO;
import com.github.owakira.news.model.dto.UpdateNewsTopicDTO;

import java.util.List;
import java.util.Optional;

public interface NewsTopicService {
    NewsTopic createNewsTopic(CreateNewsTopicDTO dto);

    List<NewsTopic> getAllNewsTopics();

    Optional<NewsTopic> getNewsTopicById(Long id);

    NewsTopic updateNewsTopicById(Long id, UpdateNewsTopicDTO dto);

    void deleteNewsTopicById(Long id);
}
