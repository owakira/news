package com.github.owakira.news.model.response;

import com.github.owakira.news.model.domain.NewsSource;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsSourceResponse {
    private Long id;
    private String name;

    public static NewsSourceResponse fromDomain(NewsSource newsSource) {
        return new NewsSourceResponse(
                newsSource.getId(),
                newsSource.getName()
        );
    }
}
