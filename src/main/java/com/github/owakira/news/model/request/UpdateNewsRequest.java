package com.github.owakira.news.model.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateNewsRequest {
    private String title;
    private String content;
    private List<Long> topicIds;
}
