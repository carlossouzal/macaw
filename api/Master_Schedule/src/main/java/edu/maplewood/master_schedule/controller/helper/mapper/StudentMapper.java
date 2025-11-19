package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.response.StudentResponse;
import edu.maplewood.master_schedule.entity.Student;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentMapper {

  private static final Logger logger = LoggerFactory.getLogger(StudentMapper.class);

  public static StudentResponse toResponse(Student student) {
    logger.debug("Mapping Student entity to StudentDTO: {}", student);
    return new StudentResponse(student.getId(),
        student.getFirstName(),
        student.getLastName(),
        student.getEmail(),
        student.getGradeLevel(),
        student.getEnrollmentYear(),
        student.getExpectedGraduationYear(),
        student.getStatus(),
        student.getCreatedAt(),
        student.getGPA());
  }

  public static List<StudentResponse> toResponse(List<Student> students) {
    logger.debug("Mapping list of Student entities to list of StudentDTOs. Total students: {}",
        students.size());
    return students.stream().map(StudentMapper::toResponse).toList();
  }
}
