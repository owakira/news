package com.github.owakira.news.controller;

import com.github.owakira.news.exception.NewsNotFoundException;
import com.github.owakira.news.model.dto.CreateNewsDTO;
import com.github.owakira.news.model.dto.UpdateNewsDTO;
import com.github.owakira.news.model.request.CreateNewsRequest;
import com.github.owakira.news.model.request.UpdateNewsRequest;
import com.github.owakira.news.model.response.NewsResponse;
import com.github.owakira.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(NewsController.NEWS_API_ENDPOINT)
@Log4j2
public class NewsController {
    private final NewsService newsService;

    public final static String NEWS_API_ENDPOINT = "/api/news";

    public final static String CREATE_NEWS_ENDPOINT = "/";
    public final static String GET_NEWS_BY_ID_ENDPOINT = "/{id}";
    public final static String UPDATE_NEWS_BY_ID_ENDPOINT = "/{id}";
    public final static String DELETE_NEWS_BY_ID_ENDPOINT = "/{id}";
    public final static String GET_ALL_NEWS_ENDPOINT = "/";
    public final static String GET_NEWS_BY_SOURCE_ID_ENDPOINT = "/by-source/{sourceId}";
    public final static String GET_NEWS_BY_TOPIC_ID_ENDPOINT = "/by-topic/{topicId}";

    @PostMapping(CREATE_NEWS_ENDPOINT)
    public ResponseEntity<?> createNews(@RequestBody CreateNewsRequest request) {
        log.info("Create news: [request={}]", request);

        var dto = new CreateNewsDTO(
                request.getTitle(),
                request.getContent(),
                request.getSourceId(),
                request.getTopicIds()
        );
        var news = newsService.createNews(dto);
        log.info("Created news: [news={}]", news);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(NewsResponse.fromDomain(news));
    }

    @GetMapping(GET_NEWS_BY_ID_ENDPOINT)
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        log.info("Get news by id: [id={}]", id);

        var news = newsService.getNewsById(id)
                .orElseThrow(NewsNotFoundException::new);
        log.info("Found news: [news={}]", news);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NewsResponse.fromDomain(news));
    }

    @GetMapping(GET_ALL_NEWS_ENDPOINT)
    public ResponseEntity<?> getAllNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        log.info("Get all news: [page={}, pageSize={}]", page, pageSize);

        var news = newsService.getAllNews(page, pageSize);
        log.info("Found news: [news={}]", news);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(news.stream().map(NewsResponse::fromDomain).collect(Collectors.toList()));
    }

    @GetMapping(GET_NEWS_BY_SOURCE_ID_ENDPOINT)
    public ResponseEntity<?> getAllNewsBySourceId(
            @PathVariable Long sourceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        log.info("Get all news by source id: [sourceId: {}, page={}, pageSize={}]", sourceId, page, pageSize);

        var news = newsService.getNewsBySourceId(sourceId, page, pageSize);
        log.info("Found news: [news={}]", news);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(news.stream().map(NewsResponse::fromDomain).collect(Collectors.toList()));
    }

    @GetMapping(GET_NEWS_BY_TOPIC_ID_ENDPOINT)
    public ResponseEntity<?> getAllNewsByTopicId(
            @PathVariable Long topicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        log.info("Get all news by topic id: [topicId: {}, page={}, pageSize={}]", topicId, page, pageSize);

        var news = newsService.getNewsByTopicId(topicId, page, pageSize);
        log.info("Found news: [news={}]", news);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(news.stream().map(NewsResponse::fromDomain).collect(Collectors.toList()));
    }

    @DeleteMapping(DELETE_NEWS_BY_ID_ENDPOINT)
    public void deleteNewsById(@PathVariable Long id) {
        log.info("Delete news by id: [id={}]", id);
        newsService.deleteNewsById(id);
        log.info("News successfully deleted: [id: {}]", id);
    }

    @PutMapping(UPDATE_NEWS_BY_ID_ENDPOINT)
    public ResponseEntity<?> updateNewsById(@PathVariable Long id, @RequestBody UpdateNewsRequest request) {
        log.info("Update news by id: [id: {}, request={}]", id, request);

        var dto = new UpdateNewsDTO(request.getTitle(), request.getContent(), request.getTopicIds());
        var updatedNews = newsService.updateNewsById(id, dto);
        log.info("News successfully updated: [updatedNews={}]", updatedNews);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NewsResponse.fromDomain(updatedNews));
    }
}
