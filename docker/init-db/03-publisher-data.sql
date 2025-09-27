INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (1, 'Wydawnictwo Literackie', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (2, 'Znak', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (3, 'Czarne', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (4, 'Agora', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (5, 'WAB', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (6, 'Społeczny Instytut Wydawniczy Znak', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (7, 'Rebis', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (8, 'Państwowy Instytut Wydawniczy', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (9, 'Muza', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (10, 'Nasza Księgarnia', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (11, 'Wydawnictwo Dolnośląskie', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (12, 'Prószyński i S-ka', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (13, 'Bellona', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (14, 'Iskry', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (15, 'Publicat', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (16, 'Książkowe Klimaty', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (17, 'Marginesy', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (18, 'Karakter', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (19, 'Sonia Draga', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (20, 'Media Rodzina', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (21, 'Literatura', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (22, 'Motyl', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (23, 'Krytyka Polityczna', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (24, 'Czerwone i Czarne', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (25, 'Świat Książki', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (26, 'Zysk i S-ka', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (27, 'Otwarte', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (28, 'Prószyński', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (29, 'WAM', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (30, 'Rebis2', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (31, 'Filia', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (32, 'Bukowy Las', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (33, 'Novae Res', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (34, 'RM', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (35, 'Czarna Owca', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (36, 'W.A.B.', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (37, 'Agora2', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (38, 'Prószyński_i_Ska', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (39, 'Marginesy2', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (40, 'Literatura2', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (41, 'Dolnoslaskie', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (42, 'Iskry2', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (43, 'Ossolineum', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (44, 'Mamania', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (45, 'Agora_SA', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (46, 'Otwarte_Sp_z_o_o', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (47, 'PWN', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (48, 'Universitas', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (49, 'Poznanskie', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (50, 'Polityka', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (51, 'Sic', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (52, 'SuperNowa', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (53, 'Albatros', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (54, 'Czwarta Strona', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (55, 'Amber', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (56, 'Media Rodzina', now(), NULL);
INSERT INTO publisher (id, name, created_at, updated_at)
VALUES (57, 'Helion', now(), NULL);


SELECT setval(pg_get_serial_sequence('publisher', 'id'), (select MAX(id) from publisher), true);