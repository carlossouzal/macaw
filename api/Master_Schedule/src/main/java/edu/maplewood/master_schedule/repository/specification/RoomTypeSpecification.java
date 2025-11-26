package edu.maplewood.master_schedule.repository.specification;

import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.entity.Specialization;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public final class RoomTypeSpecification {

  private RoomTypeSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static Specification<RoomType> build(
      String name,
      String description,
      Specialization specialization
  ) {
    List<Specification<RoomType>> specs = new ArrayList<>();
    specs.add(nameContains(name));
    specs.add(descriptionContains(description));
    specs.add(containsSpecializations(specialization));

    return specs.stream()
        .filter(Objects::nonNull)
        .reduce(Specification::and)
        .orElse(null);
  }

  private static Specification<RoomType> nameContains(String name) {
    if (name == null || name.isBlank()) {
      return null;
    }

    return (root, query, cb) -> {
      String pattern = "%" + name.toLowerCase() + "%";
      return cb.like(cb.lower(root.get("name")), pattern);
    };
  }

  private static Specification<RoomType> descriptionContains(String description) {
    if (description == null || description.isBlank()) {
      return null;
    }

    return (root, query, cb) -> {
      String pattern = "%" + description.toLowerCase() + "%";
      return cb.like(cb.lower(root.get("description")), pattern);
    };
  }

  private static Specification<RoomType> containsSpecializations(Specialization specialization) {
    if (specialization == null) {
      return null;
    }

    return (root, query, cb) -> {
      var join = root.join("specializations");
      query.distinct(true);
      if (specialization.getId() != null) {
        return cb.equal(join.get("id"), specialization.getId());
      }
      return cb.equal(join, specialization);
    };
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String name;
    private String description;
    private Specialization specialization;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder specialization(Specialization specialization) {
      this.specialization = specialization;
      return this;
    }

    public Specification<RoomType> build() {
      return RoomTypeSpecification.build(
          name,
          description,
          specialization
      );
    }
  }

}
