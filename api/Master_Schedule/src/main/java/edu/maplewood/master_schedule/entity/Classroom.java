package edu.maplewood.master_schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name = "classrooms")
public class Classroom {

  @Id
  private Long id;
  @Column(length = 20, nullable = false)
  private String name;
  private Integer capacity = 10;
  private String equipment;
  private Integer floor;
  @Column(name = "created_at", columnDefinition = "DATETIME")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne(optional = false)
  private RoomType roomType;

  @OneToMany
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<ScheduleAssignment> assignments;
}
