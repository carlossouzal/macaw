package edu.maplewood.master_schedule.controller.dto.response;

import java.util.List;

public record SemesterResponseList(
    Long total,
    Integer page,
    Integer size,
    List<SemesterResponse> semesters
) {

}
