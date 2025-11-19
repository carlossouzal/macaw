package edu.maplewood.master_schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name = "specializations")
public class Specialization {

  @Id
  private Long id;
  @Column(nullable = false, length = 50)
  private String name;
  private String description;

  @OneToMany(mappedBy = "specialization")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Teacher> teachers;

  @ManyToOne
  private RoomType roomType;
}
