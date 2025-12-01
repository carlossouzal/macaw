package edu.maplewood.master_schedule.controller.helper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.maplewood.master_schedule.controller.dto.response.StudentResponse;
import edu.maplewood.master_schedule.entity.Student;
import edu.maplewood.master_schedule.entity.Student.StudentStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StudentMapperTest {

  private Student mockStudent(
      Long id,
      String firstName,
      String lastName,
      String email,
      Integer gradeLevel,
      Integer enrollmentYear,
      Integer expectedGraduationYear,
      StudentStatus status,
      LocalDateTime createdAt,
      Double gpa
  ) {
    Student student = Mockito.mock(Student.class);
    Mockito.when(student.getId()).thenReturn(id);
    Mockito.when(student.getFirstName()).thenReturn(firstName);
    Mockito.when(student.getLastName()).thenReturn(lastName);
    Mockito.when(student.getEmail()).thenReturn(email);
    Mockito.when(student.getGradeLevel()).thenReturn(gradeLevel);
    Mockito.when(student.getEnrollmentYear()).thenReturn(enrollmentYear);
    Mockito.when(student.getExpectedGraduationYear()).thenReturn(expectedGraduationYear);
    Mockito.when(student.getStatus()).thenReturn(status);
    Mockito.when(student.getCreatedAt()).thenReturn(createdAt);
    Mockito.when(student.getGPA()).thenReturn(gpa);
    return student;
  }

  @Test
  void toResponseMapsAllFields() {
    LocalDateTime now = LocalDateTime.now();
    Student student = mockStudent(
        1L, "Jane", "Doe", "jane.doe@example.com", 10,
        2023, 2026, StudentStatus.ACTIVE, now, 3.85
    );

    StudentResponse response = StudentMapper.toResponse(student);

    assertNotNull(response);
    assertEquals(1L, response.id());
    assertEquals("Jane", response.firstName());
    assertEquals("Doe", response.lastName());
    assertEquals("jane.doe@example.com", response.email());
    assertEquals(10, response.gradeLevel());
    assertEquals(2023, response.enrollmentYear());
    assertEquals(2026, response.expectedGraduationYear());
    assertEquals(StudentStatus.ACTIVE, response.status());
    assertEquals(now, response.createdAt());
    assertEquals(3.85, response.gpa());
  }

  @Test
  void toResponseListMapsMultiple() {
    LocalDateTime t1 = LocalDateTime.now();
    LocalDateTime t2 = LocalDateTime.now();

    Student s1 = mockStudent(
        10L, "Alice", "Smith", "alice@school.org", 11,
        2022, 2025, StudentStatus.ACTIVE, t1, 3.6
    );
    Student s2 = mockStudent(
        11L, "Bob", "Brown", "bob@school.org", 12,
        2021, 2024, StudentStatus.ACTIVE, t2, 2.9
    );

    List<StudentResponse> responses = StudentMapper.toResponse(List.of(s1, s2));

    assertNotNull(responses);
    assertEquals(2, responses.size());

    StudentResponse r1 = responses.get(0);
    assertEquals(10L, r1.id());
    assertEquals("Alice", r1.firstName());
    assertEquals("Smith", r1.lastName());
    assertEquals("alice@school.org", r1.email());
    assertEquals(11, r1.gradeLevel());
    assertEquals(2022, r1.enrollmentYear());
    assertEquals(2025, r1.expectedGraduationYear());
    assertEquals(StudentStatus.ACTIVE, r1.status());
    assertEquals(t1, r1.createdAt());
    assertEquals(3.6, r1.gpa());

    StudentResponse r2 = responses.get(1);
    assertEquals(11L, r2.id());
    assertEquals("Bob", r2.firstName());
    assertEquals("Brown", r2.lastName());
    assertEquals("bob@school.org", r2.email());
    assertEquals(12, r2.gradeLevel());
    assertEquals(2021, r2.enrollmentYear());
    assertEquals(2024, r2.expectedGraduationYear());
    assertEquals(StudentStatus.ACTIVE, r2.status());
    assertEquals(t2, r2.createdAt());
    assertEquals(2.9, r2.gpa());
  }

  @Test
  void toResponseNullThrows() {
    assertThrows(NullPointerException.class, () -> StudentMapper.toResponse((Student) null));
  }

}
