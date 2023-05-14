package com.github.owakira.news.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateNewsDTO {
    private String title;
    private String content;
    private Long sourceId;
    private List<Long> topicIds;
}
