CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE boards
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    board_creator_id INT          NOT NULL,
    created_at            DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estimated_end_date    DATETIME     NULL
);

CREATE TABLE tasks
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status      VARCHAR(255) NOT NULL,
    user_id     BIGINT       NOT NULL,
    board_id    BIGINT       NOT NULL,
    FOREIGN KEY (board_id) REFERENCES boards (id)
);

-- CREATE TABLE in_progress
-- (
--     id       BIGINT AUTO_INCREMENT PRIMARY KEY,
--     title    VARCHAR(255) NOT NULL,
--     description    VARCHAR(255) NOT NULL,
--     user_id  BIGINT       NOT NULL,
--     board_id  BIGINT       NOT NULL,
--     FOREIGN KEY (board_id) REFERENCES boards (id)
-- );
--
-- CREATE TABLE completed
-- (
--     id       BIGINT AUTO_INCREMENT PRIMARY KEY,
--     title    VARCHAR(255) NOT NULL,
--     description    VARCHAR(255) NOT NULL,
--     user_id  BIGINT       NOT NULL,
--     board_id  BIGINT       NOT NULL,
--     FOREIGN KEY (board_id) REFERENCES boards (id)
-- );

CREATE TABLE user_roles
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE user_boards
(
    user_id  BIGINT,
    board_id BIGINT,
    PRIMARY KEY (user_id, board_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (board_id) REFERENCES boards (id) ON DELETE CASCADE
);