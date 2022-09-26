CREATE TABLE Enrollment (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            course_id BIGINT,
                            user_id BIGINT,
                            date TIMESTAMP WITHOUT TIME ZONE,
                            CONSTRAINT fk_course_enrollment FOREIGN KEY (course_id) REFERENCES Course,
                            CONSTRAINT fk_user_enrollment FOREIGN KEY (user_id) REFERENCES User
);