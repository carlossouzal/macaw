package edu.maplewood.master_schedule.entity;

import edu.maplewood.master_schedule.entity.converter.CourseTypeConverter;
import edu.maplewood.master_schedule.entity.converter.SemesterOrderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity(name = "courses")
public class Course {

  @Id
  private Long id;
  @Column(nullable = false, length = 10)
  private String code;
  @Column(length = 100, nullable = false)
  private String name;
  private String description;
  @Column(precision = 3, scale = 1, nullable = false)
  private BigDecimal credits;
  @Column(name = "hours_per_week", nullable = false)
  private Integer hoursPerWeek;
  @Convert(converter = CourseTypeConverter.class)
  @Column(name = "course_type", length = 20, nullable = false)
  private CourseType courseType;
  @Column(name = "grade_level_min")
  private Integer gradeLevelMin;
  @Column(name = "grade_level_max")
  private Integer gradeLevelMax;
  @Convert(converter = SemesterOrderConverter.class)
  @Column(name = "semester_order", nullable = false)
  private Semester.OrderInYear semesterOrder;
  @Column(name = "created_at", columnDefinition = "DATETIME")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne(optional = false)
  private Specialization specialization;
  @ManyToOne
  private Course prerequisite;
  @OneToMany(mappedBy = "prerequisite")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Course> prerequisiteOf;

  @OneToMany(mappedBy = "course")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<CourseSection> courseSections;
  @OneToMany(mappedBy = "course")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<StudentCourseHistory> studentCourseHistories;

  public enum CourseType {
    CORE,
    ELECTIVE,
  }
}
