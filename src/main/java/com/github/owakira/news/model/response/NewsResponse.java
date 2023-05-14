package com.github.owakira.news.model.response;

import com.github.owakira.news.model.domain.News;
import com.github.owakira.news.model.domain.NewsTopic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class NewsResponse {
    private Long id;
    private String title;
    private String content;
    private Long sourceId;
    private List<Long> topicsId;

    public static NewsResponse fromDomain(News news) {
        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getNewsSource().getId(),
                news.getNewsTopics().stream().map(NewsTopic::getId).collect(Collectors.toList())
        );
    }
}
