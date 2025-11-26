package edu.maplewood.master_schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.repository.ClassroomRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;


public class ClassroomServiceTest {

  private ClassroomRepository classroomRepository;
  private ClassroomService classroomService;

  @BeforeEach
  void setUp() {
    classroomRepository = mock(ClassroomRepository.class);
    classroomService = new ClassroomService(classroomRepository);
  }

  @Test
  void testFindAll() {
    List<Classroom> classrooms = List.of(mock(Classroom.class), mock(Classroom.class),
        mock(Classroom.class), mock(Classroom.class));

    when(classroomRepository.findAll()).thenReturn(classrooms);

    List<Classroom> result = classroomService.findAll();

    assertEquals(4, result.size());
    assertThat(result).isEqualTo(classrooms);
  }

  @Test
  void testFindAllEmpty() {
    List<Classroom> classrooms = List.of();

    when(classroomRepository.findAll()).thenReturn(classrooms);

    List<Classroom> result = classroomService.findAll();

    assertEquals(0, result.size());
    assertThat(result).isEqualTo(classrooms);

  }

  @Test
  void testNextAvailableForRoomType() {
    List<Classroom> classrooms = List.of(mock(Classroom.class), mock(Classroom.class),
        mock(Classroom.class), mock(Classroom.class));
    RoomType roomType = mock(RoomType.class);

    when(classroomRepository.findAll(any(Specification.class))).thenReturn(classrooms);

    List<Classroom> result = classroomService.nextAvailableFor(roomType);

    assertEquals(4, result.size());
    assertThat(result).isEqualTo(classrooms);
  }

  @Test
  void testNextAvailableForRoomTypeNull() {
    List<Classroom> classrooms = List.of();

    when(classroomRepository.findAll()).thenReturn(classrooms);

    List<Classroom> result = classroomService.nextAvailableFor(null);

    assertEquals(0, result.size());
    assertThat(result).isEqualTo(classrooms);
  }
}
