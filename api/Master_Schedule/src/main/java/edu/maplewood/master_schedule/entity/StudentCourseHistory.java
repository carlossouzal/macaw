package edu.maplewood.master_schedule.entity;

import edu.maplewood.master_schedule.entity.converter.StudentStatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity(name = "student_course_history")
public class StudentCourseHistory {

  @Id
  private Long id;

  @ManyToOne(optional = false)
  private Student student;
  @ManyToOne(optional = false)
  private Course course;
  @ManyToOne(optional = false)
  private Semester semester;

  @Convert(converter = StudentStatusConverter.class)
  @Column(length = 20, nullable = false)
  private StudentCourseStatus status;

  @Column(name = "created_at", columnDefinition = "DATETIME")
  @CreationTimestamp
  private LocalDateTime createdAt;

  public enum StudentCourseStatus {
    PASSED,
    FAILED
  }
}
