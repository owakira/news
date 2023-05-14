package com.github.owakira.news.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(
        name = NewsEntity.TABLE_NAME,
        schema = NewsEntity.SCHEMA_NAME
)
@Getter
@Setter
@ToString
public class NewsEntity {
    public final static String TABLE_NAME = "news";
    public final static String SCHEMA_NAME = "public";
    public final static String ID_COLUMN_NAME = "id";
    public final static String TITLE_COLUMN_NAME = "title";
    public final static String CONTENT_COLUMN_NAME = "content";
    public final static String NEWS_SOURCE_ID_NAME = "source_id";

    public final static int TITLE_COLUMN_LENGTH = 256;

    @Id
    @Column(name = ID_COLUMN_NAME)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = TITLE_COLUMN_NAME, length = TITLE_COLUMN_LENGTH, nullable = false)
    private String title;

    @Column(name = CONTENT_COLUMN_NAME, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = NEWS_SOURCE_ID_NAME, nullable = false)
    @ToString.Exclude
    private NewsSourceEntity source;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = NewsNewsTopic.TABLE_NAME,
            joinColumns = @JoinColumn(name = NewsNewsTopic.NEWS_ID_COLUMN_NAME),
            inverseJoinColumns = @JoinColumn(name = NewsNewsTopic.TOPIC_ID_COLUMN_NAME)
    )
    @ToString.Exclude
    private List<NewsTopicEntity> topics;
}
