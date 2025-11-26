package edu.maplewood.master_schedule.entity;

import edu.maplewood.master_schedule.entity.converter.DayOfWeekConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.DayOfWeek;
import lombok.Data;

@Data
@Entity
public class ScheduleAssignment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ManyToOne(optional = false)
  private CourseSection courseSection;
  @ManyToOne(optional = false)
  private Classroom classroom;

  @Convert(converter = DayOfWeekConverter.class)
  @Column(nullable = false, name = "day_of_week")
  private DayOfWeek dayOfWeek;

  @Column(name = "time_slot", nullable = false)
  private Integer timeSlot;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;
}
