package edu.maplewood.master_schedule.controller.helper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.SpecializationResponse;
import edu.maplewood.master_schedule.controller.dto.response.TeacherResponse;
import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.entity.Teacher;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class TeacherMapperTest {

  private Teacher createTeacher(Long id) {
    Teacher t = new Teacher();
    t.setId(id);
    t.setFirstName("First");
    t.setLastName("Last");
    t.setEmail("email");
    t.setCreatedAt(LocalDateTime.now());
    return t;
  }

  @Test
  void toResponseSingleTeacherMapsAllFields() {
    Specialization specialization = mock(Specialization.class);
    SpecializationResponse specializationResponse = mock(SpecializationResponse.class);
    Teacher teacher = createTeacher(1L);
    teacher.setSpecialization(specialization);

    try (MockedStatic<SpecializationMapper> mapper = mockStatic(SpecializationMapper.class)) {
      mapper.when(() -> SpecializationMapper.toResponse(specialization))
          .thenReturn(specializationResponse);
      TeacherResponse dto = TeacherMapper.toResponse(teacher);

      assertEquals(1L, dto.id());
      assertEquals("First", dto.firstName());
      assertEquals("Last", dto.lastName());
      assertEquals("email", dto.email());
      assertEquals(dto.specialization(), specializationResponse);
    }
  }

  @Test
  void toResponseSingleTeacherMapsWithoutSpecialization() {
    Teacher teacher = createTeacher(1L);

    TeacherResponse dto = TeacherMapper.toResponse(teacher);

    assertEquals(1L, dto.id());
    assertEquals("First", dto.firstName());
    assertEquals("Last", dto.lastName());
    assertEquals("email", dto.email());
    assertNull(dto.specialization());
  }

  @Test
  void toResponseListMapsEachItem() {
    List<Teacher> entities = List.of(createTeacher(1L), createTeacher(2L));

    List<TeacherResponse> dtos = TeacherMapper.toResponse(entities);

    assertEquals(2, dtos.size());
    assertEquals(1L, dtos.get(0).id());
    assertEquals(2L, dtos.get(1).id());
    assertEquals("First", dtos.get(0).firstName());
    assertEquals("First", dtos.get(1).firstName());
  }

  @Test
  void toResponsePageMapsContentAndPageMetadata() {
    List<Teacher> content = List.of(createTeacher(10L), createTeacher(11L), createTeacher(12L));
    PageRequest pageable = PageRequest.of(1, 3);
    Page<Teacher> page = new PageImpl<>(content, pageable, 9);

    ResponseList<TeacherResponse> responseList = TeacherMapper.toResponse(page);

    assertEquals(9, responseList.total());
    assertEquals(1, responseList.page());
    assertEquals(3, responseList.size());
    assertEquals(3, responseList.items().size());
    assertEquals(10L, responseList.items().get(0).id());
    assertEquals(12L, responseList.items().get(2).id());
    assertEquals(11L, responseList.items().get(1).id());
  }

}
