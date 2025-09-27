insert into author_profile_picture (id, url, created_at)
values (1, 'https://s.lubimyczytac.pl/upload/texts/20800/20820/20820_1721308292_grafika800x600.jpg', now()),
       (2,
        'https://gfx.wiadomosci.radiozet.pl/var/g3-radiozetwiadomosci/storage/images/kultura/andrzej-sapkowski-napisal-nowego-wiedzmina-znamy-tytul-u-date-premiery/29434526-1-pol-PL/Andrzej-Sapkowski-napisal-nowego-Wiedzmina-.-Znamy-tytul-i-date-premiery_full-hd.png',
        now()),
       (3, 'https://www.gandalf.com.pl/blog/wp-content/uploads/2017/07/Remigiusz-Mroz.jpg', now()),
       (4, 'https://upload.wikimedia.org/wikipedia/commons/d/d4/J._R._R._Tolkien%2C_ca._1925.jpg', now()),
       (5, 'https://m.media-amazon.com/images/S/amzn-author-media-prod/8cigckin175jtpsk3gs361r4ss.jpg', now()),
       (6, 'https://upload.wikimedia.org/wikipedia/commons/2/27/Robert_C._Martin_surrounded_by_computers.jpg', now());


ALTER TABLE author
    ALTER COLUMN total_reviews SET DEFAULT 0,
    ALTER COLUMN average_rating SET DEFAULT 0.0;

INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (1, 'Olga Tokarczuk', 'Autor: Olga Tokarczuk. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at, author_profile_picture_id)
VALUES (2, 'Andrzej Sapkowski', 'Autor: Andrzej Sapkowski. Twórca literatury polskiej.', now(), NULL, 2);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (3, 'Stanisław Lem', 'Autor: Stanisław Lem. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (4, 'Wisława Szymborska', 'Autor: Wisława Szymborska. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (5, 'Czesław Miłosz', 'Autor: Czesław Miłosz. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (6, 'Henryk Sienkiewicz', 'Autor: Henryk Sienkiewicz. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (7, 'Bolesław Prus', 'Autor: Bolesław Prus. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (8, 'Stefan Żeromski', 'Autor: Stefan Żeromski. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (9, 'Władysław Reymont', 'Autor: Władysław Reymont. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (10, 'Bruno Schulz', 'Autor: Bruno Schulz. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (11, 'Zbigniew Herbert', 'Autor: Zbigniew Herbert. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (12, 'Tadeusz Różewicz', 'Autor: Tadeusz Różewicz. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (13, 'Juliusz Słowacki', 'Autor: Juliusz Słowacki. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (14, 'Julian Tuwim', 'Autor: Julian Tuwim. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (15, 'Maria Konopnicka', 'Autor: Maria Konopnicka. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (16, 'Magdalena Tulli', 'Autor: Magdalena Tulli. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (17, 'Ryszard Kapuściński', 'Autor: Ryszard Kapuściński. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (18, 'Gustaw Herling-Grudziński', 'Autor: Gustaw Herling-Grudziński. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (19, 'Zofia Nałkowska', 'Autor: Zofia Nałkowska. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (20, 'Adam Mickiewicz', 'Autor: Adam Mickiewicz. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (21, 'Sławomir Mrożek', 'Autor: Sławomir Mrożek. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (22, 'Dorota Masłowska', 'Autor: Dorota Masłowska. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (23, 'Joanna Bator', 'Autor: Joanna Bator. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (24, 'Mariusz Szczygieł', 'Autor: Mariusz Szczygieł. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (25, 'Remigiusz Mróz', 'Autor: Remigiusz Mróz. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (26, 'Jacek Dukaj', 'Autor: Jacek Dukaj. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (27, 'Marek Krajewski', 'Autor: Marek Krajewski. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (28, 'Zygmunt Miłoszewski', 'Autor: Zygmunt Miłoszewski. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (29, 'Michał Witkowski', 'Autor: Michał Witkowski. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (30, 'Katarzyna Bonda', 'Autor: Katarzyna Bonda. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (31, 'Anna Janko', 'Autor: Anna Janko. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (32, 'Jacek Dehnel', 'Autor: Jacek Dehnel. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (33, 'Marta Kisiel', 'Autor: Marta Kisiel. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (34, 'Ewa Nowak', 'Autor: Ewa Nowak. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (35, 'Marcin Szczygielski', 'Autor: Marcin Szczygielski. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (36, 'Paweł Huelle', 'Autor: Paweł Huelle. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (37, 'Joanna Chmielewska', 'Autor: Joanna Chmielewska. Twórca literatury polskiej.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (38, 'Grzegorz Wójcik', 'Pisarz i autor kilkunastu książek: Grzegorz Wójcik.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (39, 'Piotr Rutkowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (40, 'Tomasz Zieliński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (41, 'Ewa Kowalczyk', 'Pisarz i autor kilkunastu książek: Ewa Kowalczyk.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (42, 'Filip Wójcik', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (43, 'Aleksandra Rutkowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (44, 'Natalia Wiśniewski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (45, 'Maciej Krawczyk', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (46, 'Anna Kowalski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (47, 'Katarzyna Lewandowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (48, 'Ewa Grabowski', 'Pisarz i autor kilkunastu książek: Ewa Grabowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (49, 'Barbara Kowalski', 'Pisarz i autor kilkunastu książek: Barbara Kowalski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (50, 'Natalia Lewandowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (51, 'Mateusz Jaworski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (52, 'Mateusz Olszewski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (53, 'Monika Zieliński', 'Pisarz i autor kilkunastu książek: Monika Zieliński.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (54, 'Sylwia Dąbrowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (55, 'Tomasz Czarnecki', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (56, 'Piotr Michalski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (57, 'Agnieszka Walczak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (58, 'Monika Kozłowski', 'Pisarz i autor kilkunastu książek: Monika Kozłowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (59, 'Tomasz Kowalczyk', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (60, 'Marcin Malinowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (61, 'Julia Kozłowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (62, 'Michał Wiśniewski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (63, 'Krzysztof Wójcik', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (64, 'Joanna Górski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (65, 'Joanna Lis', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (66, 'Anna Rutkowski', 'Pisarz i autor kilkunastu książek: Anna Rutkowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (67, 'Sylwia Olszewski', 'Pisarz i autor kilkunastu książek: Sylwia Olszewski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (68, 'Michał Baran', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (69, 'Krzysztof Wiśniewski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (70, 'Natalia Woźniak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (71, 'Grzegorz Lis', 'Pisarz i autor kilkunastu książek: Grzegorz Lis.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (72, 'Joanna Dąbrowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (73, 'Marcin Walczak', 'Pisarz i autor kilkunastu książek: Marcin Walczak.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (74, 'Katarzyna Nowak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (75, 'Aleksandra Zieliński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (76, 'Julia Woźniak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (77, 'Katarzyna Górski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (78, 'Ewa Górski', 'Pisarz i autor kilkunastu książek: Ewa Górski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (79, 'Michał Mazur', 'Pisarz i autor kilkunastu książek: Michał Mazur.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (80, 'Tomasz Król', 'Pisarz i autor kilkunastu książek: Tomasz Król.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (81, 'Grzegorz Piotrowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (82, 'Joanna Kamiński', 'Pisarz i autor kilkunastu książek: Joanna Kamiński.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (83, 'Joanna Jankowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (84, 'Marcin Sikora', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (85, 'Tomasz Walczak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (86, 'Aleksandra Jaworski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (87, 'Katarzyna Lis', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (88, 'Grzegorz Kamiński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (89, 'Natalia Rutkowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (90, 'Ewa Kamiński', 'Pisarz i autor kilkunastu książek: Ewa Kamiński.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (91, 'Sylwia Mazur', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (92, 'Tomasz Baran', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (93, 'Grzegorz Walczak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (94, 'Natalia Zieliński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (95, 'Aleksandra Kozłowski', 'Pisarz i autor kilkunastu książek: Aleksandra Kozłowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (96, 'Julia Michalski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (97, 'Anna Zieliński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (98, 'Anna Czarnecki', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (99, 'Paweł Mazur', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (100, 'Tomasz Wiśniewski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (101, 'Marcin Baran', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (102, 'Maciej Witkowski', 'Pisarz i autor kilkunastu książek: Maciej Witkowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (103, 'Mateusz Kozłowski', 'Pisarz i autor kilkunastu książek: Mateusz Kozłowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (104, 'Marcin Jaworski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (105, 'Adam Mazur', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (106, 'Grzegorz Król', 'Pisarz i autor kilkunastu książek: Grzegorz Król.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (107, 'Jacek Szymański', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (108, 'Jacek Zieliński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (109, 'Filip Olszewski', 'Pisarz i autor kilkunastu książek: Filip Olszewski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (110, 'Natalia Szymański', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (111, 'Filip Dąbrowski', 'Pisarz i autor kilkunastu książek: Filip Dąbrowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (112, 'Monika Witkowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (113, 'Maciej Mazur', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (114, 'Joanna Zieliński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (115, 'Jacek Grabowski', 'Pisarz i autor kilkunastu książek: Jacek Grabowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (116, 'Adam Wiśniewski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (117, 'Julia Nowak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (118, 'Michał Kowalczyk', 'Pisarz i autor kilkunastu książek: Michał Kowalczyk.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (119, 'Aleksandra Krawczyk', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (120, 'Barbara Wiśniewski', 'Pisarz i autor kilkunastu książek: Barbara Wiśniewski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (121, 'Krzysztof Mazur', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (122, 'Barbara Król', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (123, 'Łukasz Szymański', 'Pisarz i autor kilkunastu książek: Łukasz Szymański.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (124, 'Natalia Górski', 'Pisarz i autor kilkunastu książek: Natalia Górski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (125, 'Piotr Sikora', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (126, 'Aleksandra Witkowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (127, 'Natalia Michalski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (128, 'Tomasz Michalski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (129, 'Grzegorz Kozłowski', 'Pisarz i autor kilkunastu książek: Grzegorz Kozłowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (130, 'Michał Woźniak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (131, 'Monika Kamiński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (132, 'Sylwia Kowalski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (133, 'Filip Witkowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (134, 'Filip Szymański', 'Pisarz i autor kilkunastu książek: Filip Szymański.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (135, 'Łukasz Michalski', 'Pisarz i autor kilkunastu książek: Łukasz Michalski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (136, 'Agnieszka Grabowski', 'Pisarz i autor kilkunastu książek: Agnieszka Grabowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (137, 'Michał Górski', 'Pisarz i autor kilkunastu książek: Michał Górski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (138, 'Grzegorz Woźniak', 'Pisarz i autor kilkunastu książek: Grzegorz Woźniak.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (139, 'Grzegorz Grabowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (140, 'Barbara Lewandowski', 'Pisarz i autor kilkunastu książek: Barbara Lewandowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (141, 'Jacek Jankowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (142, 'Julia Kamiński', 'Pisarz i autor kilkunastu książek: Julia Kamiński.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (143, 'Natalia Malinowski', 'Pisarz i autor kilkunastu książek: Natalia Malinowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (144, 'Julia Baran', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (145, 'Łukasz Baran', 'Pisarz i autor kilkunastu książek: Łukasz Baran.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (146, 'Piotr Lis', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (147, 'Paweł Pawlak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (148, 'Piotr Wójcik', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (149, 'Joanna Witkowski', 'Pisarz i autor kilkunastu książek: Joanna Witkowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (150, 'Marta Zieliński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (151, 'Maciej Malinowski', 'Pisarz i autor kilkunastu książek: Maciej Malinowski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (152, 'Katarzyna Wiśniewski', 'Pisarz i autor kilkunastu książek: Katarzyna Wiśniewski.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (153, 'Filip Pawlak', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (154, 'Katarzyna Michalski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (155, 'Jacek Kowalczyk', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (156, 'Aleksandra Pawlak', 'Pisarz i autor kilkunastu książek: Aleksandra Pawlak.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (157, 'Natalia Kamiński', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (158, 'Tomasz Grabowski', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (159, 'Barbara Krawczyk', NULL, now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at)
VALUES (160, 'Filip Walczak', 'Pisarz i autor kilkunastu książek: Filip Walczak.', now(), NULL);
INSERT INTO author (id, full_name, description, created_at, updated_at, author_profile_picture_id)
VALUES (161, 'Stephen King',
        'amerykański pisarz specjalizujący się w literaturze grozy. W przeszłości wydawał książki pod pseudonimem Richard Bachman, oraz jako John Swithen.',
        now(), NULL, 1);

INSERT INTO author (id, full_name, description, created_at, updated_at, author_profile_picture_id)
VALUES (162, 'Remigiusz Mróz',
        'polski pisarz i prawnik; autor kilkudziesięciu powieści oraz cyklu publicystycznego „Kurs pisania”, laureat Nagrody Czytelników Wielkiego Kalibru z 2016 za powieść pt. Kasacja.',
        now(), NULL, 3);

INSERT INTO author (id, full_name, description, created_at, updated_at, author_profile_picture_id)
VALUES (163, 'J.R.R. Tolkien',
        'rytyjski pisarz oraz profesor filologii klasycznej i literatury staroangielskiej na Uniwersytecie Oksfordzkim. Jako autor powieści Władca Pierścieni, której akcja rozgrywa się w mitycznym świecie Śródziemia, spopularyzował literaturę fantasy.',
        now(), NULL, 4);

INSERT INTO author (id, full_name, description, created_at, updated_at, author_profile_picture_id)
VALUES (164, 'J.K. Rowling',
        'brytyjska pisarka, scenarzystka, producentka filmowa i telewizyjna, filantropka, a wcześniej nauczycielka języka angielskiego. Sławę przyniosła jej seria powieści fantasy Harry Potter, której sprzedaż przekroczyła pół miliarda egzemplarzy.',
        now(), NULL, 5);
INSERT INTO author (id, full_name, description, created_at, updated_at, author_profile_picture_id)
VALUES (165, 'Robert C. Martin',
        'amerykański programista i autor wielu książek dotyczących inżynierii oprogramowania. Zwany w środowisku programistów jako „wujek Bob”. Zaproponował szeroko przyjęty mnemonik na pięć podstawowych założeń programowania obiektowego – SOLID.',
        now(), NULL, 6);


SELECT setval(pg_get_serial_sequence('author', 'id'), (select MAX(id) from author), true);

ALTER TABLE author
    ALTER COLUMN total_reviews DROP DEFAULT,
    ALTER COLUMN average_rating DROP DEFAULT;