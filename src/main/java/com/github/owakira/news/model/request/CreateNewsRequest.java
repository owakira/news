package com.github.owakira.news.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateNewsRequest {
    @Size(min = 1, max = 256, message = "Title length must be between 1 and 256")
    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Content is required")
    @Size(min = 1, message = "Content size is too short")
    private String content;

    @NotNull(message = "Source id is required")
    private Long sourceId;

    @NotNull(message = "Topic ids is required")
    @Size(min = 1, message = "At least one topic id must be")
    private List<Long> topicIds;
}
