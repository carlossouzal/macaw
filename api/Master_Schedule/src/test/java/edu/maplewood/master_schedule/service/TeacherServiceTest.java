package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.entity.Teacher;
import edu.maplewood.master_schedule.repository.TeacherRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeacherServiceTest {

  private TeacherRepository repository;
  private TeacherService service;
  private SpecializationService specializationService;

  @BeforeEach
  public void setUp() {
    repository = mock(TeacherRepository.class);
    specializationService = mock(SpecializationService.class);
    service = new TeacherService(repository, specializationService);
  }

  @Test
  public void testFindAll() {
    Teacher teacher = createTeacher();
    when(repository.findAll()).thenReturn(List.of(teacher));

    List<Teacher> teachers = service.findAll();

    verify(repository).findAll();

    assertThat(teachers).containsExactly(teacher);
  }

  public static Teacher createTeacher() {
    Specialization specialization = mock(Specialization.class);
    Teacher teacher = new Teacher();
    teacher.setId(1L);
    teacher.setFirstName("Jane");
    teacher.setLastName("Smith");
    teacher.setSpecialization(specialization);
    return teacher;
  }
}
