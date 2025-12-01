package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.response.RoomTypeResponse;
import edu.maplewood.master_schedule.entity.RoomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomTypeMapper {

  private static Logger LOGGER = LoggerFactory.getLogger(RoomTypeMapper.class);

  public static RoomTypeResponse toResponse(RoomType roomType) {
    LOGGER.info("Converting RoomType to DTO");

    return new RoomTypeResponse(
        roomType.getId(),
        roomType.getName(),
        roomType.getDescription()
    );
  }
}
