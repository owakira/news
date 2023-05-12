package com.github.owakira.news.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NewsSourceNotFoundException extends RuntimeException{
    public NewsSourceNotFoundException() {
        super("News source not found");
    }
}
