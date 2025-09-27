insert into bookshelf(user_id, name, type, description, created_at)
values (1, 'Do przeczytania', 'TO_READ', 'Regał przeznaczony do gromadzenia książek do przeczytania.', now()),
       (1, 'W trakcie czytania', 'READING', 'Regał przeznaczony do gromadzenia książek w trakcie czytania.', now()),
       (1, 'Przeczytanie', 'READ', 'Regał przeznaczony do gromadzenia książek przeczytanych.', now());


INSERT INTO reading_book (bookshelf_id, book_id, status, current_page, total_notes, total_sessions, started_reading_at,
                          finished_reading_at, created_at, updated_at)
VALUES
-- bookshelf 3 - Przeczytane
(3, 301, 'READ', 288, 0, 0, '2025-07-01', '2025-07-10', now(), NULL),
(3, 302, 'READ', 384, 0, 0, '2025-07-11', '2025-07-25', now(), NULL),
(3, 303, 'READ', 320, 0, 0, '2025-07-26', '2025-08-05', now(), NULL),

-- bookshelf 2 - W trakcie czytania
(2, 304, 'READING', 150, 0, 0, '2025-09-01', NULL, now(), NULL),
(2, 305, 'READING', 200, 0, 0, '2025-09-05', NULL, now(), NULL),
(2, 306, 'READING', 90, 0, 0, '2025-09-10', NULL, now(), NULL),

-- bookshelf 1 - Do przeczytania
(1, 307, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now(), NULL),
(1, 308, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now(), NULL);


INSERT INTO reading_note (reading_book_id, title, content, page_from, page_to, created_at, updated_at)
VALUES
-- Book_id 301
(1, 'Początek opowieści', 'Świetne wprowadzenie do świata Wiedźmina.', 1, 20, now(), NULL),
(1, 'Charakterystyka Geralta', 'Opis Geralta robi ogromne wrażenie.', 50, 70, now(), NULL),
(1, 'Relacje z Yennefer', 'Pierwsze zarysy relacji między bohaterami.', 120, 140, now(), NULL),
(1, 'Wątek polityczny', 'Interesujące tło polityczne w opowiadaniach.', 180, 200, now(), NULL),
(1, 'Zakończenie', 'Mocne i zaskakujące zakończenie tomu.', 260, 288, now(), NULL),

-- Book_id 302
(2, 'Opowiadanie tytułowe', '„Miecz przeznaczenia” wywołuje wiele emocji.', 1, 30, now(), NULL),
(2, 'Rozwój postaci Ciri', 'Pierwsze sygnały ważnej roli Ciri.', 80, 100, now(), NULL),
(2, 'Humor i ironia', 'Drobne wstawki humorystyczne rozładowują napięcie.', 160, 180, now(), NULL),
(2, 'Motyw przeznaczenia', 'Przeznaczenie staje się centralnym motywem.', 250, 270, now(), NULL),
(2, 'Klimatyczny finał', 'Finał pozostawia czytelnika w zadumie.', 350, 384, now(), NULL),

-- Book_id 303
(3, 'Nowa forma narracji', 'Pierwsza powieść pełnowymiarowa w serii.', 1, 25, now(), NULL),
(3, 'Ciri w Kaer Morhen', 'Trening Ciri wśród wiedźminów.', 60, 80, now(), NULL),
(3, 'Relacja Geralt – Ciri', 'Pojawia się prawdziwa więź między nimi.', 140, 160, now(), NULL),
(3, 'Magia i polityka', 'Rozszerzający się świat czarodziejek i królów.', 200, 220, now(), NULL),
(3, 'Mocne zakończenie', 'Zakończenie buduje napięcie na kolejne tomy.', 290, 320, now(), NULL),

-- Book_id 304
(4, 'Otwarcie tomu', 'Mocne otwarcie i kontynuacja wątku Ciri.', 1, 20, now(), NULL),
(4, 'Polityczne intrygi', 'Silne tło polityczne i intrygi czarodziejów.', 40, 60, now(), NULL),
(4, 'Dynamiczna akcja', 'Fragmenty akcji są bardzo żywe i szczegółowe.', 80, 100, now(), NULL),
(4, 'Ciri na rozdrożu', 'Ciri staje przed ważnymi wyborami.', 110, 130, now(), NULL),
(4, 'Ostatnie przeczytane strony', 'Zatrzymałem się na istotnym momencie fabuły.', 140, 150, now(), NULL),

-- Book_id 305
(5, 'Początek podróży', 'Geralt wyrusza w kolejną podróż.', 1, 20, now(), NULL),
(5, 'Spotkanie z nowymi postaciami', 'Nowe, barwne postacie pojawiają się w historii.', 40, 70, now(), NULL),
(5, 'Walka i napięcie', 'Dobrze opisane sceny walki.', 100, 130, now(), NULL),
(5, 'Zagrożenie dla bohaterów', 'Pojawiają się poważne wyzwania.', 150, 170, now(), NULL),
(5, 'Kolejne zatrzymanie lektury', 'Skończone czytanie w połowie książki.', 190, 200, now(), NULL),

-- Book_id 306
(6, 'Nowa perspektywa', 'Opowieść zaczyna się od interesującego wątku.', 1, 15, now(), NULL),
(6, 'Atmosfera tajemnicy', 'Nastrój książki od samego początku jest mroczny.', 20, 40, now(), NULL),
(6, 'Rozwój akcji', 'Pierwsze ważne wydarzenia fabularne.', 45, 65, now(), NULL),
(6, 'Analiza bohaterów', 'Postacie są przedstawione wielowymiarowo.', 70, 85, now(), NULL),
(6, 'Notatka kończąca aktualny etap', 'Skończyłem czytać na tej stronie.', 86, 90, now(), NULL);


INSERT INTO reading_session (reading_book_id, page_from, page_to, started_reading_at, finished_reading_at, description,
                             created_at, updated_at)
VALUES
-- Book_id 301 (READ, 288 stron)
(1, 1, 60, '2024-01-05 18:00', '2024-01-05 19:30', 'Pierwsze spotkanie z Geraltem.', now(), NULL),
(1, 61, 130, '2024-01-06 20:00', '2024-01-06 21:30', 'Wciągające opowiadania.', now(), NULL),
(1, 131, 200, '2024-01-07 18:30', '2024-01-07 20:00', 'Coraz więcej polityki.', now(), NULL),
(1, 201, 288, '2024-01-08 19:00', '2024-01-08 20:30', 'Mocne zakończenie.', now(), NULL),

-- Book_id 302 (READ, 384 stron)
(2, 1, 80, '2024-02-01 17:00', '2024-02-01 19:00', 'Rozpoczęcie drugiego tomu.', now(), NULL),
(2, 81, 160, '2024-02-02 18:00', '2024-02-02 20:00', 'Wątek Ciri nabiera znaczenia.', now(), NULL),
(2, 161, 260, '2024-02-03 19:00', '2024-02-03 21:00', 'Świetne opisy akcji.', now(), NULL),
(2, 261, 384, '2024-02-04 20:00', '2024-02-04 22:00', 'Poruszające zakończenie.', now(), NULL),

-- Book_id 303 (READ, 320 stron)
(3, 1, 100, '2024-03-10 15:00', '2024-03-10 17:00', 'Pierwsza większa powieść wciąga.', now(), NULL),
(3, 101, 200, '2024-03-11 16:00', '2024-03-11 18:00', 'Relacje Geralt–Ciri rozwijają się.', now(), NULL),
(3, 201, 320, '2024-03-12 17:00', '2024-03-12 19:00', 'Doskonałe zakończenie tomu.', now(), NULL),

-- Book_id 304 (READING, 336 stron, current_page=150)
(4, 1, 50, '2024-04-01 19:00', '2024-04-01 20:00', 'Początek lektury.', now(), NULL),
(4, 51, 100, '2024-04-02 18:30', '2024-04-02 19:30', 'Świetne wątki polityczne.', now(), NULL),
(4, 101, 150, '2024-04-03 20:00', '2024-04-03 21:00', 'Zatrzymałem się na tym etapie.', now(), NULL),

-- Book_id 305 (READING, 376 stron, current_page=200)
(5, 1, 70, '2024-05-01 17:00', '2024-05-01 18:30', 'Nowa podróż Geralta.', now(), NULL),
(5, 71, 140, '2024-05-02 18:00', '2024-05-02 19:30', 'Dynamiczne sceny walki.', now(), NULL),
(5, 141, 200, '2024-05-03 19:00', '2024-05-03 20:30', 'Na tym etapie zakończyłem czytanie.', now(), NULL),

-- Book_id 306 (READING, 384 stron, current_page=90)
(6, 1, 30, '2024-06-01 18:00', '2024-06-01 18:45', 'Interesujący początek.', now(), NULL),
(6, 31, 60, '2024-06-02 19:00', '2024-06-02 19:45', 'Coraz bardziej mroczny klimat.', now(), NULL),
(6, 61, 90, '2024-06-03 20:00', '2024-06-03 20:45', 'Tu skończyłem lekturę.', now(), NULL);


-- READING_BOOK
INSERT INTO reading_book (id, bookshelf_id, book_id, status, current_page, total_notes, total_sessions,
                          started_reading_at, finished_reading_at, created_at, updated_at)
VALUES (7, 3, 310, 'READ', 823, 5, 4, '2023-01-05', '2023-02-15', now(), NULL),
       (8, 3, 311, 'READ', 640, 5, 3, '2023-03-01', '2023-03-28', now(), NULL),
       (9, 3, 312, 'READ', 515, 5, 3, '2023-04-10', '2023-05-02', now(), NULL),
       (10, 3, 313, 'READ', 790, 5, 4, '2023-06-01', '2023-07-05', now(), NULL),

       (11, 2, 314, 'READING', 230, 5, 3, '2024-01-10', NULL, now(), NULL),
       (12, 2, 315, 'READING', 120, 5, 2, '2024-02-01', NULL, now(), NULL),
       (13, 2, 316, 'READING', 340, 5, 4, '2024-03-05', NULL, now(), NULL),
       (14, 2, 317, 'READING', 90, 5, 2, '2024-03-20', NULL, now(), NULL),

       (15, 1, 318, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now(), NULL),
       (16, 1, 319, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now(), NULL),
       (17, 1, 320, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now(), NULL),
       (18, 1, 321, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now(), NULL),
       (19, 1, 322, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now(), NULL),
       (20, 1, 323, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now(), NULL);

-- READING_NOTE
INSERT INTO reading_note (reading_book_id, title, content, page_from, page_to, created_at, updated_at)
VALUES
-- book_id 310
(7, 'Mocny początek', 'Świetne otwarcie, od razu buduje napięcie.', 1, 50, now(), NULL),
(7, 'Pierwszy zwrot akcji', 'Nieoczekiwany moment zmienia całą historię.', 120, 150, now(), NULL),
(7, 'Rozwinięcie postaci', 'Bardzo ciekawe tło psychologiczne bohatera.', 200, 250, now(), NULL),
(7, 'Klimatyczny opis', 'Opis małego miasteczka bardzo sugestywny.', 400, 420, now(), NULL),
(7, 'Finał', 'Zakończenie trzymające w napięciu do końca.', 780, 823, now(), NULL),

-- book_id 311
(8, 'Wciągający początek', 'Od razu widać mistrzowski styl Kinga.', 1, 30, now(), NULL),
(8, 'Budowanie atmosfery', 'Mrok i napięcie w opisach przyrody.', 100, 150, now(), NULL),
(8, 'Charakter antagonisty', 'Postać złoczyńcy wyjątkowo przerażająca.', 200, 250, now(), NULL),
(8, 'Kulminacja', 'Akcja nabiera ogromnego tempa.', 500, 600, now(), NULL),
(8, 'Zakończenie', 'Świetny epilog, pozostawia niedosyt.', 620, 640, now(), NULL),

-- book_id 312
(9, 'Otwarcie', 'Intrygujący początek historii.', 1, 20, now(), NULL),
(9, 'Relacje bohaterów', 'Świetnie oddane napięcia między postaciami.', 150, 180, now(), NULL),
(9, 'Pierwszy dramat', 'Moment, który naprawdę wstrząsa czytelnikiem.', 250, 280, now(), NULL),
(9, 'Opis miejsc', 'King genialnie buduje mroczny klimat.', 350, 370, now(), NULL),
(9, 'Finałowe sceny', 'Trzyma w napięciu aż do ostatniej strony.', 480, 515, now(), NULL),

-- book_id 313
(10, 'Wstęp', 'Prolog wciąga od pierwszej strony.', 1, 40, now(), NULL),
(10, 'Rozwinięcie', 'Bardzo interesująca narracja i budowa świata.', 200, 230, now(), NULL),
(10, 'Wątek poboczny', 'Dobrze uzupełnia główną historię.', 350, 380, now(), NULL),
(10, 'Kulminacja', 'Prawdziwy rollercoaster emocji.', 700, 750, now(), NULL),
(10, 'Zakończenie', 'Mocne i satysfakcjonujące.', 770, 790, now(), NULL),

-- book_id 314
(11, 'Start', 'Dynamiczne rozpoczęcie historii.', 1, 20, now(), NULL),
(11, 'Postać główna', 'Coraz ciekawsze rysy psychologiczne.', 100, 130, now(), NULL),
(11, 'Zwrot akcji', 'Historia zmienia się o 180 stopni.', 150, 180, now(), NULL),
(11, 'Nowy wątek', 'Wprowadza ciekawą dynamikę.', 200, 230, now(), NULL),
(11, 'Fragment pełen grozy', 'Jeden z najlepszych momentów.', 220, 230, now(), NULL),

-- book_id 315
(12, 'Otwarcie', 'Lekkość stylu, ale czuć narastający mrok.', 1, 15, now(), NULL),
(12, 'Pierwsze napięcia', 'Pojawiają się konflikty między bohaterami.', 80, 100, now(), NULL),
(12, 'Wstrząs', 'Szokujący moment zmienia kierunek opowieści.', 110, 120, now(), NULL),
(12, 'Nowa relacja', 'Bardzo ciekawa dynamika między postaciami.', 60, 90, now(), NULL),
(12, 'Scena w półmroku', 'Dreszcz przechodzi podczas czytania.', 110, 120, now(), NULL),

-- book_id 316
(13, 'Początek', 'Wprowadzenie do historii z klimatem grozy.', 1, 30, now(), NULL),
(13, 'Pierwsza tragedia', 'Szokująca scena budująca atmosferę.', 100, 140, now(), NULL),
(13, 'Rozwój bohatera', 'Ciekawa przemiana postaci głównej.', 200, 250, now(), NULL),
(13, 'Napięcie', 'Akcja staje się coraz bardziej intensywna.', 300, 330, now(), NULL),
(13, 'Cliffhanger', 'Pozostawia czytelnika z pytaniami.', 320, 340, now(), NULL),

-- book_id 317
(14, 'Wprowadzenie', 'Pierwsze strony dobrze nastrajają.', 1, 20, now(), NULL),
(14, 'Zarys świata', 'Świetnie opisane realia.', 50, 70, now(), NULL),
(14, 'Pierwsze konflikty', 'Postacie zaczynają się ścierać.', 80, 90, now(), NULL),
(14, 'Nowy motyw', 'Dodaje świeżości do historii.', 60, 80, now(), NULL),
(14, 'Scena grozy', 'Mocny moment pełen napięcia.', 85, 90, now(), NULL);

-- READING_SESSION
INSERT INTO reading_session (reading_book_id, page_from, page_to, started_reading_at, finished_reading_at, description,
                             created_at, updated_at)
VALUES
-- book_id 310
(7, 1, 200, '2023-01-05 18:00', '2023-01-05 20:00', 'Pierwsze podejście.', now(), NULL),
(7, 201, 400, '2023-01-10 19:00', '2023-01-10 21:30', 'Dłuższa lektura.', now(), NULL),
(7, 401, 600, '2023-01-20 20:00', '2023-01-20 22:00', 'Wieczór z książką.', now(), NULL),
(7, 601, 823, '2023-02-15 18:00', '2023-02-15 21:00', 'Finałowa sesja.', now(), NULL),

-- book_id 311
(8, 1, 200, '2023-03-01 17:00', '2023-03-01 19:00', 'Mocne rozpoczęcie.', now(), NULL),
(8, 201, 400, '2023-03-10 19:00', '2023-03-10 21:30', 'Środek historii.', now(), NULL),
(8, 401, 640, '2023-03-28 20:00', '2023-03-28 23:00', 'Zakończenie.', now(), NULL),

-- book_id 312
(9, 1, 150, '2023-04-10 18:00', '2023-04-10 20:00', 'Start książki.', now(), NULL),
(9, 151, 350, '2023-04-18 19:00', '2023-04-18 21:00', 'Rozwój akcji.', now(), NULL),
(9, 351, 515, '2023-05-02 18:00', '2023-05-02 21:00', 'Zakończenie.', now(), NULL),

-- book_id 313
(10, 1, 200, '2023-06-01 17:00', '2023-06-01 19:00', 'Pierwsze wrażenia.', now(), NULL),
(10, 201, 400, '2023-06-15 20:00', '2023-06-15 22:30', 'Środek historii.', now(), NULL),
(10, 401, 600, '2023-06-25 19:00', '2023-06-25 21:30', 'Akcja nabiera tempa.', now(), NULL),
(10, 601, 790, '2023-07-05 18:00', '2023-07-05 22:00', 'Wielki finał.', now(), NULL),

-- book_id 314
(11, 1, 80, '2024-01-10 18:00', '2024-01-10 19:30', 'Rozpoczęcie czytania.', now(), NULL),
(11, 81, 160, '2024-01-15 19:00', '2024-01-15 20:30', 'Kolejna sesja.', now(), NULL),
(11, 161, 230, '2024-01-20 20:00', '2024-01-20 22:00', 'Zatrzymanie się w połowie.', now(), NULL),

-- book_id 315
(12, 1, 60, '2024-02-01 18:00', '2024-02-01 19:00', 'Pierwsze strony.', now(), NULL),
(12, 61, 120, '2024-02-10 19:00', '2024-02-10 20:30', 'Dalsza lektura.', now(), NULL),

-- book_id 316
(13, 1, 120, '2024-03-05 18:00', '2024-03-05 20:00', 'Rozpoczęcie książki.', now(), NULL),
(13, 121, 220, '2024-03-12 19:00', '2024-03-12 21:00', 'Środek akcji.', now(), NULL),
(13, 221, 300, '2024-03-18 20:00', '2024-03-18 21:30', 'Kolejne wydarzenia.', now(), NULL),
(13, 301, 340, '2024-03-25 18:00', '2024-03-25 19:30', 'Ostatnie strony na razie.', now(), NULL),

-- book_id 317
(14, 1, 40, '2024-03-20 18:00', '2024-03-20 19:00', 'Wstęp.', now(), NULL),
(14, 41, 70, '2024-03-22 19:00', '2024-03-22 20:00', 'Dalsze strony.', now(), NULL),
(14, 71, 90, '2024-03-25 20:00', '2024-03-25 21:00', 'Zatrzymanie się na krótkim fragmencie.', now(), NULL);


-- reading_book
INSERT INTO reading_book(bookshelf_id, book_id, status, current_page, total_notes, total_sessions, started_reading_at,
                         finished_reading_at, created_at)
VALUES
-- READ
(1, 324, 'READ', 410, 5, 3, '2024-01-01 08:00:00', '2024-01-10 20:00:00', now()),
(1, 325, 'READ', 387, 5, 3, '2024-01-02 09:00:00', '2024-01-11 21:00:00', now()),
(1, 326, 'READ', 432, 5, 4, '2024-01-03 07:30:00', '2024-01-12 19:00:00', now()),
(1, 327, 'READ', 398, 5, 3, '2024-01-04 10:00:00', '2024-01-13 18:30:00', now()),
(1, 328, 'READ', 456, 5, 4, '2024-01-05 08:15:00', '2024-01-14 20:00:00', now()),
(1, 329, 'READ', 430, 5, 3, '2024-01-06 09:45:00', '2024-01-15 19:45:00', now()),

-- READING
(2, 330, 'READING', 200, 5, 2, '2024-09-01 08:00:00', NULL, now()),
(2, 331, 'READING', 215, 5, 2, '2024-09-02 09:00:00', NULL, now()),
(2, 332, 'READING', 190, 5, 2, '2024-09-03 07:30:00', NULL, now()),
(2, 333, 'READING', 230, 5, 2, '2024-09-04 10:00:00', NULL, now()),
(2, 334, 'READING', 250, 5, 3, '2024-09-05 08:15:00', NULL, now()),
(2, 335, 'READING', 240, 5, 2, '2024-09-06 09:45:00', NULL, now()),

-- WANT_TO_READ
(3, 336, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now()),
(3, 337, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now()),
(3, 338, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now()),
(3, 339, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now()),
(3, 340, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now()),
(3, 341, 'WANT_TO_READ', 0, 0, 0, NULL, NULL, now());

-- reading_note (READ + READING)
INSERT INTO reading_note(reading_book_id, title, content, page_from, page_to, created_at)
VALUES
-- READ notes
(21, 'Notatka 1', 'Interesujące zdarzenie w fabule.', 1, 82, now()),
(21, 'Notatka 2', 'Opis świata i bohaterów.', 83, 164, now()),
(21, 'Notatka 3', 'Ważne zwroty akcji.', 165, 246, now()),
(21, 'Notatka 4', 'Refleksja nad bohaterami.', 247, 328, now()),
(21, 'Notatka 5', 'Podsumowanie wątków.', 329, 410, now()),

(22, 'Notatka 1', 'Rozpoczęcie przygody.', 1, 77, now()),
(22, 'Notatka 2', 'Nowi bohaterowie.', 78, 154, now()),
(22, 'Notatka 3', 'Napięcie rośnie.', 155, 231, now()),
(22, 'Notatka 4', 'Przełomowy moment.', 232, 308, now()),
(22, 'Notatka 5', 'Końcowe refleksje.', 309, 387, now()),

(23, 'Notatka 1', 'Nowe miejsce akcji.', 1, 108, now()),
(23, 'Notatka 2', 'Szczegóły fabuły.', 109, 216, now()),
(23, 'Notatka 3', 'Konflikty bohaterów.', 217, 324, now()),
(23, 'Notatka 4', 'Decydujące wydarzenie.', 325, 432, now()),
(23, 'Notatka 5', 'Podsumowanie.', 433, 432, now()),

(24, 'Notatka 1', 'Opis bohatera.', 1, 100, now()),
(24, 'Notatka 2', 'Pierwszy zwrot akcji.', 101, 200, now()),
(24, 'Notatka 3', 'Napięcie rośnie.', 201, 298, now()),
(24, 'Notatka 4', 'Klimatyczne wydarzenie.', 299, 398, now()),
(24, 'Notatka 5', 'Zakończenie wątku.', 399, 398, now()),

(25, 'Notatka 1', 'Wprowadzenie w świat.', 1, 114, now()),
(25, 'Notatka 2', 'Relacje bohaterów.', 115, 228, now()),
(25, 'Notatka 3', 'Konflikt główny.', 229, 342, now()),
(25, 'Notatka 4', 'Punkt kulminacyjny.', 343, 456, now()),
(25, 'Notatka 5', 'Refleksje po zakończeniu.', 457, 456, now()),

(26, 'Notatka 1', 'Nowy wątek.', 1, 108, now()),
(26, 'Notatka 2', 'Interakcje bohaterów.', 109, 216, now()),
(26, 'Notatka 3', 'Kluczowa decyzja.', 217, 324, now()),
(26, 'Notatka 4', 'Konfrontacja.', 325, 432, now()),
(26, 'Notatka 5', 'Podsumowanie wątków.', 433, 432, now()),

-- READING notes
(27, 'Notatka 1', 'Pierwsze strony.', 1, 50, now()),
(27, 'Notatka 2', 'Rozwój fabuły.', 51, 100, now()),
(27, 'Notatka 3', 'Napięcie rośnie.', 101, 150, now()),
(27, 'Notatka 4', 'Interakcje bohaterów.', 151, 200, now()),
(27, 'Notatka 5', 'Punkty zwrotne.', 201, 200, now()),

(28, 'Notatka 1', 'Wprowadzenie.', 1, 50, now()),
(28, 'Notatka 2', 'Rozwój akcji.', 51, 100, now()),
(28, 'Notatka 3', 'Zaskakujące wydarzenia.', 101, 150, now()),
(28, 'Notatka 4', 'Analiza bohaterów.', 151, 200, now()),
(28, 'Notatka 5', 'Podsumowanie wątku.', 201, 200, now()),

(29, 'Notatka 1', 'Pierwsze strony.', 1, 47, now()),
(29, 'Notatka 2', 'Napięcie.', 48, 94, now()),
(29, 'Notatka 3', 'Interakcje bohaterów.', 95, 141, now()),
(29, 'Notatka 4', 'Decydujący moment.', 142, 190, now()),
(29, 'Notatka 5', 'Podsumowanie.', 191, 190, now()),

(30, 'Notatka 1', 'Start historii.', 1, 58, now()),
(30, 'Notatka 2', 'Wydarzenia.', 59, 116, now()),
(30, 'Notatka 3', 'Rozwój bohaterów.', 117, 174, now()),
(30, 'Notatka 4', 'Klimat.', 175, 230, now()),
(30, 'Notatka 5', 'Podsumowanie.', 231, 230, now()),

(31, 'Notatka 1', 'Początek.', 1, 50, now()),
(31, 'Notatka 2', 'Fabuła.', 51, 100, now()),
(31, 'Notatka 3', 'Zwroty akcji.', 101, 150, now()),
(31, 'Notatka 4', 'Analiza.', 151, 200, now()),
(31, 'Notatka 5', 'Wnioski.', 201, 200, now()),

(32, 'Notatka 1', 'Pierwsze strony.', 1, 63, now()),
(32, 'Notatka 2', 'Interakcje bohaterów.', 64, 126, now()),
(32, 'Notatka 3', 'Rozwój wydarzeń.', 127, 189, now()),
(32, 'Notatka 4', 'Konflikty.', 190, 252, now()),
(32, 'Notatka 5', 'Podsumowanie wątku.', 253, 252, now()),

(33, 'Notatka 1', 'Początek.', 1, 50, now()),
(33, 'Notatka 2', 'Fabuła.', 51, 100, now()),
(33, 'Notatka 3', 'Zwroty akcji.', 101, 150, now()),
(33, 'Notatka 4', 'Analiza bohaterów.', 151, 200, now()),
(33, 'Notatka 5', 'Wnioski.', 201, 200, now()),

(34, 'Notatka 1', 'Start książki.', 1, 50, now()),
(34, 'Notatka 2', 'Rozwój wątku.', 51, 100, now()),
(34, 'Notatka 3', 'Napięcie.', 101, 150, now()),
(34, 'Notatka 4', 'Klimat.', 151, 200, now()),
(34, 'Notatka 5', 'Podsumowanie.', 201, 200, now()),

(35, 'Notatka 1', 'Pierwsze strony.', 1, 50, now()),
(35, 'Notatka 2', 'Fabuła.', 51, 100, now()),
(35, 'Notatka 3', 'Zwroty akcji.', 101, 150, now()),
(35, 'Notatka 4', 'Analiza.', 151, 200, now()),
(35, 'Notatka 5', 'Wnioski.', 201, 200, now()),

(36, 'Notatka 1', 'Początek.', 1, 50, now()),
(36, 'Notatka 2', 'Fabuła.', 51, 100, now()),
(36, 'Notatka 3', 'Zwroty akcji.', 101, 150, now()),
(36, 'Notatka 4', 'Analiza.', 151, 200, now()),
(36, 'Notatka 5', 'Wnioski.', 201, 200, now());

-- reading_session (READ + READING)
INSERT INTO reading_session(reading_book_id, page_from, page_to, started_reading_at, finished_reading_at, description,
                            created_at)
VALUES
-- READ sessions
(21, 1, 82, '2024-01-01 08:00:00', '2024-01-02 10:00:00', 'Pierwsza sesja', now()),
(21, 83, 164, '2024-01-02 11:00:00', '2024-01-03 12:00:00', 'Druga sesja', now()),
(21, 165, 246, '2024-01-03 13:00:00', '2024-01-04 14:00:00', 'Trzecia sesja', now()),

(22, 1, 77, '2024-01-02 09:00:00', '2024-01-03 11:00:00', 'Pierwsza sesja', now()),
(22, 78, 154, '2024-01-03 12:00:00', '2024-01-04 13:00:00', 'Druga sesja', now()),
(22, 155, 231, '2024-01-04 14:00:00', '2024-01-05 15:00:00', 'Trzecia sesja', now()),

-- READING sessions
(27, 1, 50, '2024-09-01 08:00:00', '2024-09-01 09:00:00', 'Pierwsza sesja', now()),
(27, 51, 100, '2024-09-01 09:30:00', '2024-09-01 10:30:00', 'Druga sesja', now()),
(27, 101, 150, '2024-09-01 11:00:00', '2024-09-01 12:00:00', 'Trzecia sesja', now()),

(28, 1, 50, '2024-09-02 08:00:00', '2024-09-02 09:00:00', 'Pierwsza sesja', now()),
(28, 51, 100, '2024-09-02 09:30:00', '2024-09-02 10:30:00', 'Druga sesja', now()),
(28, 101, 150, '2024-09-02 11:00:00', '2024-09-02 12:00:00', 'Trzecia sesja', now()),

(29, 1, 47, '2024-09-03 08:00:00', '2024-09-03 09:00:00', 'Pierwsza sesja', now()),
(29, 48, 94, '2024-09-03 09:30:00', '2024-09-03 10:30:00', 'Druga sesja', now()),
(29, 95, 141, '2024-09-03 11:00:00', '2024-09-03 12:00:00', 'Trzecia sesja', now());

-- reading_book
INSERT INTO reading_book(bookshelf_id, book_id, status, current_page, total_notes, total_sessions, started_reading_at,
                         finished_reading_at, created_at)
VALUES
-- READ
(1, 342, 'READ', 310, 5, 3, '2024-02-01 08:00:00', '2024-02-05 20:00:00', now()),
(1, 343, 'READ', 450, 5, 3, '2024-02-02 09:00:00', '2024-02-06 21:00:00', now()),

-- READING
(2, 344, 'READING', 200, 5, 2, '2024-09-07 08:00:00', NULL, now()),
(2, 345, 'READING', 180, 5, 2, '2024-09-08 09:00:00', NULL, now());

-- reading_note
INSERT INTO reading_note(reading_book_id, title, content, page_from, page_to, created_at)
VALUES
-- Hobbit
(39, 'Początek przygody', 'Bilbo wyrusza w nieznane, spotkania z elfami i krasnoludami.', 1, 62, now()),
(39, 'Pierwsze wyzwania', 'Spotkanie z trollami i pierwsze lekcje odwagi.', 63, 124, now()),
(39, 'Niebezpieczeństwa w drodze', 'Zagrożenia i przyjaźnie na trasie podróży.', 125, 186, now()),
(39, 'Smocze tajemnice', 'Pierwsze zetknięcie z zagadkami Smauga.', 187, 248, now()),
(39, 'Podsumowanie przygody', 'Bilbo powraca do domu z nowym doświadczeniem.', 249, 310, now()),

-- Władca Pierścieni 1
(40, 'Nowy wątek', 'Frodo otrzymuje pierścień i rozpoczyna swoją misję.', 1, 112, now()),
(40, 'Spotkania i niebezpieczeństwa', 'Podróż przez Śródziemie, nowe postacie i zagrożenia.', 113, 224, now()),
(40, 'Próby i zdrady', 'Frodo i Drużyna stają przed poważnymi próbami.', 225, 336, now()),
(40, 'Bitwy i decyzje', 'Decydujące starcia z wrogami, konflikty wewnętrzne bohaterów.', 337, 448, now()),
(40, 'Podsumowanie części', 'Zakończenie pierwszej części podróży.', 449, 450, now()),

-- Władca Pierścieni 2
(41, 'Początek wędrówki', 'Frodo i Sam kontynuują podróż przez trudne tereny.', 1, 112, now()),
(41, 'Niebezpieczeństwa', 'Przeszkody na trasie, starcia z wrogami.', 113, 224, now()),
(41, 'Spotkania', 'Nowe postacie i wyzwania moralne.', 225, 336, now()),
(41, 'Decyzje i konflikty', 'Trudne wybory bohaterów.', 337, 448, now()),
(41, 'Refleksje i wnioski', 'Podsumowanie wydarzeń i przygotowanie do finału.', 449, 450, now()),

-- Władca Pierścieni 3
(42, 'Wędrówka', 'Bohaterowie zmierzają w stronę ostatecznej konfrontacji.', 1, 112, now()),
(42, 'Niebezpieczeństwa', 'Wrogowie czyhają na każdym kroku.', 113, 224, now()),
(42, 'Starcia', 'Bitwy i dramatyczne wydarzenia.', 225, 336, now()),
(42, 'Finał', 'Ostateczne starcie dobra ze złem.', 337, 448, now()),
(42, 'Zakończenie', 'Rozwiązanie historii i losy bohaterów.', 449, 450, now());

-- reading_session
INSERT INTO reading_session(reading_book_id, page_from, page_to, started_reading_at, finished_reading_at, description,
                            created_at)
VALUES
-- Hobbit
(39, 1, 62, '2024-02-01 08:00:00', '2024-02-01 10:00:00', 'Pierwsza sesja', now()),
(39, 63, 124, '2024-02-01 11:00:00', '2024-02-01 13:00:00', 'Druga sesja', now()),
(39, 125, 186, '2024-02-02 08:00:00', '2024-02-02 10:00:00', 'Trzecia sesja', now()),

-- Władca Pierścieni 1
(40, 1, 112, '2024-02-02 08:00:00', '2024-02-02 10:00:00', 'Pierwsza sesja', now()),
(40, 113, 224, '2024-02-03 08:00:00', '2024-02-03 10:00:00', 'Druga sesja', now()),
(40, 225, 336, '2024-02-04 08:00:00', '2024-02-04 10:00:00', 'Trzecia sesja', now()),

-- Władca Pierścieni 2
(41, 1, 112, '2024-02-05 08:00:00', '2024-02-05 10:00:00', 'Pierwsza sesja', now()),
(41, 113, 224, '2024-02-06 08:00:00', '2024-02-06 10:00:00', 'Druga sesja', now()),
(41, 225, 336, '2024-02-07 08:00:00', '2024-02-07 10:00:00', 'Trzecia sesja', now()),

-- Władca Pierścieni 3
(42, 1, 112, '2024-02-08 08:00:00', '2024-02-08 10:00:00', 'Pierwsza sesja', now()),
(42, 113, 224, '2024-02-09 08:00:00', '2024-02-09 10:00:00', 'Druga sesja', now()),
(42, 225, 336, '2024-02-10 08:00:00', '2024-02-10 10:00:00', 'Trzecia sesja', now());


-- reading_book
INSERT INTO reading_book(bookshelf_id, book_id, status, current_page, total_notes, total_sessions, started_reading_at,
                         finished_reading_at, created_at)
VALUES
-- READ
(1, 353, 'READ', 450, 5, 3, '2024-03-01 08:00:00', '2024-03-05 20:00:00', now()),

-- READING
(2, 354, 'READING', 200, 5, 2, '2024-09-01 08:00:00', NULL, now()),
(2, 355, 'READING', 180, 5, 2, '2024-09-02 09:00:00', NULL, now()),
(3, 356, 'READING', 150, 5, 2, '2024-09-03 09:00:00', NULL, now());

-- reading_note
INSERT INTO reading_note(reading_book_id, title, content, page_from, page_to, created_at)
VALUES
-- Book 353
(43, 'Wprowadzenie do Agile', 'Podstawy zwinnego wytwarzania oprogramowania, wartości i zasady.', 1, 90, now()),
(43, 'Zasady SOLID', 'Omówienie zasad SOLID i ich znaczenie w czystym kodzie.', 91, 180, now()),
(43, 'Refaktoryzacja', 'Jak poprawiać kod bez zmiany jego zachowania.', 181, 270, now()),
(43, 'Test Driven Development', 'Praktyki pisania testów najpierw.', 271, 360, now()),
(43, 'Wzorce projektowe', 'Najważniejsze wzorce projektowe w praktyce.', 361, 450, now()),

-- Book 354
(44, 'Wprowadzenie do czystego kodu', 'Jak pisać kod czytelny i łatwy w utrzymaniu.', 1, 50, now()),
(44, 'Refaktoryzacja metod', 'Praktyczne wskazówki refaktoryzacji.', 51, 100, now()),
(44, 'Zasady projektowania klas', 'Tworzenie dobrze zaprojektowanych klas.', 101, 150, now()),
(44, 'Testy jednostkowe', 'Pisanie skutecznych testów jednostkowych.', 151, 200, now()),
(44, 'Dobre praktyki', 'Ogólne wskazówki i pułapki do unikania.', 201, 200, now()),

-- Book 355
(45, 'Podstawy czystego architektonicznie kodu', 'Znaczenie modularności i separacji odpowiedzialności.', 1, 45, now()),
(45, 'Zarządzanie zależnościami', 'Inwersja kontroli i wstrzykiwanie zależności.', 46, 90, now()),
(45, 'Testowanie integracyjne', 'Jak testować większe moduły.', 91, 135, now()),
(45, 'Projektowanie systemów', 'Podstawowe wzorce architektoniczne.', 136, 180, now()),
(45, 'Refaktoryzacja systemowa', 'Utrzymywanie dużych systemów w czystości.', 181, 180, now()),

-- Book 356
(46, 'Zwinne wytwarzanie oprogramowania', 'Agile w praktyce – wartości, ceremonie, retrospektywy.', 1, 50, now()),
(46, 'Zwinne zespoły', 'Organizacja zespołów programistycznych.', 51, 100, now()),
(46, 'Praktyki XP', 'Extreme Programming i praktyczne wskazówki.', 101, 150, now()),
(46, 'Planowanie sprintów', 'Efektywne planowanie i estymacja.', 151, 200, now()),
(46, 'Ciągłe doskonalenie', 'Retrospektywy i poprawa procesów.', 201, 150, now());

-- reading_session
INSERT INTO reading_session(reading_book_id, page_from, page_to, started_reading_at, finished_reading_at, description,
                            created_at)
VALUES
-- Book 353
(43, 1, 150, '2024-03-01 08:00:00', '2024-03-01 10:00:00', 'Pierwsza sesja', now()),
(43, 151, 300, '2024-03-02 08:00:00', '2024-03-02 10:00:00', 'Druga sesja', now()),
(43, 301, 450, '2024-03-03 08:00:00', '2024-03-03 10:00:00', 'Trzecia sesja', now()),

-- Book 354
(44, 1, 70, '2024-09-01 08:00:00', '2024-09-01 09:00:00', 'Pierwsza sesja', now()),
(44, 71, 140, '2024-09-01 09:30:00', '2024-09-01 10:30:00', 'Druga sesja', now()),
(44, 141, 200, '2024-09-02 08:00:00', '2024-09-02 09:00:00', 'Trzecia sesja', now()),

-- Book 355
(45, 1, 60, '2024-09-02 09:00:00', '2024-09-02 10:00:00', 'Pierwsza sesja', now()),
(45, 61, 120, '2024-09-03 08:00:00', '2024-09-03 09:00:00', 'Druga sesja', now()),
(45, 121, 180, '2024-09-03 09:30:00', '2024-09-03 10:30:00', 'Trzecia sesja', now()),

-- Book 356
(46, 1, 50, '2024-09-04 08:00:00', '2024-09-04 09:00:00', 'Pierwsza sesja', now()),
(46, 51, 100, '2024-09-04 09:30:00', '2024-09-04 10:30:00', 'Druga sesja', now()),
(46, 101, 150, '2024-09-05 08:00:00', '2024-09-05 09:00:00', 'Trzecia sesja', now());


update reading_book
set total_notes    = (select count(*) from reading_note where reading_book.id = reading_note.reading_book_id),
    total_sessions = (select count(*) from reading_session where reading_book.id = reading_session.reading_book_id);
update bookshelf
set total_books = (select count(*) from reading_book where bookshelf.id = reading_book.bookshelf_id)