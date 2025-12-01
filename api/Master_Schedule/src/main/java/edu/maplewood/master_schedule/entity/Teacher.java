package edu.maplewood.master_schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity(name = "teachers")
public class Teacher {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "first_name", length = 50, nullable = false)
  private String firstName;
  @Column(name = "last_name", length = 50, nullable = false)
  private String lastName;
  @Column(unique = true, length = 100)
  private String email;
  @Column(name = "max_daily_hours")
  private Integer maxDailyHours = 4;
  @Column(name = "created_at", columnDefinition = "DATETIME")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne(optional = false)
  private Specialization specialization;

  @OneToMany(mappedBy = "teacher")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<CourseSection> courseSections;
}
