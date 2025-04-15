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
    authority  VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE user_profile_picture
(
    id                  BIGSERIAL PRIMARY KEY,
    profile_picture_url TEXT        NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ
);

CREATE TABLE user_profile_picture_img
(
    id                      BIGSERIAL PRIMARY KEY,
    user_profile_picture_id BIGINT REFERENCES user_profile_picture (id),
    img                     BYTEA,
    img_type                TEXT,
    created_at              TIMESTAMPTZ NOT NULL,
    updated_at              TIMESTAMPTZ
);

CREATE TABLE user_profile
(
    id                 BIGSERIAL PRIMARY KEY,
    user_id            BIGINT      NOT NULL REFERENCES users (id),
    profile_picture_id BIGINT REFERENCES user_profile_picture (id),
    first_name         TEXT,
    last_name          TEXT,
    description        TEXT,
    created_at         TIMESTAMPTZ NOT NULL,
    updated_at         TIMESTAMPTZ
);

CREATE TABLE user_genre_preferences
(
    user_profile_id BIGINT REFERENCES user_profile (id),
    genre_id        BIGINT REFERENCES genre (id),
    PRIMARY KEY (user_profile_id, genre_id)
);

CREATE TABLE book
(
    id              BIGSERIAL PRIMARY KEY,
    order_in_series INT,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ
);

CREATE TABLE book_series
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE book_series_names
(
    id           BIGSERIAL PRIMARY KEY,
    name         TEXT        NOT NULL,
    language_tag TEXT        NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL,
    updated_at   TIMESTAMPTZ
);

CREATE TABLE book_book_series
(
    book_id        BIGINT REFERENCES book (id),
    book_series_id BIGINT REFERENCES book_series (id),
    PRIMARY KEY (book_id, book_series_id)
);

CREATE TABLE book_format
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE book_format_name
(
    id             BIGSERIAL PRIMARY KEY,
    book_format_id BIGINT REFERENCES book_format (id),
    name           TEXT        NOT NULL,
    language_tag   TEXT        NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL,
    updated_at     TIMESTAMPTZ
);

CREATE TABLE book_edition
(
    id               BIGSERIAL PRIMARY KEY,
    book_id          BIGINT REFERENCES book (id),
    book_format_id   BIGINT REFERENCES book_format (id),
    isbn             VARCHAR(13),
    title            TEXT        NOT NULL,
    publication_year INT,
    language_tag     TEXT,
    created_at       TIMESTAMPTZ NOT NULL,
    updated_at       TIMESTAMPTZ
);

CREATE TABLE genre
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE genre_name
(
    id            BIGSERIAL PRIMARY KEY,
    book_genre_id BIGINT REFERENCES genre (id),
    created_at    TIMESTAMPTZ NOT NULL,
    updated_at    TIMESTAMPTZ
);

CREATE TABLE book_genre
(
    book_id  BIGINT REFERENCES book (id),
    genre_id BIGINT REFERENCES genre (id),
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE book_cover_img
(
    id         BIGSERIAL PRIMARY KEY,
    cover_img  BYTEA,
    img_type   TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE book_cover
(
    id                BIGSERIAL PRIMARY KEY,
    book_id           BIGINT      NOT NULL REFERENCES book (id),
    book_cover_img_id BIGINT REFERENCES book_cover_img (id),
    cover_url         TEXT        NOT NULL,
    created_at        TIMESTAMPTZ NOT NULL,
    updated_at        TIMESTAMPTZ
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

CREATE TABLE publisher
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT        NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE book_edition_publisher
(
    book_edition_id BIGINT REFERENCES book_edition (id),
    publisher_id    BIGINT REFERENCES publisher (id),
    PRIMARY KEY (book_edition_id, publisher_id)
);

CREATE TABLE author
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id),
    full_name   TEXT,
    description TEXT,
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ
);

CREATE TABLE author_photo_img
(
    id         BIGSERIAL PRIMARY KEY,
    img        BYTEA       NOT NULL,
    img_type   VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE author_photo
(
    id                  BIGSERIAL PRIMARY KEY,
    author_photo_img_id BIGINT REFERENCES author_photo_img (id),
    photo_url           TEXT        NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ
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


CREATE TABLE book_notes
(
    id              BIGSERIAL PRIMARY KEY,
    book_edition_id BIGINT REFERENCES book_edition (id),
    start_page      INT         NOT NULL,
    end_page        INT         NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ
);

CREATE TABLE book_reading_details
(
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT      NOT NULL REFERENCES users (id),
    book_id             BIGINT      NOT NULL REFERENCES book (id),
    current_page        INT,
    progress_percentage INT,
    finished_at         TIMESTAMPTZ,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ
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

CREATE TABLE bookshelf_book_edition
(
    bookshelf_id    BIGINT REFERENCES bookshelf (id),
    book_edition_id BIGINT REFERENCES book_edition (id),
    PRIMARY KEY (bookshelf_id, book_edition_id)
);

CREATE TABLE book_recommendation
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    book_id    BIGINT REFERENCES book (id),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE genre_recommendation
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    genre_id   BIGINT      NOT NULL REFERENCES genre (id),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE tag
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE book_tag
(
    id         BIGSERIAL PRIMARY KEY,
    book_id    BIGINT      NOT NULL REFERENCES book (id),
    tag_id     BIGINT      NOT NULL REFERENCES tag (id),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE author_tag
(
    id         BIGSERIAL PRIMARY KEY,
    author_id  BIGINT      NOT NULL REFERENCES author (id),
    tag_id     BIGINT      NOT NULL REFERENCES tag (id),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE challenge
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id),
    description TEXT        NOT NULL,
    start_at    TIMESTAMPTZ NOT NULL,
    finish_at   TIMESTAMPTZ NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ
);

CREATE TABLE challenge_participant_details
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
