package com.github.owakira.news.model.domain;

import com.github.owakira.news.model.entity.NewsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class News {
    private Long id;
    private String title;
    private String content;
    private NewsSource newsSource;
    private List<NewsTopic> newsTopics;

    public static News fromEntity(NewsEntity newsEntity) {
        return new News(
                newsEntity.getId(),
                newsEntity.getTitle(),
                newsEntity.getContent(),
                NewsSource.fromEntity(newsEntity.getSource()),
                newsEntity.getTopics().stream().map(NewsTopic::fromEntity).collect(Collectors.toList())
        );
    }
}
