-- USER GENRE PREFERENCES
INSERT INTO user_genre_preferences(user_id, genre_id, created_at)
VALUES (1, 1, now()),
       (1, 2, now()),
       (1,3, now()),
       (1,4, now()),
       (1, 3, now()),
       (1, 4, now()),
       (1, 51, now()),
       (2, 1, now()),
       (2, 2, now()),
       (3, 3, now()),
       (4, 4, now()),
       (4, 51, now());

-- GENRE
INSERT INTO genre_recommendation (user_id, genre_id, score, created_at, updated_at)
SELECT bs.user_id,
       g.id                               AS genre_id,
       (COUNT(DISTINCT rb.id)
           + COUNT(DISTINCT rn.id)
           + COUNT(DISTINCT rs.id)) * 0.1 AS score,
       now(),
       NULL
FROM reading_book rb
         JOIN bookshelf bs ON rb.bookshelf_id = bs.id
         JOIN book b ON rb.book_id = b.id
         JOIN book_genre bg ON b.id = bg.book_id
         JOIN genre g ON g.id = bg.genre_id
         LEFT JOIN reading_note rn ON rn.reading_book_id = rb.id
         LEFT JOIN reading_session rs ON rs.reading_book_id = rb.id
GROUP BY bs.user_id, g.id;

-- AUTHOR
INSERT INTO author_recommendation (user_id, author_id, score, created_at, updated_at)
SELECT bs.user_id,
       a.id                               AS author_id,
       (COUNT(DISTINCT rb.id)
           + COUNT(DISTINCT rn.id)
           + COUNT(DISTINCT rs.id)) * 0.1 AS score,
       now(),
       NULL
FROM reading_book rb
         JOIN bookshelf bs ON rb.bookshelf_id = bs.id
         JOIN book b ON rb.book_id = b.id
         JOIN book_author ba ON b.id = ba.book_id
         JOIN author a ON a.id = ba.author_id
         LEFT JOIN reading_note rn ON rn.reading_book_id = rb.id
         LEFT JOIN reading_session rs ON rs.reading_book_id = rb.id
GROUP BY bs.user_id, a.id;


-- BOOK
INSERT INTO book_recommendation (user_id, book_id, score, created_at, updated_at)
SELECT 1, b.id, (random() * 4 + 1)::numeric(2, 1), now(), NULL
FROM book b
WHERE b.id BETWEEN 300 AND 356
  AND b.id NOT IN (355, 335);

