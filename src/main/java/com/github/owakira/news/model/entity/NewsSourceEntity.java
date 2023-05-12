package com.github.owakira.news.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = NewsSourceEntity.TABLE_NAME)
@Entity
@Getter
@Setter
@ToString
public class NewsSourceEntity {
    public final static String TABLE_NAME = "news_source";
    public final static String ID_COLUMN_NAME = "id";
    public final static String NAME_COLUMN_NAME = "name";

    @Id
    @Column(name = ID_COLUMN_NAME)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = NAME_COLUMN_NAME, nullable = false)
    private String name;


}
