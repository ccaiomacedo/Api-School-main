CREATE TABLE Video (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         video VARCHAR(255) NOT NULL UNIQUE,
                         section_id BIGINT,
                         CONSTRAINT fk_section_video FOREIGN KEY (section_id) REFERENCES Section
);

