package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.SpecializationResponse;
import edu.maplewood.master_schedule.controller.dto.response.TeacherResponse;
import edu.maplewood.master_schedule.entity.Teacher;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class TeacherMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherMapper.class);

  public static TeacherResponse toResponse(Teacher teacher) {
    LOGGER.debug("Converting Teacher to DTO");
    SpecializationResponse specialization = null;
    if (teacher.getSpecialization() != null) {
      specialization = SpecializationMapper.toResponse(teacher.getSpecialization());
    }

    return new TeacherResponse(
        teacher.getId(),
        teacher.getFirstName(),
        teacher.getLastName(),
        teacher.getEmail(),
        teacher.getMaxDailyHours(),
        teacher.getCreatedAt(),
        specialization
    );
  }

  public static List<TeacherResponse> toResponse(List<Teacher> teachers) {
    LOGGER.debug("Converting Teacher List to DTO");
    return teachers.stream().map(TeacherMapper::toResponse).toList();
  }

  public static ResponseList<TeacherResponse> toResponse(Page<Teacher> teacherPage) {
    LOGGER.debug(
        "Mapping Page of Teachers entities to ResponseList<TeacherResponse>. Total teachers: {}",
        teacherPage.getTotalElements());
    List<Teacher> teacherList = teacherPage.getContent();
    List<TeacherResponse> teacherResponses = toResponse(teacherList);

    return new ResponseList<>(teacherPage.getTotalElements(),
        teacherPage.getNumber(), teacherPage.getSize(), teacherResponses);
  }
}
