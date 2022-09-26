CREATE TABLE Section (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         code VARCHAR(50) NOT NULL UNIQUE,
                         title VARCHAR(50) NOT NULL,
                         author_Username_id BIGINT,
                         course_id BIGINT ,
                         CONSTRAINT fk_user_section FOREIGN KEY (author_Username_id) REFERENCES User,
                         CONSTRAINT fk_course_section FOREIGN KEY (course_id) REFERENCES Course
);

