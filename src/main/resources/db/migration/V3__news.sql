BEGIN;

CREATE TABLE public.news
(
    id        bigserial PRIMARY KEY,
    title     varchar(256) NOT NULL,
    content   text         NOT NULL,
    source_id bigint REFERENCES news_source (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.news_news_topic
(
    news_id  bigint REFERENCES news (id) ON DELETE CASCADE ON UPDATE CASCADE       NOT NULL,
    topic_id bigint REFERENCES news_topic (id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL
);

COMMIT;