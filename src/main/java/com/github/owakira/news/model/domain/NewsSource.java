package com.github.owakira.news.model.domain;

import com.github.owakira.news.model.entity.NewsSourceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsSource {
    private Long id;
    private String name;

    public static NewsSource fromEntity(NewsSourceEntity newsSourceEntity) {
        return new NewsSource(
                newsSourceEntity.getId(),
                newsSourceEntity.getName()
        );
    }
}
