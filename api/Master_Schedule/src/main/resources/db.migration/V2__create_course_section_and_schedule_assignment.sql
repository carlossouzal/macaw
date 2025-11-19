CREATE TABLE IF NOT EXISTS course_section (
                                              id INTEGER PRIMARY KEY AUTOINCREMENT,
                                              course_id INTEGER,
                                              semester_id INTEGER,
                                              code VARCHAR(10) NOT NULL,
                                              teacher_id INTEGER,
                                              created_at DATETIME DEFAULT (datetime('now')),
    UNIQUE(course_id, semester_id, code),
    FOREIGN KEY(course_id) REFERENCES course(id) ON DELETE SET NULL,
    FOREIGN KEY(semester_id) REFERENCES semester(id) ON DELETE SET NULL,
    FOREIGN KEY(teacher_id) REFERENCES instructor(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_course_section_course_id ON course_section(course_id);
CREATE INDEX IF NOT EXISTS idx_course_section_teacher_id ON course_section(teacher_id);
CREATE INDEX IF NOT EXISTS idx_course_section_semester_id ON course_section(semester_id);

CREATE TABLE IF NOT EXISTS schedule_assignment (
                                                   id INTEGER PRIMARY KEY AUTOINCREMENT,
                                                   course_section_id INTEGER NOT NULL,
                                                   classroom_id INTEGER NOT NULL,
                                                   day_of_week VARCHAR(10) NOT NULL,
                                                   start_time TIME NOT NULL,
                                                   end_time TIME NOT NULL,
                                                   is_active BOOLEAN DEFAULT 0,
    FOREIGN KEY(course_section_id) REFERENCES course_section(id) ON DELETE SET NULL,
    FOREIGN KEY(classroom_id) REFERENCES classroom(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_schedule_assignment_section_id ON schedule_assignment(course_section_id);
CREATE INDEX IF NOT EXISTS idx_schedule_assignment_day ON schedule_assignment(day_of_week);
CREATE INDEX IF NOT EXISTS idx_schedule_assignment_classroom_id ON schedule_assignment(classroom_id);

CREATE TABLE student_course_sections (
                                         student_id INTEGER NOT NULL,
                                         course_section_id INTEGER NOT NULL,
                                         PRIMARY KEY (student_id, course_section_id),
                                         CONSTRAINT fk_student_course_sections_student
                                             FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                                         CONSTRAINT fk_student_course_sections_course_section
                                             FOREIGN KEY (course_section_id) REFERENCES course_sections(id) ON DELETE CASCADE
);

CREATE INDEX idx_student_course_sections_course_section_id
    ON student_course_sections(course_section_id);

CREATE INDEX idx_student_course_sections_student_id
    ON student_course_sections(student_id);
