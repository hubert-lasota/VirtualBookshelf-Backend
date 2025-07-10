CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50) NOT NULL UNIQUE,
    password   TEXT        NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE authority
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE user_profile_picture_binary
(
    id          BIGSERIAL PRIMARY KEY,
    binary_data BYTEA       NOT NULL,
    mime_type   VARCHAR(50) NOT NULL,
    file_name   VARCHAR(150),
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ
);

CREATE TABLE user_profile_picture
(
    id                             BIGSERIAL PRIMARY KEY,
    user_profile_picture_binary_id BIGINT REFERENCES user_profile_picture_binary (id),
    url                            TEXT        NOT NULL,
    created_at                     TIMESTAMPTZ NOT NULL,
    updated_at                     TIMESTAMPTZ
);

CREATE TABLE user_profile
(
    id                      BIGSERIAL PRIMARY KEY,
    user_id                 BIGINT      NOT NULL REFERENCES users (id),
    user_profile_picture_id BIGINT REFERENCES user_profile_picture (id),
    first_name              TEXT,
    last_name               TEXT,
    description             TEXT,
    created_at              TIMESTAMPTZ NOT NULL,
    updated_at              TIMESTAMPTZ
);


CREATE TABLE book_format
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE book_format_translation
(
    id             BIGSERIAL PRIMARY KEY,
    book_format_id BIGINT REFERENCES book_format (id),
    name           TEXT        NOT NULL,
    language_code  TEXT        NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL,
    updated_at     TIMESTAMPTZ
);


CREATE TABLE publisher
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT        NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE book
(
    id               BIGSERIAL PRIMARY KEY,
    book_format_id   BIGINT REFERENCES book_format (id),
    publisher_id     BIGINT REFERENCES publisher (id),
    isbn             VARCHAR(13),
    title            TEXT        NOT NULL,
    publication_year INT,
    language_code    TEXT,
    page_count       INT,
    description      TEXT,
    created_at       TIMESTAMPTZ NOT NULL,
    updated_at       TIMESTAMPTZ
);

CREATE TABLE genre
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE genre_translation
(
    id            BIGSERIAL PRIMARY KEY,
    genre_id      BIGINT      NOT NULL REFERENCES genre (id),
    name          TEXT        NOT NULL,
    language_code TEXT        NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL,
    updated_at    TIMESTAMPTZ
);

CREATE TABLE book_genre
(
    book_id  BIGINT REFERENCES book (id),
    genre_id BIGINT REFERENCES genre (id),
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE book_cover_binary
(
    id          BIGSERIAL PRIMARY KEY,
    binary_data BYTEA       NOT NULL,
    mime_type   VARCHAR(50) NOT NULL,
    file_name   VARCHAR(150),
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ
);

CREATE TABLE book_cover
(
    id                   BIGSERIAL PRIMARY KEY,
    book_id              BIGINT      NOT NULL REFERENCES book (id),
    book_cover_binary_id BIGINT REFERENCES book_cover_binary (id),
    url                  TEXT        NOT NULL,
    created_at           TIMESTAMPTZ NOT NULL,
    updated_at           TIMESTAMPTZ
);


CREATE TABLE book_review
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    book_id    BIGINT      NOT NULL REFERENCES book (id),
    rating     FLOAT       NOT NULL,
    content    TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE author_profile_picture_binary
(
    id          BIGSERIAL PRIMARY KEY,
    binary_data BYTEA       NOT NULL,
    mime_type   VARCHAR(50) NOT NULL,
    file_name   VARCHAR(150),
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ
);

CREATE TABLE author_profile_picture
(
    id                               BIGSERIAL PRIMARY KEY,
    author_profile_picture_binary_id BIGINT REFERENCES author_profile_picture_binary (id),
    url                              TEXT        NOT NULL,
    created_at                       TIMESTAMPTZ NOT NULL,
    updated_at                       TIMESTAMPTZ
);

CREATE TABLE author
(
    id                        BIGSERIAL PRIMARY KEY,
    user_id                   BIGINT REFERENCES users (id),
    author_profile_picture_id BIGINT REFERENCES author_profile_picture (id),
    full_name                 TEXT,
    description               TEXT,
    created_at                TIMESTAMPTZ NOT NULL,
    updated_at                TIMESTAMPTZ
);

CREATE TABLE book_author
(
    book_id   BIGINT REFERENCES book (id),
    author_id BIGINT REFERENCES author (id),
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE author_review
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    author_id  BIGINT      NOT NULL REFERENCES author (id),
    rating     FLOAT       NOT NULL,
    content    TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);


CREATE TABLE bookshelf
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT      NOT NULL REFERENCES users (id),
    name        TEXT        NOT NULL,
    type        TEXT        NOT NULL,
    description TEXT,
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ
);

CREATE TABLE reading_book
(
    id                  BIGSERIAL PRIMARY KEY,
    bookshelf_id        BIGINT      NOT NULL REFERENCES bookshelf (id),
    book_id             BIGINT      NOT NULL REFERENCES book (id),
    status              VARCHAR(50) NOT NULL,
    started_reading_at  TIMESTAMPTZ NOT NULL,
    finished_reading_at TIMESTAMPTZ,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ
);

CREATE TABLE reading_session
(
    id                  BIGSERIAL PRIMARY KEY,
    reading_book_id     BIGINT      NOT NULL REFERENCES reading_book (id),
    page_from           INT         NOT NULL,
    page_to             INT         NOT NULL,
    started_reading_at  TIMESTAMPTZ NOT NULL,
    finished_reading_at TIMESTAMPTZ NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ
);

CREATE TABLE reading_note
(
    id              BIGSERIAL PRIMARY KEY,
    reading_book_id BIGINT      NOT NULL REFERENCES reading_book (id),
    title           TEXT        NOT NULL,
    content         TEXT        NOT NULL,
    page_from       INT         NOT NULL,
    page_to         INT         NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ
);

CREATE TABLE challenge
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT      NOT NULL REFERENCES users (id),
    genre_id     BIGINT REFERENCES genre (id),
    title        TEXT        NOT NULL,
    description  TEXT        NOT NULL,
    start_at     TIMESTAMPTZ NOT NULL,
    end_at       TIMESTAMPTZ NOT NULL,
    type         TEXT        NOT NULL,
    target_count INTEGER     NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL,
    updated_at   TIMESTAMPTZ
);

CREATE TABLE challenge_participant
(
    id           BIGSERIAL PRIMARY KEY,
    challenge_id BIGINT      NOT NULL REFERENCES challenge (id),
    user_id      BIGINT      NOT NULL REFERENCES users (id),
    status       VARCHAR(50),
    started_at   TIMESTAMPTZ NOT NULL,
    finished_at  TIMESTAMPTZ,
    created_at   TIMESTAMPTZ NOT NULL,
    updated_at   TIMESTAMPTZ
);

-- TODO create notification types -> comment_notification etc
CREATE TABLE notification
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    message    TEXT        NOT NULL,
    is_read    BOOLEAN     NOT NULL,
    read_at    TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);


CREATE TABLE user_genre_preferences
(
    user_profile_id BIGINT REFERENCES user_profile (id),
    genre_id        BIGINT REFERENCES genre (id),
    PRIMARY KEY (user_profile_id, genre_id)
);