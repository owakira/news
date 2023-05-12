package com.github.owakira.news.model.domain;

import com.github.owakira.news.model.entity.NewsTopicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsTopic {
    private Long id;
    private String name;

    public static NewsTopic fromEntity(NewsTopicEntity newsTopic) {
        return new NewsTopic(
                newsTopic.getId(),
                newsTopic.getName()
        );
    }
}
