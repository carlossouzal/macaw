package edu.maplewood.master_schedule.controller.helper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import edu.maplewood.master_schedule.controller.dto.response.RoomTypeResponse;
import edu.maplewood.master_schedule.controller.dto.response.SpecializationResponse;
import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.entity.Specialization;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class SpecializationMapperTest {

  private Specialization createSpecialization(Long id) {
    Specialization s = new Specialization();
    s.setId(id);
    s.setName("First");
    s.setDescription("Description");
    return s;
  }

  @Test
  void toResponseSingleSpecializationMapAllFields() {
    RoomType roomType = mock(RoomType.class);
    Specialization specialization = createSpecialization(1L);
    specialization.setRoomType(roomType);
    RoomTypeResponse roomTypeResponse = mock(RoomTypeResponse.class);

    try (MockedStatic<RoomTypeMapper> mapper = mockStatic(RoomTypeMapper.class)) {
      mapper.when(() -> RoomTypeMapper.toResponse(roomType))
          .thenReturn(roomTypeResponse);

      SpecializationResponse dto = SpecializationMapper.toResponse(specialization);

      assertEquals(1L, dto.id());
      assertEquals("First", dto.name());
      assertEquals("Description", dto.description());
      assertEquals(roomTypeResponse, dto.roomType());
    }
  }

  @Test
  void toResponseSingleSpecializationMapWithoutRoomType() {
    Specialization specialization = createSpecialization(1L);

    SpecializationResponse dto = SpecializationMapper.toResponse(specialization);

    assertEquals(1L, dto.id());
    assertEquals("First", dto.name());
    assertEquals("Description", dto.description());
    assertNull(dto.roomType());
  }
}
