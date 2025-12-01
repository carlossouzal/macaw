package edu.maplewood.master_schedule.controller.helper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import edu.maplewood.master_schedule.controller.dto.response.ClassroomResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.RoomTypeResponse;
import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.entity.RoomType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class ClassroomMapperTest {

  private Classroom createClassroom(Long id) {
    Classroom c = new Classroom();
    c.setId(id);
    c.setName("Test Classroom");
    c.setEquipment("Equipment");
    c.setFloor(1);
    return c;
  }

  @Test
  void toResponseSingleClassroomMapsAllFields() {
    RoomType roomType = mock(RoomType.class);
    RoomTypeResponse roomTypeResponse = mock(RoomTypeResponse.class);
    Classroom classroom = createClassroom(1L);
    classroom.setRoomType(roomType);

    try (MockedStatic<RoomTypeMapper> mapper = mockStatic(RoomTypeMapper.class)) {
      mapper.when(() -> RoomTypeMapper.toResponse(roomType))
          .thenReturn(roomTypeResponse);
      ClassroomResponse dto = ClassroomMapper.toResponse(classroom);

      assertEquals(1L, dto.id());
      assertEquals("Test Classroom", dto.name());
      assertEquals("Equipment", dto.equipment());
      assertEquals(1, dto.floor());
      assertEquals(10, dto.capacity());
      assertEquals(dto.roomType(), roomTypeResponse);
    }
  }

  @Test
  void toResponseSingleClassroomMapsWithoutSpecialization() {
    Classroom classroom = createClassroom(1L);

    ClassroomResponse dto = ClassroomMapper.toResponse(classroom);

    assertEquals(1L, dto.id());
    assertEquals("Test Classroom", dto.name());
    assertEquals("Equipment", dto.equipment());
    assertEquals(1, dto.floor());
    assertEquals(10, dto.capacity());
    assertNull(dto.roomType());
  }

  @Test
  void toResponseListMapsEachItem() {
    List<Classroom> entities = List.of(createClassroom(1L), createClassroom(2L));

    List<ClassroomResponse> dtos = ClassroomMapper.toResponse(entities);

    assertEquals(2, dtos.size());
    assertEquals(1L, dtos.get(0).id());
    assertEquals(2L, dtos.get(1).id());
    assertEquals("Test Classroom", dtos.get(0).name());
    assertEquals("Test Classroom", dtos.get(1).name());
  }

  @Test
  void toResponsePageMapsContentAndPageMetadata() {
    List<Classroom> content = List.of(createClassroom(10L), createClassroom(11L),
        createClassroom(12L));
    PageRequest pageable = PageRequest.of(1, 3);
    Page<Classroom> page = new PageImpl<>(content, pageable, 9);

    ResponseList<ClassroomResponse> responseList = ClassroomMapper.toResponse(page);

    assertEquals(9, responseList.total());
    assertEquals(1, responseList.page());
    assertEquals(3, responseList.size());
    assertEquals(3, responseList.items().size());
    assertEquals(10L, responseList.items().get(0).id());
    assertEquals(12L, responseList.items().get(2).id());
    assertEquals(11L, responseList.items().get(1).id());
  }
}
