INSERT INTO genre (id, created_at, updated_at)
VALUES (1, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (2, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (3, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (4, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (5, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (6, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (7, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (8, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (9, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (10, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (11, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (12, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (13, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (14, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (15, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (16, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (17, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (18, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (19, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (20, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (21, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (22, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (23, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (24, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (25, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (26, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (27, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (28, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (29, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (30, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (31, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (32, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (33, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (34, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (35, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (36, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (37, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (38, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (39, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (40, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (41, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (42, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (43, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (44, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (45, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (46, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (47, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (48, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (49, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (50, NOW(), NULL);
INSERT INTO genre (id, created_at, updated_at)
VALUES (51, NOW(), NULL);

SELECT setval(pg_get_serial_sequence('genre', 'id'), (select MAX(id) from genre), true);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (1, 'Fantastyka', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (1, 'Fantasy', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (2, 'Kryminał', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (2, 'Crime', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (3, 'Horror', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (3, 'Horror', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (4, 'Thriller', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (4, 'Thriller', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (5, 'Romans', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (5, 'Romance', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (6, 'Science fiction', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (6, 'Science Fiction', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (7, 'Biografia', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (7, 'Biography', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (8, 'Historyczna', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (8, 'Historical', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (9, 'Powieść obyczajowa', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (9, 'Contemporary Fiction', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (10, 'Powieść przygodowa', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (10, 'Adventure', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (11, 'Literatura młodzieżowa', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (11, 'Young Adult', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (12, 'Poezja', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (12, 'Poetry', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (13, 'Dramat', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (13, 'Drama', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (14, 'Komedia', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (14, 'Comedy', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (15, 'Fantastyka naukowa', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (15, 'Science Fantasy', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (16, 'Literatura faktu', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (16, 'Non-fiction', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (17, 'Podróżnicza', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (17, 'Travel', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (18, 'Esej', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (18, 'Essay', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (19, 'Literatura dziecięca', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (19, 'Children''s Literature', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (20, 'Dystopia', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (20, 'Dystopia', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (21, 'Manga', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (21, 'Manga', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (22, 'Komiks', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (22, 'Comic', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (23, 'Thriller medyczny', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (23, 'Medical Thriller', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (24, 'Psychologiczny', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (24, 'Psychological', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (25, 'Detektywistyczny', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (25, 'Detective', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (26, 'Thriller polityczny', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (26, 'Political Thriller', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (27, 'Fantasy młodzieżowe', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (27, 'Young Adult Fantasy', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (28, 'Steampunk', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (28, 'Steampunk', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (29, 'Cyberpunk', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (29, 'Cyberpunk', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (30, 'Postapokaliptyczna', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (30, 'Post-apocalyptic', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (31, 'Magiczny realizm', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (31, 'Magical Realism', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (32, 'Romans historyczny', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (32, 'Historical Romance', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (33, 'Western', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (33, 'Western', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (34, 'Thriller wojenny', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (34, 'War Thriller', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (35, 'Epicka', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (35, 'Epic', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (36, 'Sensacyjna', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (36, 'Action', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (37, 'Literatura faktu historyczna', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (37, 'Historical Non-fiction', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (38, 'Satira', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (38, 'Satire', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (39, 'Mitologia', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (39, 'Mythology', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (40, 'Literatura podróżnicza historyczna', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (40, 'Historical Travel', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (41, 'Biograficzna sensacyjna', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (41, 'Action Biography', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (42, 'Fantastyka młodzieżowa', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (42, 'Young Adult Fantasy', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (43, 'Science Fiction młodzieżowe', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (43, 'Young Adult Sci-Fi', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (44, 'Literatura obyczajowa młodzieżowa', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (44, 'Young Adult Contemporary', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (45, 'Thriller psychologiczny', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (45, 'Psychological Thriller', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (46, 'Literatura kobieca', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (46, 'Women''s Fiction', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (47, 'Ekonomia', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (47, 'Economics', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (48, 'Polityka', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (48, 'Politics', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (49, 'Religia', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (49, 'Religion', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (50, 'Kultura i sztuka', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (50, 'Culture & Arts', 'en', NOW(), NULL);

INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (51, 'Informatyka', 'pl', NOW(), NULL);
INSERT INTO genre_translation (genre_id, name, language_code, created_at, updated_at)
VALUES (51, 'Computer Science', 'en', NOW(), NULL);


