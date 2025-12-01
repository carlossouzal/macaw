package edu.maplewood.master_schedule.controller.dto.response;

import java.util.List;

public record ResponseList<T>(
    Long total,
    Integer page,
    Integer size,
    List<T> items
) {

}
