package com.github.owakira.news.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateNewsDTO {
    private String title;
    private String content;
    private List<Long> topicIds;
}
