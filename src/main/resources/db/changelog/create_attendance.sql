CREATE TABLE IF NOT EXISTS attendance (
    id SERIAL PRIMARY KEY,
    time TIMESTAMP NOT NULL,
    is_attendance BOOLEAN NOT NULL,
    student_id VARCHAR(255) NOT NULL,
    course_code VARCHAR(255) NOT NULL,
    puted_by_id VARCHAR(255) NOT NULL,
    puted_by_role VARCHAR(255) NOT NULL,
    attendance_type VARCHAR(255) NOT NULL
);