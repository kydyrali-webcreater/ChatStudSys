CREATE TABLE subjects (
                          id VARCHAR(255) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE subject_teachers (
                                  subject_id INTEGER NOT NULL,
                                  teacher_id INTEGER NOT NULL,
                                  PRIMARY KEY (subject_id, teacher_id),
                                  FOREIGN KEY (subject_id) REFERENCES subjects (id) ON DELETE CASCADE,
                                  FOREIGN KEY (teacher_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE user_subjects (
                               user_id INTEGER NOT NULL,
                               subject_id INTEGER NOT NULL,
                               PRIMARY KEY (user_id, subject_id),
                               FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                               FOREIGN KEY (subject_id) REFERENCES subjects (id) ON DELETE CASCADE
);
