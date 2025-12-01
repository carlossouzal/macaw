package edu.maplewood.master_schedule.entity;

import edu.maplewood.master_schedule.entity.converter.StudentStatusConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

@Data
@Entity(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "first_name", length = 50, nullable = false)
  private String firstName;
  @Column(name = "last_name", length = 50, nullable = false)
  private String lastName;
  @Column(unique = true)
  private String email;
  @Column(name = "grade_level", nullable = false)
  private Integer gradeLevel;
  @Column(name = "enrollment_year", nullable = false)
  private Integer enrollmentYear;
  @Column(name = "expected_graduation_year")
  private Integer expectedGraduationYear;
  @Convert(converter = StudentStatusConverter.class)
  @Column(length = 20)
  private StudentStatus status = StudentStatus.ACTIVE;
  @Column(name = "created_at", columnDefinition = "DATETIME")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<StudentCourseHistory> courseHistories;

  @ManyToMany
  @JoinTable(
      name = "student_course_sections",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_section_id")
  )
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<CourseSection> courseSectionsEnrolled;

  @Formula(
      "(SELECT IFNULL(ROUND(SUM(CASE WHEN sch.status = 'passed' THEN c.credits ELSE 0 END) / NULLIF(SUM(c.credits),0) * 4.0, 2), 0) "
          +
          "FROM student_course_history sch " +
          "LEFT JOIN courses c ON sch.course_id = c.id " +
          "WHERE sch.student_id = id)"
  )
  private Double GPA;

  public enum StudentStatus {
    ACTIVE,
    INACTIVE
  }
}