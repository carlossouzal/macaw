package edu.maplewood.master_schedule.repository.specification;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.Course.CourseType;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Specialization;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public final class CourseSpecification {

  private CourseSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static Specification<Course> build(
      String code,
      String name,
      String description,
      BigDecimal creditsMin,
      BigDecimal creditsMax,
      Integer hoursPerWeekMin,
      Integer hoursPerWeekMax,
      CourseType courseType,
      Integer gradeLevelMin,
      Integer gradeLevelMax,
      Semester.OrderInYear semesterOrder,
      Specialization specialization,
      Course prerequisite
  ) {
    List<Specification<Course>> specs = new ArrayList<>();
    specs.add(codeContains(code));
    specs.add(nameContains(name));
    specs.add(descriptionContains(description));
    specs.add(creditsBetween(creditsMin, creditsMax));
    specs.add(hoursPerWeekBetween(hoursPerWeekMin, hoursPerWeekMax));
    specs.add(isCourseType(courseType));
    specs.add(gradeLevelBetween(gradeLevelMin, gradeLevelMax));
    specs.add(isSemesterOrder(semesterOrder));
    specs.add(isSpecialization(specialization));
    specs.add(isPrerequisite(prerequisite));

    return specs.stream()
        .filter(Objects::nonNull)
        .reduce(Specification::and)
        .orElse(null);
  }

  private static Specification<Course> codeContains(String code) {
    if (code == null || code.isBlank()) {
      return null;
    }

    return (root, query, cb) -> {
      String pattern = "%" + code.toLowerCase() + "%";
      return cb.like(cb.lower(root.get("code")), pattern);
    };
  }

  private static Specification<Course> nameContains(String name) {
    if (name == null || name.isBlank()) {
      return null;
    }

    return (root, query, cb) -> {
      String pattern = "%" + name.toLowerCase() + "%";
      return cb.like(cb.lower(root.get("name")), pattern);
    };
  }

  private static Specification<Course> descriptionContains(String description) {
    if (description == null || description.isBlank()) {
      return null;
    }

    return (root, query, cb) -> {
      String pattern = "%" + description.toLowerCase() + "%";
      return cb.like(cb.lower(root.get("description")), pattern);
    };
  }

  private static Specification<Course> creditsBetween(BigDecimal creditsMin,
      BigDecimal creditsMax) {
    if (creditsMin == null && creditsMax == null) {
      return null;
    }

    return (root, query, cb) -> {
      if (creditsMin != null && creditsMax != null) {
        return cb.between(root.get("credits"), creditsMin, creditsMax);
      } else if (creditsMin != null) {
        return cb.greaterThanOrEqualTo(root.get("credits"), creditsMin);
      } else {
        return cb.lessThanOrEqualTo(root.get("credits"), creditsMax);
      }
    };
  }

  private static Specification<Course> hoursPerWeekBetween(Integer hoursPerWeekMin,
      Integer hoursPerWeekMax) {
    if (hoursPerWeekMin == null && hoursPerWeekMax == null) {
      return null;
    }

    return (root, query, cb) -> {
      if (hoursPerWeekMin != null && hoursPerWeekMax != null) {
        return cb.between(root.get("hoursPerWeek"), hoursPerWeekMin, hoursPerWeekMax);
      } else if (hoursPerWeekMin != null) {
        return cb.greaterThanOrEqualTo(root.get("hoursPerWeek"), hoursPerWeekMin);
      } else {
        return cb.lessThanOrEqualTo(root.get("hoursPerWeek"), hoursPerWeekMax);
      }
    };
  }

  private static Specification<Course> isCourseType(CourseType courseType) {
    if (courseType == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("courseType"), courseType);
  }

  private static Specification<Course> gradeLevelBetween(Integer gradeLevelMin,
      Integer gradeLevelMax) {
    if (gradeLevelMin == null && gradeLevelMax == null) {
      return null;
    }

    return (root, query, cb) -> {
      if (gradeLevelMin != null && gradeLevelMax != null) {
        return cb.between(root.get("gradeLevelMin"), gradeLevelMin, gradeLevelMax);
      } else if (gradeLevelMin != null) {
        return cb.greaterThanOrEqualTo(root.get("gradeLevelMin"), gradeLevelMin);
      } else {
        return cb.lessThanOrEqualTo(root.get("gradeLevelMax"), gradeLevelMax);
      }
    };
  }

  private static Specification<Course> isSemesterOrder(Semester.OrderInYear semesterOrder) {
    if (semesterOrder == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("semesterOrder"), semesterOrder);
  }

  private static Specification<Course> isSpecialization(Specialization specialization) {
    if (specialization == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("specialization"), specialization);
  }

  private static Specification<Course> isPrerequisite(Course prerequisite) {
    if (prerequisite == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("prerequisite"), prerequisite);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String code;
    private String name;
    private String description;
    private BigDecimal creditsMin;
    private BigDecimal creditsMax;
    private Integer hoursPerWeekMin;
    private Integer hoursPerWeekMax;
    private CourseType courseType;
    private Integer gradeLevelMin;
    private Integer gradeLevelMax;
    private Semester.OrderInYear semesterOrder;
    private Specialization specialization;
    private Course prerequisite;

    private Builder() {
    }

    public Builder code(String code) {
      this.code = code;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder creditsMin(BigDecimal creditsMin) {
      this.creditsMin = creditsMin;
      return this;
    }

    public Builder creditsMax(BigDecimal creditsMax) {
      this.creditsMax = creditsMax;
      return this;
    }

    public Builder hoursPerWeekMin(Integer hoursPerWeekMin) {
      this.hoursPerWeekMin = hoursPerWeekMin;
      return this;
    }

    public Builder hoursPerWeekMax(Integer hoursPerWeekMax) {
      this.hoursPerWeekMax = hoursPerWeekMax;
      return this;
    }

    public Builder courseType(CourseType courseType) {
      this.courseType = courseType;
      return this;
    }

    public Builder gradeLevelMin(Integer gradeLevelMin) {
      this.gradeLevelMin = gradeLevelMin;
      return this;
    }

    public Builder gradeLevelMax(Integer gradeLevelMax) {
      this.gradeLevelMax = gradeLevelMax;
      return this;
    }

    public Builder semesterOrder(Semester.OrderInYear semesterOrder) {
      this.semesterOrder = semesterOrder;
      return this;
    }

    public Builder specialization(Specialization specialization) {
      this.specialization = specialization;
      return this;
    }

    public Builder prerequisite(Course prerequisite) {
      this.prerequisite = prerequisite;
      return this;
    }

    public Specification<Course> build() {
      return CourseSpecification.build(
          code,
          name,
          description,
          creditsMin,
          creditsMax,
          hoursPerWeekMin,
          hoursPerWeekMax,
          courseType,
          gradeLevelMin,
          gradeLevelMax,
          semesterOrder,
          specialization,
          prerequisite
      );
    }
  }
}
