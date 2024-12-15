INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO users (username, password)
VALUES ('admin', '$2a$10$dItUTI4jMBJ.VK2qeayOC.rg/a9wdjhfMHWdcMUTTlbG60uNHif1O');

INSERT INTO users (username, password)
VALUES ('user', '$2a$10$DpU6XMeL/1nFyC2yyuE33.wgU1C0tGU/W9BTcvENbpRsI94wbIuUK');

INSERT INTO user_roles (role_id, user_id)
SELECT r.id, u.id FROM users u, roles r WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

INSERT INTO user_roles (role_id, user_id)
SELECT r.id, u.id FROM users u, roles r WHERE u.username = 'user' AND r.name = 'ROLE_USER';