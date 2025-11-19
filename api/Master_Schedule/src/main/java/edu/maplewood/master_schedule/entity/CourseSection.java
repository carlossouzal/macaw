package edu.maplewood.master_schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity(name = "course_section")
public class CourseSection {

  @Id
  private Long id;
  @Column(nullable = false, length = 10)
  private String code;

  @ManyToOne(optional = false)
  private Course course;
  @ManyToOne(optional = false)
  private Semester semester;
  @ManyToOne
  private Teacher teacher;

  @Column(name = "created_at", columnDefinition = "DATETIME")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @OneToMany
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<ScheduleAssignment> scheduleAssignments;

  @ManyToMany(mappedBy = "courseSectionsEnrolled")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Student> students;
}
