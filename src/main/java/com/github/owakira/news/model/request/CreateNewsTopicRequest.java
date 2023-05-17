package com.github.owakira.news.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateNewsTopicRequest {
    @NotNull(message = "Name is required")
    @Size(min = 1, max = 20, message = "Name length must be between 1 and 20")
    private String name;
}
