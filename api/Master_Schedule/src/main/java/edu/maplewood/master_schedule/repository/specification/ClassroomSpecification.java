package edu.maplewood.master_schedule.repository.specification;

import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.entity.RoomType;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public class ClassroomSpecification {

  private ClassroomSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static Specification<Classroom> build(
      String name,
      Integer capacityMin,
      Integer capacityMax,
      String equipment,
      Integer floor,
      RoomType roomType,
      Boolean available
  ) {
    List<Specification<Classroom>> specs = new ArrayList<>();
    specs.add(nameContains(name));
    specs.add(capacityBetween(capacityMin, capacityMax));
    specs.add(equipmentContains(equipment));
    specs.add(floorEquals(floor));
    specs.add(roomTypeEquals(roomType));
    specs.add(isAvailable(available));

    return specs.stream()
        .filter(Objects::nonNull)
        .reduce(Specification::and)
        .orElse(null);
  }

  private static Specification<Classroom> nameContains(String name) {
    if (name == null || name.isBlank()) {
      return null;
    }

    return (root, query, cb) ->
        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  private static Specification<Classroom> capacityBetween(Integer capacityMin,
      Integer capacityMax) {
    if (capacityMin == null && capacityMax == null) {
      return null;
    }

    return (root, query, cb) -> {
      if (capacityMin != null && capacityMax != null) {
        return cb.between(root.get("capacity"), capacityMin, capacityMax);
      } else if (capacityMin != null) {
        return cb.greaterThanOrEqualTo(root.get("capacity"), capacityMin);
      } else {
        return cb.lessThanOrEqualTo(root.get("capacity"), capacityMax);
      }
    };
  }

  private static Specification<Classroom> equipmentContains(String equipment) {
    if (equipment == null || equipment.isBlank()) {
      return null;
    }

    return (root, query, cb) ->
        cb.like(cb.lower(root.get("equipment")), "%" + equipment.toLowerCase() + "%");
  }

  private static Specification<Classroom> floorEquals(Integer floor) {
    if (floor == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("floor"), floor);
  }

  private static Specification<Classroom> roomTypeEquals(RoomType roomType) {
    if (roomType == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("roomType"), roomType);
  }

  private static Specification<Classroom> isAvailable(Boolean available) {
    if (available == null) {
      return null;
    }

    return (root, query, cb) -> {
      Expression<Long> countExpr =
          cb.count(root.join("timeSlots", JoinType.LEFT));
      query.groupBy(root.get("id"));

      if (available) {
        return cb.lessThan(countExpr, countExpr);
      } else {
        return cb.greaterThanOrEqualTo(countExpr, countExpr);
      }
    };
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String name;
    private Integer capacityMin;
    private Integer capacityMax;
    private String equipment;
    private Integer floor;
    private RoomType roomType;
    private Boolean available;

    private Builder() {
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder capacityMin(Integer capacityMin) {
      this.capacityMin = capacityMin;
      return this;
    }

    public Builder capacityMax(Integer capacityMax) {
      this.capacityMax = capacityMax;
      return this;
    }

    public Builder equipment(String equipment) {
      this.equipment = equipment;
      return this;
    }

    public Builder floor(Integer floor) {
      this.floor = floor;
      return this;
    }

    public Builder roomType(RoomType roomType) {
      this.roomType = roomType;
      return this;
    }

    public Builder available(Boolean isAvailable) {
      this.available = isAvailable;
      return this;
    }

    public Specification<Classroom> build() {
      return ClassroomSpecification.build(
          name,
          capacityMin,
          capacityMax,
          equipment,
          floor,
          roomType,
          available
      );
    }
  }
}