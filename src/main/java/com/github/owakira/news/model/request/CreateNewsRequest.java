package com.github.owakira.news.model.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateNewsRequest {
    private String title;
    private String content;
    private Long sourceId;
    private List<Long> topicIds;
}
