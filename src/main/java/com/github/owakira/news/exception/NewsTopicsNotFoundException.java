package com.github.owakira.news.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NewsTopicsNotFoundException extends RuntimeException {
    public NewsTopicsNotFoundException(Set<Long> missingTopicIds) {
        super("News topics not found: " + missingTopicIds);
    }
}
