package edu.maplewood.master_schedule.repository.specification;

import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.entity.Teacher;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public class TeacherSpecification {

  private TeacherSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static Specification<Teacher> build(
      String name,
      String email,
      Specialization specialization,
      Boolean available
  ) {
    List<Specification<Teacher>> specs = new ArrayList<>();
    specs.add(nameContains(name));
    specs.add(emailContains(email));
    specs.add(specializationEquals(specialization));
    specs.add(maxDailyIsNotFull(available));

    return specs.stream()
        .filter(Objects::nonNull)
        .reduce(Specification::and)
        .orElse(null);
  }

  private static Specification<Teacher> nameContains(String name) {
    if (name == null || name.isBlank()) {
      return null;
    }

    return (root, query, cb) ->
        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
  }

  private static Specification<Teacher> emailContains(String email) {
    if (email == null || email.isBlank()) {
      return null;
    }

    return (root, query, cb) ->
        cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
  }


  private static Specification<Teacher> specializationEquals(Specialization specialization) {
    if (specialization == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("specialization"), specialization);
  }

  private static Specification<Teacher> maxDailyIsNotFull(Boolean available) {
    if (available == null) {
      return null;
    }

    return (root, query, cb) -> {
      Subquery<Integer> weeklyLoad = query.subquery(Integer.class);
      Root<Teacher> t2 = weeklyLoad.from(Teacher.class);
      Join<?, ?> cs = t2.join("courseSections", JoinType.LEFT);
      Join<?, ?> course = cs.join("course", JoinType.LEFT);
      weeklyLoad.select(cb.coalesce(cb.sum(course.get("hoursPerWeek")), 0));
      weeklyLoad.where(cb.equal(t2.get("id"), root.get("id")));

      Expression<Integer> maxWeeklyCapacity = cb.prod(root.get("maxDailyHours"), cb.literal(5));

      if (available) {
        return cb.lessThan(weeklyLoad, maxWeeklyCapacity);
      } else {
        return cb.greaterThanOrEqualTo(weeklyLoad, maxWeeklyCapacity);
      }
    };
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String name;
    private String email;
    private Specialization specialization;
    private Boolean available;

    private Builder() {
      throw new IllegalStateException("Utility class");
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder specialization(Specialization specialization) {
      this.specialization = specialization;
      return this;
    }

    public Builder available(Boolean isAvailable) {
      this.available = isAvailable;
      return this;
    }

    public Specification<Teacher> build() {
      return TeacherSpecification.build(
          name,
          email,
          specialization,
          available
      );
    }
  }

}
