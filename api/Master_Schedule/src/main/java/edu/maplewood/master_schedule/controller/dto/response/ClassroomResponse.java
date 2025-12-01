package edu.maplewood.master_schedule.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ClassroomResponse(
    Long id,
    String name,
    Integer capacity,
    String equipment,
    Integer floor,
    @JsonProperty("created_at") LocalDateTime createdAt,
    @JsonProperty("room_type") RoomTypeResponse roomType
) {

}
