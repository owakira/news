BEGIN;

CREATE TABLE public.usr
(
    id         bigserial PRIMARY KEY,
    email      varchar(256) NOT NULL
        CONSTRAINT uk_email UNIQUE,
    username   varchar(32)  NOT NULL
        CONSTRAINT uk_username UNIQUE,
    password   varchar(60)  NOT NULL
);

COMMIT;