CREATE TABLE registration
(
    registration_id   UUID PRIMARY KEY,
    registration_date DATE NOT NULL,
    student_id        UUID NOT NULL,
    CONSTRAINT fk_student
        FOREIGN KEY (student_id)
            REFERENCES student (student_id)
);