package com.github.owakira.news.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NewsTopicNotFoundException extends RuntimeException {
    public NewsTopicNotFoundException() {
        super("News topic not found exception");
    }
}
