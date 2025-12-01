package edu.maplewood.master_schedule.controller.helper.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.maplewood.master_schedule.controller.dto.response.RoomTypeResponse;
import edu.maplewood.master_schedule.entity.RoomType;
import org.junit.jupiter.api.Test;

public class RoomTypeMapperTest {

  private RoomType createRoomType(Long id) {
    RoomType r = new RoomType();
    r.setId(id);
    r.setName("Room Type");
    r.setDescription("Description");
    return r;
  }

  @Test
  void toResponseSingleSpecializationMapAllFields() {
    RoomType roomType = createRoomType(1L);

    RoomTypeResponse dto = RoomTypeMapper.toResponse(roomType);

    assertEquals(1L, dto.id());
    assertEquals("Room Type", dto.name());
    assertEquals("Description", dto.description());
  }
}
