package com.github.owakira.news.controller;

import com.github.owakira.news.exception.NewsSourceNotFoundException;
import com.github.owakira.news.model.dto.CreateNewsSourceDTO;
import com.github.owakira.news.model.dto.UpdateNewsSourceDTO;
import com.github.owakira.news.model.request.CreateNewsSourceRequest;
import com.github.owakira.news.model.request.UpdateNewsSourceRequest;
import com.github.owakira.news.model.response.NewsSourceResponse;
import com.github.owakira.news.service.NewsSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(NewsSourceController.NEWS_SOURCES_API_ENDPOINT)
@RequiredArgsConstructor
@Log4j2
public class NewsSourceController {
    private final NewsSourceService newsSourceService;

    public final static String NEWS_SOURCES_API_ENDPOINT = "/api/news-sources";

    public final static String GET_ALL_NEWS_SOURCES_ENDPOINT = "/";
    public final static String CREATE_NEWS_SOURCE_ENDPOINT = "/";
    public final static String GET_NEWS_SOURCE_BY_ID_ENDPOINT = "/{id}";
    public final static String UPDATE_NEWS_SOURCE_BY_ID_ENDPOINT = "/{id}";
    public final static String DELETE_NEWS_SOURCE_BY_ID_ENDPOINT = "/{id}";

    @PostMapping(CREATE_NEWS_SOURCE_ENDPOINT)
    public ResponseEntity<?> createNewsSource(@Valid @RequestBody CreateNewsSourceRequest request) {
        log.info("Create news source: [request={}]", request);

        var newsSource = newsSourceService.createNewsSource(new CreateNewsSourceDTO(request.getName()));
        log.info("News source successfully created: [newsSource={}]", newsSource);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newsSource);
    }

    @GetMapping(GET_ALL_NEWS_SOURCES_ENDPOINT)
    public ResponseEntity<?> getAllNewsSources() {
        log.info("Get all news sources");

        var newsSources = newsSourceService.getAllNewsSources();
        log.info("Found news sources: [newsSources={}]", newsSources);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newsSources.stream()
                        .map(NewsSourceResponse::fromDomain)
                        .collect(Collectors.toList())
                );
    }

    @GetMapping(GET_NEWS_SOURCE_BY_ID_ENDPOINT)
    public ResponseEntity<?> getNewsSourceById(@PathVariable Long id) {
        log.info("Get news source by id: [id={}]", id);

        var newsSource = newsSourceService.getNewsSourceById(id)
                .orElseThrow(() -> {
                    log.info("News source not found: [id={}]", id);
                    return new NewsSourceNotFoundException();
                });
        log.info("News source found: [newsSource={}]", newsSource);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NewsSourceResponse.fromDomain(newsSource));
    }

    @PutMapping(UPDATE_NEWS_SOURCE_BY_ID_ENDPOINT)
    public ResponseEntity<?> updateNewsSourceById(@PathVariable Long id, @Valid @RequestBody UpdateNewsSourceRequest request) {
        log.info("Update news source by id: [id={}, request={}]", id, request);

        var updatedNewsSource = newsSourceService.updateNewsSourceById(id, new UpdateNewsSourceDTO(request.getName()));
        log.info("News source successfully updated: [newsSource={}]", updatedNewsSource);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NewsSourceResponse.fromDomain(updatedNewsSource));
    }

    @DeleteMapping(DELETE_NEWS_SOURCE_BY_ID_ENDPOINT)
    public void deleteNewsSourceById(@PathVariable Long id) {
        log.info("Delete news source by id: [id={}]", id);
        newsSourceService.deleteNewsSourceById(id);
    }
}
