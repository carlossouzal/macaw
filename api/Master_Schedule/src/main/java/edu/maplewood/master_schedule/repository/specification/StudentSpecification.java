package edu.maplewood.master_schedule.repository.specification;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.Student;
import edu.maplewood.master_schedule.entity.StudentCourseHistory;
import edu.maplewood.master_schedule.entity.StudentCourseHistory.StudentCourseStatus;
import jakarta.persistence.criteria.Join;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class StudentSpecification {

  private StudentSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static Specification<Student> build(
      String name,
      String email,
      Integer gradeLevelMin,
      Integer gradeLevelMax,
      Integer enrollmentYearMin,
      Integer enrollmentYearMax,
      Integer expectedGraduationYearMin,
      Integer expectedGraduationYearMax,
      Course prerequiredCourse,
      Student.StudentStatus status
  ) {
    List<Specification<Student>> specs = new ArrayList<>();
    specs.add(nameContains(name));
    specs.add(emailContains(email));
    specs.add(gradeLevelBetween(gradeLevelMin, gradeLevelMax));
    specs.add(enrollmentYearBetween(enrollmentYearMin, enrollmentYearMax));
    specs.add(expectedGraduationYearBetween(expectedGraduationYearMin, expectedGraduationYearMax));
    specs.add(prerequiredCourseContains(prerequiredCourse));
    specs.add(isStatus(status));

    return specs.stream()
        .filter(Objects::nonNull)
        .reduce(Specification::and)
        .orElse(null);
  }

  private static Specification<Student> nameContains(String name) {
    if (!StringUtils.hasText(name)) {
      return null;
    }

    return (root, query, cb) -> {
      String pattern = "%" + name.toLowerCase() + "%";
      return cb.or(
          cb.like(cb.lower(root.get("firstName")), pattern),
          cb.like(cb.lower(root.get("lastName")), pattern)
      );
    };
  }

  private static Specification<Student> emailContains(String email) {
    if (!StringUtils.hasText(email)) {
      return null;
    }

    return (root, query, cb) ->
        cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
  }

  private static Specification<Student> gradeLevelBetween(Integer gradeLevelMin,
      Integer gradeLevelMax) {
    if (gradeLevelMin == null && gradeLevelMax == null) {
      return null;
    }

    return (root, query, cb) -> {
      if (gradeLevelMin != null && gradeLevelMax != null) {
        return cb.between(root.get("gradeLevel"), gradeLevelMin, gradeLevelMax);
      } else if (gradeLevelMin != null) {
        return cb.greaterThanOrEqualTo(root.get("gradeLevel"), gradeLevelMin);
      } else {
        return cb.lessThanOrEqualTo(root.get("gradeLevel"), gradeLevelMax);
      }
    };
  }

  private static Specification<Student> enrollmentYearBetween(Integer enrollmentYearMin,
      Integer enrollmentYearMax) {
    if (enrollmentYearMin == null && enrollmentYearMax == null) {
      return null;
    }

    return (root, query, cb) -> {
      if (enrollmentYearMin != null && enrollmentYearMax != null) {
        return cb.between(root.get("enrollmentYear"), enrollmentYearMin, enrollmentYearMax);
      } else if (enrollmentYearMin != null) {
        return cb.greaterThanOrEqualTo(root.get("enrollmentYear"), enrollmentYearMin);
      } else {
        return cb.lessThanOrEqualTo(root.get("enrollmentYear"), enrollmentYearMax);
      }
    };
  }

  private static Specification<Student> expectedGraduationYearBetween(
      Integer expectedGraduationYearMin,
      Integer expectedGraduationYearMax) {
    if (expectedGraduationYearMin == null && expectedGraduationYearMax == null) {
      return null;
    }

    return (root, query, cb) -> {
      if (expectedGraduationYearMin != null && expectedGraduationYearMax != null) {
        return cb.between(root.get("expectedGraduationYear"), expectedGraduationYearMin,
            expectedGraduationYearMax);
      } else if (expectedGraduationYearMin != null) {
        return cb.greaterThanOrEqualTo(root.get("expectedGraduationYear"),
            expectedGraduationYearMin);
      } else {
        return cb.lessThanOrEqualTo(root.get("expectedGraduationYear"),
            expectedGraduationYearMax);
      }
    };
  }

  private static Specification<Student> prerequiredCourseContains(Course prerquiredCourse) {
    if (prerquiredCourse == null) {
      return null;
    }

    return (root, query, cb) -> {
      Join<Student, StudentCourseHistory> courseHistoryJoin = root.join("courseHistories");
      return cb.and(
          cb.equal(courseHistoryJoin.get("course"), prerquiredCourse),
          cb.equal(courseHistoryJoin.get("status"), StudentCourseStatus.PASSED)
      );
    };
  }

  private static Specification<Student> isStatus(Student.StudentStatus status) {
    if (status == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("status"), status);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String name;
    private String email;
    private Integer gradeLevelMin;
    private Integer gradeLevelMax;
    private Integer enrollmentYearMin;
    private Integer enrollmentYearMax;
    private Integer expectedGraduationYearMin;
    private Integer expectedGraduationYearMax;
    private Course prerquiredCourse;
    private Student.StudentStatus status;

    private Builder() {
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder gradeLevelBetween(Integer min, Integer max) {
      this.gradeLevelMin = min;
      this.gradeLevelMax = max;
      return this;
    }

    public Builder enrollmentYearBetween(Integer min, Integer max) {
      this.enrollmentYearMin = min;
      this.enrollmentYearMax = max;
      return this;
    }

    public Builder expectedGraduationYearBetween(Integer min, Integer max) {
      this.expectedGraduationYearMin = min;
      this.expectedGraduationYearMax = max;
      return this;
    }

    public Builder prerequiredCourse(Course prerquiredCourse) {
      this.prerquiredCourse = prerquiredCourse;
      return this;
    }

    public Builder status(Student.StudentStatus status) {
      this.status = status;
      return this;
    }

    public Specification<Student> build() {
      return StudentSpecification.build(
          name,
          email,
          gradeLevelMin,
          gradeLevelMax,
          enrollmentYearMin,
          enrollmentYearMax,
          expectedGraduationYearMin,
          expectedGraduationYearMax,
          prerquiredCourse,
          status
      );
    }
  }
}
