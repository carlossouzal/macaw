package edu.maplewood.master_schedule.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpecializationResponse(
    Long id,
    String name,
    String description,
    @JsonProperty("room_type") RoomTypeResponse roomType
) {

}
