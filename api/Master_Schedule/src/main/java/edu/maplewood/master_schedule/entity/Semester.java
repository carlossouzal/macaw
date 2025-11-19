package edu.maplewood.master_schedule.entity;

import edu.maplewood.master_schedule.entity.converter.SemesterOrderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity(name = "semesters")
public class Semester {

  @Id
  private Long id;
  @Column(nullable = false, length = 50)
  private String name;
  @Column(nullable = false)
  private Integer year;
  @Convert(converter = SemesterOrderConverter.class)
  @Column(name = "order_in_year", nullable = false)
  private OrderInYear orderInYear;
  @Column(name = "start_date", columnDefinition = "DATE")
  private LocalDate startDate;
  @Column(name = "end_date", columnDefinition = "DATE")
  private LocalDate endDate;
  @Column(name = "is_active", nullable = false)
  private Boolean isActive = false;
  @Column(name = "created_at", columnDefinition = "DATETIME")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "semester")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<CourseSection> courseSections;
  @OneToMany(mappedBy = "semester")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<StudentCourseHistory> studentCourseHistories;

  public enum OrderInYear {
    FALL(1),
    SPRING(2);

    private final int order;

    OrderInYear(int order) {
      this.order = order;
    }

    public int getOrder() {
      return order;
    }
  }
}
