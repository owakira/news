package com.github.owakira.news.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateNewsRequest {
    @Size(min = 1, max = 256, message = "Title size must be between 1 and 256")
    private String title;

    @Size(min = 1, message = "Content size is too short")
    private String content;

    private List<Long> topicIds;
}
