package com.github.owakira.news.model.response;

import com.github.owakira.news.model.domain.NewsSource;
import com.github.owakira.news.model.domain.NewsTopic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsTopicResponse {
    private Long id;
    private String name;

    public static NewsTopicResponse fromDomain(NewsTopic newsTopic) {
        return new NewsTopicResponse(
                newsTopic.getId(),
                newsTopic.getName()
        );
    }
}
