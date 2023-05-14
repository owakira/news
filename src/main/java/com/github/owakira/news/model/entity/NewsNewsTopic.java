package com.github.owakira.news.model.entity;

import lombok.Data;

@Data
public class NewsNewsTopic {
    public static final String TABLE_NAME = "news_news_topic";
    public static final String NEWS_ID_COLUMN_NAME = "news_id";
    public static final String TOPIC_ID_COLUMN_NAME = "topic_id";
}
