CREATE TABLE student
(
    student_id                    UUID PRIMARY KEY,
    firstname                     VARCHAR(50) NOT NULL,
    lastname                      VARCHAR(50) NOT NULL,
    gender                        VARCHAR(50) NOT NULL,
    age                           integer     NOT NULL,
    eligibleForErasmusScholarship boolean     NOT NULL
);