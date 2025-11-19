package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.repository.ClassroomRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassroomServiceTest {

  private ClassroomRepository classroomRepository;
  private ClassroomService classroomService;

  @BeforeEach
  public void setUp() {
    classroomRepository = mock(ClassroomRepository.class);
    classroomService = new ClassroomService(classroomRepository);
  }

  @Test
  public void testFindAll() {
    Classroom classroom1 = createMockClassroom();
    when(classroomRepository.findAll()).thenReturn(List.of(classroom1));

    List<Classroom> classrooms = classroomService.findAll();

    verify(classroomRepository).findAll();

    assertThat(classrooms).containsExactly(classroom1);
  }


  public static Classroom createMockClassroom() {
    Classroom classroom = new Classroom();
    classroom.setId(1L);
    classroom.setName("Room 101");
    classroom.setCapacity(30);
    classroom.setEquipment("Projector, Whiteboard");
    classroom.setFloor(1);
    return classroom;
  }
}
