-- challenge_id = 1
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (1, 1, 50, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
       (1, 2, 100, 'COMPLETED', now() - interval '14 days', now() - interval '2 days', now(), NULL),
       (1, 3, 70, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),
       (1, 4, 20, 'UNCOMPLETED', now() - interval '14 days', now() - interval '10 days', now(), NULL),
       (1, 5, 95, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (1, 6, 10, 'UNCOMPLETED', now() - interval '13 days', now() - interval '7 days', now(), NULL),
       (1, 7, 100, 'COMPLETED', now() - interval '12 days', now() - interval '1 day', now(), NULL),
       (1, 8, 40, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
       (1, 9, 80, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
       (1, 10, 30, 'UNCOMPLETED', now() - interval '14 days', now() - interval '5 days', now(), NULL),
       (1, 11, 100, 'COMPLETED', now() - interval '11 days', now() - interval '2 days', now(), NULL),
       (1, 12, 55, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),
       (1, 13, 100, 'COMPLETED', now() - interval '14 days', now() - interval '3 days', now(), NULL),
       (1, 14, 25, 'UNCOMPLETED', now() - interval '12 days', now() - interval '6 days', now(), NULL),
       (1, 15, 65, 'ACTIVE', now() - interval '8 days', NULL, now(), NULL);

-- challenge_id = 2
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (2, 16, 5000, 'ACTIVE', now() - interval '19 days', NULL, now(), NULL),
       (2, 17, 10000, 'COMPLETED', now() - interval '18 days', now() - interval '1 day', now(), NULL),
       (2, 18, 2000, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (2, 19, 9000, 'ACTIVE', now() - interval '18 days', NULL, now(), NULL),
       (2, 20, 10000, 'COMPLETED', now() - interval '17 days', now() - interval '2 days', now(), NULL),
       (2, 21, 3000, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (2, 22, 7000, 'ACTIVE', now() - interval '16 days', NULL, now(), NULL),
       (2, 23, 10000, 'COMPLETED', now() - interval '18 days', now() - interval '3 days', now(), NULL),
       (2, 24, 1500, 'UNCOMPLETED', now() - interval '15 days', now() - interval '12 days', now(), NULL),
       (2, 25, 2500, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
       (2, 26, 8000, 'ACTIVE', now() - interval '19 days', NULL, now(), NULL),
       (2, 27, 4000, 'ACTIVE', now() - interval '17 days', NULL, now(), NULL),
       (2, 28, 10000, 'COMPLETED', now() - interval '16 days', now() - interval '2 days', now(), NULL),
       (2, 29, 6000, 'ACTIVE', now() - interval '18 days', NULL, now(), NULL),
       (2, 30, 1200, 'UNCOMPLETED', now() - interval '15 days', now() - interval '10 days', now(), NULL);

-- challenge_id = 3
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (3, 1, 80, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (3, 2, 100, 'COMPLETED', now() - interval '25 days', now() - interval '2 days', now(), NULL),
       (3, 3, 45, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (3, 4, 100, 'COMPLETED', now() - interval '20 days', now() - interval '1 day', now(), NULL),
       (3, 5, 70, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (3, 6, 65, 'ACTIVE', now() - interval '12 days', NULL, now(), NULL),
       (3, 7, 50, 'UNCOMPLETED', now() - interval '30 days', now() - interval '1 day', now(), NULL),
       (3, 8, 90, 'ACTIVE', now() - interval '18 days', NULL, now(), NULL),
       (3, 9, 100, 'COMPLETED', now() - interval '28 days', now() - interval '3 days', now(), NULL),
       (3, 10, 20, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (3, 11, 85, 'ACTIVE', now() - interval '22 days', NULL, now(), NULL),
       (3, 12, 60, 'UNCOMPLETED', now() - interval '35 days', now() - interval '5 days', now(), NULL),
       (3, 13, 95, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
       (3, 14, 40, 'ACTIVE', now() - interval '6 days', NULL, now(), NULL),
       (3, 15, 100, 'COMPLETED', now() - interval '26 days', now() - interval '2 days', now(), NULL);

-- challenge_id = 4
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (4, 16, 12, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (4, 17, 15, 'COMPLETED', now() - interval '14 days', now() - interval '2 days', now(), NULL),
       (4, 18, 8, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (4, 19, 10, 'UNCOMPLETED', now() - interval '30 days', now() - interval '1 day', now(), NULL),
       (4, 20, 15, 'COMPLETED', now() - interval '20 days', now() - interval '1 day', now(), NULL),
       (4, 21, 7, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
       (4, 22, 14, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (4, 23, 6, 'ACTIVE', now() - interval '4 days', NULL, now(), NULL),
       (4, 24, 15, 'COMPLETED', now() - interval '25 days', now() - interval '3 days', now(), NULL),
       (4, 25, 9, 'ACTIVE', now() - interval '8 days', NULL, now(), NULL),
       (4, 26, 3, 'UNCOMPLETED', now() - interval '35 days', now() - interval '5 days', now(), NULL),
       (4, 27, 15, 'COMPLETED', now() - interval '28 days', now() - interval '1 day', now(), NULL),
       (4, 28, 12, 'ACTIVE', now() - interval '11 days', NULL, now(), NULL),
       (4, 29, 5, 'ACTIVE', now() - interval '6 days', NULL, now(), NULL),
       (4, 30, 13, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL);

-- challenge_id = 5
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (5, 31, 1200, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (5, 32, 1500, 'COMPLETED', now() - interval '30 days', now() - interval '2 days', now(), NULL),
       (5, 33, 800, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (5, 34, 950, 'ACTIVE', now() - interval '12 days', NULL, now(), NULL),
       (5, 35, 300, 'UNCOMPLETED', now() - interval '40 days', now() - interval '5 days', now(), NULL),
       (5, 36, 700, 'ACTIVE', now() - interval '8 days', NULL, now(), NULL),
       (5, 37, 1500, 'COMPLETED', now() - interval '25 days', now() - interval '1 day', now(), NULL),
       (5, 38, 200, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (5, 39, 1300, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
       (5, 40, 900, 'ACTIVE', now() - interval '6 days', NULL, now(), NULL),
       (5, 41, 1500, 'COMPLETED', now() - interval '35 days', now() - interval '3 days', now(), NULL),
       (5, 42, 100, 'UNCOMPLETED', now() - interval '50 days', now() - interval '2 days', now(), NULL),
       (5, 43, 750, 'ACTIVE', now() - interval '11 days', NULL, now(), NULL),
       (5, 44, 1400, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),
       (5, 45, 1500, 'COMPLETED', now() - interval '20 days', now() - interval '1 day', now(), NULL);


INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (6, 46, 50, 'ACTIVE', now() - interval '12 days', NULL, now(), NULL),
       (6, 47, 80, 'ACTIVE', now() - interval '18 days', NULL, now(), NULL),
       (6, 48, 100, 'COMPLETED', now() - interval '25 days', now() - interval '2 days', now(), NULL),
       (6, 49, 20, 'UNCOMPLETED', now() - interval '30 days', now() - interval '5 days', now(), NULL),
       (6, 50, 60, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (6, 1, 100, 'COMPLETED', now() - interval '22 days', now() - interval '1 day', now(), NULL),
       (6, 2, 75, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (6, 3, 40, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
       (6, 4, 90, 'ACTIVE', now() - interval '8 days', NULL, now(), NULL),
       (6, 5, 30, 'UNCOMPLETED', now() - interval '35 days', now() - interval '3 days', now(), NULL),
       (6, 6, 100, 'COMPLETED', now() - interval '28 days', now() - interval '2 days', now(), NULL),
       (6, 7, 10, 'ACTIVE', now() - interval '6 days', NULL, now(), NULL),
       (6, 8, 85, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (6, 9, 60, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),
       (6, 10, 100, 'COMPLETED', now() - interval '20 days', now() - interval '1 day', now(), NULL);


INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (7, 11, 200, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
       (7, 12, 150, 'ACTIVE', now() - interval '18 days', NULL, now(), NULL),
       (7, 13, 300, 'COMPLETED', now() - interval '25 days', now() - interval '2 days', now(), NULL),
       (7, 14, 75, 'UNCOMPLETED', now() - interval '30 days', now() - interval '5 days', now(), NULL),
       (7, 15, 210, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
       (7, 16, 280, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
       (7, 17, 100, 'UNCOMPLETED', now() - interval '35 days', now() - interval '4 days', now(), NULL),
       (7, 18, 300, 'COMPLETED', now() - interval '27 days', now() - interval '1 day', now(), NULL),
       (7, 19, 250, 'ACTIVE', now() - interval '12 days', NULL, now(), NULL),
       (7, 20, 50, 'ACTIVE', now() - interval '6 days', NULL, now(), NULL),
       (7, 21, 275, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (7, 22, 300, 'COMPLETED', now() - interval '22 days', now() - interval '3 days', now(), NULL),
       (7, 23, 180, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (7, 24, 60, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (7, 25, 300, 'COMPLETED', now() - interval '19 days', now() - interval '1 day', now(), NULL);


INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (8, 1, 100, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (8, 2, 120, 'ACTIVE', now() - interval '12 days', NULL, now(), NULL),
       (8, 3, 150, 'COMPLETED', now() - interval '20 days', now() - interval '2 days', now(), NULL),
       (8, 4, 50, 'UNCOMPLETED', now() - interval '15 days', now() - interval '1 day', now(), NULL),
       (8, 5, 180, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
       (8, 6, 200, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
       (8, 7, 75, 'UNCOMPLETED', now() - interval '25 days', now() - interval '3 days', now(), NULL),
       (8, 8, 220, 'ACTIVE', now() - interval '11 days', NULL, now(), NULL),
       (8, 9, 150, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),
       (8, 10, 200, 'ACTIVE', now() - interval '8 days', NULL, now(), NULL),
       (8, 11, 180, 'ACTIVE', now() - interval '16 days', NULL, now(), NULL),
       (8, 12, 250, 'COMPLETED', now() - interval '21 days', now() - interval '1 day', now(), NULL),
       (8, 13, 90, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (8, 14, 300, 'COMPLETED', now() - interval '22 days', now() - interval '2 days', now(), NULL),
       (8, 15, 160, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL);


INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (9, 1, 120, 'ACTIVE', now() - interval '11 days', NULL, now(), NULL),
       (9, 2, 200, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),
       (9, 3, 300, 'COMPLETED', now() - interval '25 days', now() - interval '2 days', now(), NULL),
       (9, 4, 150, 'UNCOMPLETED', now() - interval '18 days', now() - interval '4 days', now(), NULL),
       (9, 5, 180, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (9, 6, 250, 'ACTIVE', now() - interval '16 days', NULL, now(), NULL),
       (9, 7, 50, 'UNCOMPLETED', now() - interval '30 days', now() - interval '2 days', now(), NULL),
       (9, 8, 220, 'ACTIVE', now() - interval '12 days', NULL, now(), NULL),
       (9, 9, 200, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
       (9, 10, 300, 'COMPLETED', now() - interval '20 days', now() - interval '1 day', now(), NULL),
       (9, 11, 180, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (9, 12, 100, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (9, 13, 260, 'COMPLETED', now() - interval '23 days', now() - interval '2 days', now(), NULL),
       (9, 14, 150, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
       (9, 15, 300, 'COMPLETED', now() - interval '19 days', now() - interval '1 day', now(), NULL);


INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (10, 1, 180, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (10, 2, 200, 'ACTIVE', now() - interval '12 days', NULL, now(), NULL),
       (10, 3, 300, 'COMPLETED', now() - interval '25 days', now() - interval '2 days', now(), NULL),
       (10, 4, 150, 'UNCOMPLETED', now() - interval '20 days', now() - interval '3 days', now(), NULL),
       (10, 5, 220, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
       (10, 6, 100, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (10, 7, 150, 'UNCOMPLETED', now() - interval '28 days', now() - interval '1 day', now(), NULL),
       (10, 8, 240, 'ACTIVE', now() - interval '11 days', NULL, now(), NULL),
       (10, 9, 260, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),
       (10, 10, 300, 'COMPLETED', now() - interval '21 days', now() - interval '1 day', now(), NULL),
       (10, 11, 190, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
       (10, 12, 180, 'ACTIVE', now() - interval '8 days', NULL, now(), NULL),
       (10, 13, 250, 'COMPLETED', now() - interval '22 days', now() - interval '2 days', now(), NULL),
       (10, 14, 160, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (10, 15, 300, 'COMPLETED', now() - interval '18 days', now() - interval '1 day', now(), NULL);


-- challenge_id 11
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (11, 1, 20, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (11, 2, 50, 'COMPLETED', now() - interval '5 days', now() - interval '1 days', now(), NULL),
       (11, 3, 35, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (11, 4, 40, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (11, 5, 50, 'COMPLETED', now() - interval '5 days', now() - interval '2 days', now(), NULL),
       (11, 6, 10, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (11, 7, 50, 'COMPLETED', now() - interval '5 days', now() - interval '1 days', now(), NULL),
       (11, 8, 30, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (11, 9, 25, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (11, 10, 45, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL);

-- challenge_id 12
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (12, 11, 500, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (12, 12, 1000, 'COMPLETED', now() - interval '10 days', now() - interval '1 days', now(), NULL),
       (12, 13, 800, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (12, 14, 1000, 'COMPLETED', now() - interval '10 days', now() - interval '2 days', now(), NULL),
       (12, 15, 400, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (12, 16, 1000, 'COMPLETED', now() - interval '10 days', now() - interval '1 days', now(), NULL),
       (12, 17, 600, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (12, 18, 900, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (12, 19, 1000, 'COMPLETED', now() - interval '10 days', now() - interval '3 days', now(), NULL),
       (12, 20, 750, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL);

-- challenge_id 13
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (13, 21, 2, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
       (13, 22, 5, 'COMPLETED', now() - interval '20 days', now() - interval '1 days', now(), NULL),
       (13, 23, 3, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
       (13, 24, 5, 'COMPLETED', now() - interval '20 days', now() - interval '2 days', now(), NULL),
       (13, 25, 4, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
       (13, 26, 1, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
       (13, 27, 5, 'COMPLETED', now() - interval '20 days', now() - interval '1 days', now(), NULL),
       (13, 28, 3, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
       (13, 29, 2, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
       (13, 30, 4, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL);

-- challenge_id 14
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (14, 31, 7, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL),
       (14, 32, 10, 'COMPLETED', now() - interval '3 days', now() - interval '1 days', now(), NULL),
       (14, 33, 4, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL),
       (14, 34, 9, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL),
       (14, 35, 6, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL),
       (14, 36, 10, 'COMPLETED', now() - interval '3 days', now() - interval '1 days', now(), NULL),
       (14, 37, 8, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL),
       (14, 38, 3, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL),
       (14, 39, 10, 'COMPLETED', now() - interval '3 days', now() - interval '2 days', now(), NULL),
       (14, 40, 5, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL);

-- challenge_id 15
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (15, 1, 12, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL),
       (15, 2, 20, 'COMPLETED', now() - interval '2 days', now() - interval '1 days', now(), NULL),
       (15, 3, 15, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL),
       (15, 4, 8, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL),
       (15, 5, 20, 'COMPLETED', now() - interval '2 days', now() - interval '1 days', now(), NULL),
       (15, 6, 10, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL),
       (15, 7, 19, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL),
       (15, 8, 14, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL),
       (15, 9, 20, 'COMPLETED', now() - interval '2 days', now() - interval '1 days', now(), NULL),
       (15, 10, 17, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL);

-- challenge_id 16
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (16, 11, 400, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (16, 12, 1500, 'COMPLETED', now() - interval '15 days', now() - interval '1 days', now(), NULL),
       (16, 13, 900, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (16, 14, 1500, 'COMPLETED', now() - interval '15 days', now() - interval '2 days', now(), NULL),
       (16, 15, 1200, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (16, 16, 1000, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (16, 17, 1500, 'COMPLETED', now() - interval '15 days', now() - interval '1 days', now(), NULL),
       (16, 18, 1300, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (16, 19, 1400, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
       (16, 20, 1500, 'COMPLETED', now() - interval '15 days', now() - interval '2 days', now(), NULL);

-- challenge_id 17
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (17, 21, 2, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (17, 22, 3, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (17, 23, 3, 'COMPLETED', now() - interval '5 days', now() - interval '1 days', now(), NULL),
       (17, 24, 1, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (17, 25, 3, 'COMPLETED', now() - interval '5 days', now() - interval '1 days', now(), NULL),
       (17, 26, 2, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (17, 27, 1, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (17, 28, 3, 'COMPLETED', now() - interval '5 days', now() - interval '1 days', now(), NULL),
       (17, 29, 2, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
       (17, 30, 3, 'COMPLETED', now() - interval '5 days', now() - interval '1 days', now(), NULL);

-- challenge_id 18
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (18, 31, 10, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (18, 32, 8, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (18, 33, 9, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (18, 34, 10, 'COMPLETED', now() - interval '10 days', now() - interval '1 days', now(), NULL),
       (18, 35, 7, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (18, 36, 10, 'COMPLETED', now() - interval '10 days', now() - interval '2 days', now(), NULL),
       (18, 37, 5, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (18, 38, 9, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
       (18, 39, 10, 'COMPLETED', now() - interval '10 days', now() - interval '1 days', now(), NULL),
       (18, 40, 6, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL);

-- challenge_id 19
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (19, 1, 25, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (19, 2, 30, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (19, 3, 20, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (19, 4, 18, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (19, 5, 30, 'COMPLETED', now() - interval '7 days', now() - interval '1 days', now(), NULL),
       (19, 6, 22, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (19, 7, 25, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (19, 8, 30, 'COMPLETED', now() - interval '7 days', now() - interval '2 days', now(), NULL),
       (19, 9, 28, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (19, 10, 26, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL);

-- challenge_id 20
INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES (20, 11, 3, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (20, 12, 5, 'COMPLETED', now() - interval '7 days', now() - interval '1 days', now(), NULL),
       (20, 13, 4, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (20, 14, 2, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (20, 15, 5, 'COMPLETED', now() - interval '7 days', now() - interval '1 days', now(), NULL),
       (20, 16, 3, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (20, 17, 4, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (20, 18, 5, 'COMPLETED', now() - interval '7 days', now() - interval '1 days', now(), NULL),
       (20, 19, 2, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
       (20, 20, 5, 'COMPLETED', now() - interval '7 days', now() - interval '1 days', now(), NULL);

INSERT INTO challenge_participant (challenge_id, user_id, current_goal_value, status, started_at, finished_at,
                                   created_at, updated_at)
VALUES
-- challenge_id = 21, goal_value = 20
(21, 1, 15, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
(21, 2, 20, 'COMPLETED', now() - interval '10 days', now(), now(), NULL),
(21, 3, 10, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
(21, 4, 18, 'ACTIVE', now() - interval '10 days', NULL, now(), NULL),
(21, 5, 20, 'COMPLETED', now() - interval '8 days', now(), now(), NULL),
(21, 6, 12, 'ACTIVE', now() - interval '7 days', NULL, now(), NULL),
(21, 7, 20, 'COMPLETED', now() - interval '10 days', now(), now(), NULL),
(21, 8, 17, 'ACTIVE', now() - interval '9 days', NULL, now(), NULL),
(21, 9, 20, 'COMPLETED', now() - interval '10 days', now(), now(), NULL),
(21, 10, 14, 'ACTIVE', now() - interval '8 days', NULL, now(), NULL),

-- challenge_id = 22, goal_value = 2000 (PAGE_COUNT)
(22, 1, 500, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
(22, 2, 2000, 'COMPLETED', now() - interval '5 days', now(), now(), NULL),
(22, 3, 1500, 'ACTIVE', now() - interval '4 days', NULL, now(), NULL),
(22, 4, 1800, 'ACTIVE', now() - interval '5 days', NULL, now(), NULL),
(22, 5, 2000, 'COMPLETED', now() - interval '4 days', now(), now(), NULL),
(22, 6, 1200, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL),
(22, 7, 2000, 'COMPLETED', now() - interval '5 days', now(), now(), NULL),
(22, 8, 1700, 'ACTIVE', now() - interval '4 days', NULL, now(), NULL),
(22, 9, 2000, 'COMPLETED', now() - interval '5 days', now(), now(), NULL),
(22, 10, 1400, 'ACTIVE', now() - interval '3 days', NULL, now(), NULL),

-- challenge_id = 23, goal_value = 10 (AUTHOR_COUNT)
(23, 1, 5, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL),
(23, 2, 10, 'COMPLETED', now() - interval '2 days', now(), now(), NULL),
(23, 3, 7, 'ACTIVE', now() - interval '1 days', NULL, now(), NULL),
(23, 4, 8, 'ACTIVE', now() - interval '2 days', NULL, now(), NULL),
(23, 5, 10, 'COMPLETED', now() - interval '1 days', now(), now(), NULL),
(23, 6, 6, 'ACTIVE', now() - interval '1 days', NULL, now(), NULL),
(23, 7, 10, 'COMPLETED', now() - interval '2 days', now(), now(), NULL),
(23, 8, 9, 'ACTIVE', now() - interval '1 days', NULL, now(), NULL),
(23, 9, 10, 'COMPLETED', now() - interval '2 days', now(), now(), NULL),
(23, 10, 4, 'ACTIVE', now() - interval '1 days', NULL, now(), NULL),

-- challenge_id = 24, goal_value = 10 (GENRE_BOOKS)
(24, 1, 7, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
(24, 2, 10, 'COMPLETED', now() - interval '15 days', now(), now(), NULL),
(24, 3, 6, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
(24, 4, 9, 'ACTIVE', now() - interval '15 days', NULL, now(), NULL),
(24, 5, 10, 'COMPLETED', now() - interval '14 days', now(), now(), NULL),
(24, 6, 4, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),
(24, 7, 10, 'COMPLETED', now() - interval '15 days', now(), now(), NULL),
(24, 8, 8, 'ACTIVE', now() - interval '14 days', NULL, now(), NULL),
(24, 9, 10, 'COMPLETED', now() - interval '15 days', now(), now(), NULL),
(24, 10, 3, 'ACTIVE', now() - interval '13 days', NULL, now(), NULL),

-- challenge_id = 25, goal_value = 50 (BOOK_COUNT)
(25, 1, 30, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
(25, 2, 50, 'COMPLETED', now() - interval '20 days', now(), now(), NULL),
(25, 3, 40, 'ACTIVE', now() - interval '19 days', NULL, now(), NULL),
(25, 4, 45, 'ACTIVE', now() - interval '20 days', NULL, now(), NULL),
(25, 5, 50, 'COMPLETED', now() - interval '19 days', now(), now(), NULL),
(25, 6, 25, 'ACTIVE', now() - interval '18 days', NULL, now(), NULL),
(25, 7, 50, 'COMPLETED', now() - interval '20 days', now(), now(), NULL),
(25, 8, 35, 'ACTIVE', now() - interval '19 days', NULL, now(), NULL),
(25, 9, 50, 'COMPLETED', now() - interval '20 days', now(), now(), NULL),
(25, 10, 28, 'ACTIVE', now() - interval '18 days', NULL, now(), NULL);



update challenge as c
set total_participants = (coalesce((select count(*) from challenge_participant cp where cp.challenge_id = c.id), 0));

update challenge_participant cp
set current_goal_value = (select c.goal_value from challenge c where c.id = cp.challenge_id),
    status = 'COMPLETED',
    finished_at = now()
from challenge c
where c.id = cp.challenge_id and cp.current_goal_value >= c.goal_value;