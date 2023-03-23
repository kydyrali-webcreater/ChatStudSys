CREATE TABLE IF NOT EXISTS subjects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    course_code VARCHAR(255) NOT NULL,
    student_ids text NOT NULL,
    teacher_id VARCHAR(255) NOT NULL,
    time TIMESTAMP NOT NULL,
    week_day VARCHAR(10) NOT NULL
);