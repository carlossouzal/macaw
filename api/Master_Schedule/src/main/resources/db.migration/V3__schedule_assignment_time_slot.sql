DROP TABLE schedule_assignment;

CREATE TABLE schedule_assignment (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     course_section_id INTEGER NOT NULL,
                                     classroom_id INTEGER NOT NULL,
                                     day_of_week VARCHAR(10) NOT NULL,
                                     time_slot INTEGER NOT NULL,
                                     is_active BOOLEAN NOT NULL DEFAULT 0,
                                     FOREIGN KEY(course_section_id) REFERENCES course_section(id) ON DELETE RESTRICT,
                                     FOREIGN KEY(classroom_id) REFERENCES classroom(id) ON DELETE RESTRICT
);

CREATE INDEX idx_schedule_assignment_section_id ON schedule_assignment(course_section_id);
CREATE INDEX idx_schedule_assignment_day ON schedule_assignment(day_of_week);
CREATE INDEX idx_schedule_assignment_classroom_id ON schedule_assignment(classroom_id);
