package com.github.owakira.news.controller;

import com.github.owakira.news.exception.NewsTopicNotFoundException;
import com.github.owakira.news.model.dto.CreateNewsTopicDTO;
import com.github.owakira.news.model.dto.UpdateNewsTopicDTO;
import com.github.owakira.news.model.request.CreateNewsTopicRequest;
import com.github.owakira.news.model.request.UpdateNewsTopicRequest;
import com.github.owakira.news.model.response.NewsTopicResponse;
import com.github.owakira.news.service.NewsTopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(NewsTopicController.NEWS_TOPIC_API_ENDPOINT)
@RequiredArgsConstructor
@Log4j2
public class NewsTopicController {
    private final NewsTopicService newsTopicService;

    public final static String NEWS_TOPIC_API_ENDPOINT = "/api/news-topics";

    public final static String GET_ALL_NEWS_TOPICS_ENDPOINT = "/";
    public final static String CREATE_NEWS_TOPIC_ENDPOINT = "/";
    public final static String GET_NEWS_TOPIC_BY_ID_ENDPOINT = "/{id}";
    public final static String UPDATE_NEWS_TOPIC_BY_ID_ENDPOINT = "/{id}";
    public final static String DELETE_NEWS_TOPIC_BY_ID_ENDPOINT = "/{id}";

    @PostMapping(CREATE_NEWS_TOPIC_ENDPOINT)
    public ResponseEntity<?> createNewsTopic(@RequestBody CreateNewsTopicRequest request) {
        log.info("Create news topic: [request={}]", request);

        var newsTopic = newsTopicService.createNewsTopic(new CreateNewsTopicDTO(request.getName()));
        log.info("News topic successfully created: [newsTopic={}]", newsTopic);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newsTopic);
    }

    @GetMapping(GET_ALL_NEWS_TOPICS_ENDPOINT)
    public ResponseEntity<?> getAllNewsTopics() {
        log.info("Get all news topics");

        var newsTopics = newsTopicService.getAllNewsTopics();
        log.info("Found news topics: [newsTopics={}]", newsTopics);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newsTopics.stream()
                        .map(NewsTopicResponse::fromDomain)
                        .collect(Collectors.toList())
                );
    }

    @GetMapping(GET_NEWS_TOPIC_BY_ID_ENDPOINT)
    public ResponseEntity<?> getNewsTopicById(@PathVariable Long id) {
        log.info("Get news topic by id: [id={}]", id);

        var newsTopic = newsTopicService.getNewsTopicById(id)
                .orElseThrow(() -> {
                    log.info("News topic not found: [id={}]", id);
                    return new NewsTopicNotFoundException();
                });
        log.info("News topic found: [newsTopic={}]", newsTopic);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NewsTopicResponse.fromDomain(newsTopic));
    }

    @PutMapping(UPDATE_NEWS_TOPIC_BY_ID_ENDPOINT)
    public ResponseEntity<?> updateNewsTopicById(@PathVariable Long id, @RequestBody UpdateNewsTopicRequest request) {
        log.info("Update news topic by id: [id={}, request={}]", id, request);

        var updatedNewsTopic = newsTopicService.updateNewsTopicById(id, new UpdateNewsTopicDTO(request.getName()));
        log.info("News topic successfully updated: [newsTopic={}]", updatedNewsTopic);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NewsTopicResponse.fromDomain(updatedNewsTopic));
    }

    @DeleteMapping(DELETE_NEWS_TOPIC_BY_ID_ENDPOINT)
    public void deleteNewsTopicById(@PathVariable Long id) {
        log.info("Delete news topic by id: [id={}]", id);
        newsTopicService.deleteNewsTopicById(id);
    }
}

