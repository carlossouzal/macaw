package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.request.CreateSemesterRequest;
import edu.maplewood.master_schedule.controller.dto.response.SemesterResponse;
import edu.maplewood.master_schedule.controller.dto.response.SemesterResponseList;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class SemesterMapper {

  private static final Logger logger = LoggerFactory.getLogger(SemesterMapper.class);

  public static SemesterResponse toResponse(Semester semester) {
    logger.debug("Converting semester to DTO");
    return new SemesterResponse(
        semester.getId(),
        semester.getName(),
        semester.getYear(),
        semester.getOrderInYear(),
        semester.getStartDate(),
        semester.getEndDate(),
        semester.getIsActive(),
        semester.getCreatedAt());
  }

  public static List<SemesterResponse> toResponse(List<Semester> semesters) {
    logger.debug("Mapping list of Semester entities to list of SemesterDTOs. Total semesters: {}",
        semesters.size());
    return semesters.stream().map(SemesterMapper::toResponse).toList();
  }

  public static SemesterResponseList toResponse(Page<Semester> semesterPage) {
    logger.debug("Mapping list of Semester entities to SemesterResponseList. Total semesters: {}",
        semesterPage.getTotalElements());
    List<Semester> semesterList = semesterPage.getContent();
    List<SemesterResponse> semesterResponses = toResponse(semesterList);

    return new SemesterResponseList(semesterPage.getTotalElements(),
        semesterPage.getNumber(), semesterPage.getSize(), semesterResponses);
  }

  public static Semester toEntity(CreateSemesterRequest createSemesterRequest) {
    logger.debug("Mapping CreateSemesterRequest to Semester entity: {}", createSemesterRequest);
    Semester semester = new Semester();
    semester.setName(createSemesterRequest.name());
    semester.setYear(createSemesterRequest.year());
    semester.setOrderInYear(Enum.valueOf(OrderInYear.class, createSemesterRequest.orderInYear()));
    semester.setStartDate(createSemesterRequest.startDate().orElse(null));
    semester.setEndDate(createSemesterRequest.endDate().orElse(null));
    return semester;
  }
}
