package edu.maplewood.master_schedule.repository.specification;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.CourseSection;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Teacher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public class CourseSectionSpecification {

  private CourseSectionSpecification() {
  }

  public static Specification<CourseSection> build(
      String code,
      Course course,
      Semester semester,
      Teacher teacher
  ) {
    List<Specification<CourseSection>> specs = new ArrayList<>();
    specs.add(codeContains(code));
    specs.add(courseEquals(course));
    specs.add(semesterEquals(semester));
    specs.add(teacherEquals(teacher));

    return specs.stream()
        .filter(Objects::nonNull)
        .reduce(Specification::and)
        .orElse(null);
  }

  private static Specification<CourseSection> codeContains(String code) {
    if (code == null || code.isBlank()) {
      return null;
    }

    return (root, query, cb) ->
        cb.like(cb.lower(root.get("code")), "%" + code.toLowerCase() + "%");
  }

  private static Specification<CourseSection> courseEquals(Course course) {
    if (course == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("course"), course);
  }

  private static Specification<CourseSection> semesterEquals(Semester semester) {
    if (semester == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("semester"), semester);
  }

  private static Specification<CourseSection> teacherEquals(Teacher teacher) {
    if (teacher == null) {
      return null;
    }

    return (root, query, cb) ->
        cb.equal(root.get("teacher"), teacher);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String code = null;
    private Course course = null;
    private Semester semester = null;
    private Teacher teacher = null;

    private Builder() {
    }

    public Builder code(String code) {
      this.code = code;
      return this;
    }

    public Builder course(Course course) {
      this.course = course;
      return this;
    }

    public Builder semester(Semester semester) {
      this.semester = semester;
      return this;
    }

    public Builder teacher(Teacher teacher) {
      this.teacher = teacher;
      return this;
    }

    public Specification<CourseSection> build() {
      return CourseSectionSpecification.build(code, course, semester, teacher);
    }
  }
}
