INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO users (username, email, password)
VALUES ('admin', 'admin@admin.com', '$2a$10$dItUTI4jMBJ.VK2qeayOC.rg/a9wdjhfMHWdcMUTTlbG60uNHif1O');

INSERT INTO users (username, email, password)
VALUES ('user', 'user@user.com', '$2a$10$DpU6XMeL/1nFyC2yyuE33.wgU1C0tGU/W9BTcvENbpRsI94wbIuUK');

INSERT INTO user_roles (role_id, user_id)
SELECT r.id, u.id FROM users u, roles r WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

INSERT INTO user_roles (role_id, user_id)
SELECT r.id, u.id FROM users u, roles r WHERE u.username = 'user' AND r.name = 'ROLE_USER';

INSERT INTO boards (name, board_creator_id)
VALUES ('Projekt grafiki', 1);

INSERT INTO user_boards (user_id, board_id)
VALUES (1, 1);

INSERT INTO boards (name, board_creator_id)
VALUES ('Projekt marketing', 1);

INSERT INTO user_boards (user_id, board_id)
VALUES (1, 2);

INSERT INTO boards (name, board_creator_id)
VALUES ('Analiza Projektu', 1);

INSERT INTO user_boards (user_id, board_id)
VALUES (1, 3);

INSERT INTO boards (name, board_creator_id)
VALUES ('Analiza projektowa', 1);

INSERT INTO user_boards (user_id, board_id)
VALUES (1, 4);

INSERT INTO boards (name, board_creator_id)
VALUES ('Projekt ecommerce', 1);

INSERT INTO user_boards (user_id, board_id)
VALUES (1, 5);

-- -- Pierwsze zapytanie: dodanie tablicy do tabeli boards
-- INSERT INTO boards (name, board_creator_id)
-- VALUES ('Projekt marketing', 1);
--
-- -- Drugie zapytanie: dodanie powiązania użytkownika z tablicą w tabeli user_boards
-- INSERT INTO user_boards (user_id, board_id)
-- VALUES (1, 1);

-- INSERT INTO boards (name)
-- VALUES ('tytul tablicy');

INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 1', 'Opis zadania numer 1', 'TO_DO', 1, 1);
INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 2', 'Opis zadania numer 2', 'TO_DO', 1, 1);
INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 3', 'Opis zadania numer 3', 'TO_DO', 1, 1);
INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 4', 'Opis zadania numer 4', 'IN_PROGRESS', 1, 1);
INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 5', 'Opis zadania numer 5', 'IN_PROGRESS', 1, 1);
INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 6', 'Opis zadania numer 6', 'IN_PROGRESS', 1, 1);
INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 7', 'Opis zadania numer 7', 'COMPLETED', 1, 1);
INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 8', 'Opis zadania numer 8', 'COMPLETED', 1, 1);
INSERT INTO tasks (title, description, status, user_id, board_id)
VALUES ('Zadanie 9', 'Opis zadania numer 9', 'COMPLETED', 1, 1);
