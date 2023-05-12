BEGIN;

CREATE TABLE public.news_topic
(
    id   bigserial PRIMARY KEY,
    name varchar(20) NOT NULL
);

COMMIT;