INSERT INTO book_format (id, created_at, updated_at)
VALUES (1, now(), NULL),
       (2, now(), NULL),
       (3, now(), NULL),
       (4, now(), NULL),
       (5, now(), NULL),
       (6, now(), NULL);

INSERT INTO book_format_translation (book_format_id, language_code, name, created_at, updated_at)
VALUES (1, 'pl', 'Twarda oprawa', now(), NULL),
       (2, 'pl', 'Miękka oprawa', now(), NULL),
       (3, 'pl', 'E-book', now(), NULL),
       (4, 'pl', 'Audiobook', now(), NULL),
       (5, 'pl', 'Kieszonkowa', now(), NULL),
       (6, 'pl', 'Magazyn', now(), NULL);

INSERT INTO book_format_translation (book_format_id, language_code, name, created_at, updated_at)
VALUES (1, 'en', 'Hardcover', now(), NULL),
       (2, 'en', 'Paperback', now(), NULL),
       (3, 'en', 'E-book', now(), NULL),
       (4, 'en', 'Audiobook', now(), NULL),
       (5, 'en', 'Pocket edition', now(), NULL),
       (6, 'en', 'Magazine', now(), NULL);

SELECT setval(pg_get_serial_sequence('book_format', 'id'), (select MAX(id) from book_format), true);