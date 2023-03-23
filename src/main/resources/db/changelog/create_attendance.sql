CREATE TABLE attendance (
                            id SERIAL PRIMARY KEY,
                            subject_id INTEGER NOT NULL,
                            student_id INTEGER NOT NULL,
                            attended BOOLEAN NOT NULL,
                            date TIMESTAMP NOT NULL,
                            FOREIGN KEY (subject_id) REFERENCES subjects (id) ON DELETE CASCADE,
                            FOREIGN KEY (student_id) REFERENCES users (id) ON DELETE CASCADE
);