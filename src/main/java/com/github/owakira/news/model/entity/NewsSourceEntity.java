package com.github.owakira.news.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Table(
        name = NewsSourceEntity.TABLE_NAME,
        schema = NewsSourceEntity.SCHEMA_NAME
)
@Entity
@Getter
@Setter
@ToString
public class NewsSourceEntity {
    public final static String TABLE_NAME = "news_source";
    public final static String SCHEMA_NAME = "public";
    public final static String ID_COLUMN_NAME = "id";
    public final static String NAME_COLUMN_NAME = "name";

    public final static int NAME_COLUMN_LENGTH = 256;

    @Id
    @Column(name = ID_COLUMN_NAME)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = NAME_COLUMN_NAME, nullable = false, length = NAME_COLUMN_LENGTH)
    private String name;
}
