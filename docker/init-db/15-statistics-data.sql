INSERT INTO book_length_statistics (user_id, length, book_count, read_book_count, year_month, created_at, updated_at)
VALUES (1, 'SHORT', 12, 8, '2025-06', now(), NULL),
       (1, 'MEDIUM', 20, 15, '2025-06', now(), NULL),
       (1, 'LONG', 8, 5, '2025-06', now(), NULL),

       (1, 'SHORT', 10, 7, '2025-07', now(), NULL),
       (1, 'MEDIUM', 22, 18, '2025-07', now(), NULL),
       (1, 'LONG', 9, 6, '2025-07', now(), NULL),

       (1, 'SHORT', 14, 11, '2025-08', now(), NULL),
       (1, 'MEDIUM', 19, 14, '2025-08', now(), NULL),
       (1, 'LONG', 11, 7, '2025-08', now(), NULL),

       (1, 'SHORT', 9, 6, '2025-09', now(), NULL),
       (1, 'MEDIUM', 25, 20, '2025-09', now(), NULL),
       (1, 'LONG', 10, 8, '2025-09', now(), NULL);


INSERT INTO genre_statistics (user_id, genre_id, book_count, read_book_count, year_month, created_at, updated_at)
VALUES (1, 1, 15, 12, '2025-06', now(), NULL),
       (1, 2, 10, 7, '2025-06', now(), NULL),
       (1, 3, 5, 3, '2025-06', now(), NULL),
       (1, 51, 3, 2, '2025-06', now(), NULL),

       (1, 1, 18, 14, '2025-07', now(), NULL),
       (1, 2, 12, 8, '2025-07', now(), NULL),
       (1, 3, 7, 4, '2025-07', now(), NULL),
       (1, 51, 4, 3, '2025-07', now(), NULL),

       (1, 1, 20, 15, '2025-08', now(), NULL),
       (1, 2, 11, 9, '2025-08', now(), NULL),
       (1, 3, 6, 5, '2025-08', now(), NULL),
       (1, 51, 5, 4, '2025-08', now(), NULL),

       (1, 1, 22, 18, '2025-09', now(), NULL),
       (1, 2, 14, 11, '2025-09', now(), NULL),
       (1, 3, 9, 6, '2025-09', now(), NULL),
       (1, 51, 6, 5, '2025-09', now(), NULL);


INSERT INTO user_reading_statistics (user_id, total_read_books, total_read_pages, most_pages_read_in_session,
                                     total_read_minutes, longest_read_minutes, current_reading_streak,
                                     longest_reading_streak, year_month, created_at, updated_at)
VALUES (1, 25, 6200, 310, 4800, 240, 12, 18, '2025-06', now(), NULL),
       (1, 28, 7000, 350, 5200, 260, 15, 20, '2025-07', now(), NULL),
       (1, 30, 7600, 400, 5600, 300, 16, 22, '2025-08', now(), NULL),
       (1, 32, 8200, 420, 6000, 310, 18, 25, '2025-09', now(), NULL);
