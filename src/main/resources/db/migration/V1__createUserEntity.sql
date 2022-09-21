-- OBSERVAÇÃO: Esse arquivo não pode ser alterado

CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(100) default 'STUDENT'
);

