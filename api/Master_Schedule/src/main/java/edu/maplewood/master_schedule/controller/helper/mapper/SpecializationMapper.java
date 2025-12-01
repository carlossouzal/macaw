package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.response.RoomTypeResponse;
import edu.maplewood.master_schedule.controller.dto.response.SpecializationResponse;
import edu.maplewood.master_schedule.entity.Specialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecializationMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpecializationMapper.class);

  public static SpecializationResponse toResponse(Specialization specialization) {
    LOGGER.info("Converting Specialization to DTO");

    RoomTypeResponse roomTypeResponse = null;
    if (specialization.getRoomType() != null) {
      roomTypeResponse = RoomTypeMapper.toResponse(specialization.getRoomType());
    }

    return new SpecializationResponse(
        specialization.getId(),
        specialization.getName(),
        specialization.getDescription(),
        roomTypeResponse
    );
  }
}
