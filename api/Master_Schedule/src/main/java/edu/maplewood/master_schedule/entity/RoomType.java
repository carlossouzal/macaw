package edu.maplewood.master_schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name = "room_types")
public class RoomType {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(nullable = false, length = 50)
  private String name;
  private String description;

  @OneToMany(mappedBy = "roomType")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Specialization> specializations;

  @OneToMany(mappedBy = "roomType")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Classroom> classrooms;

}
