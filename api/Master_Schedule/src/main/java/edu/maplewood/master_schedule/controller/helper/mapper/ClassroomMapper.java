package edu.maplewood.master_schedule.controller.helper.mapper;

import edu.maplewood.master_schedule.controller.dto.response.ClassroomResponse;
import edu.maplewood.master_schedule.controller.dto.response.ResponseList;
import edu.maplewood.master_schedule.controller.dto.response.RoomTypeResponse;
import edu.maplewood.master_schedule.entity.Classroom;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class ClassroomMapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomMapper.class);

  public static ClassroomResponse toResponse(Classroom classroom) {
    LOGGER.debug("Converting Classroom to DTO");
    RoomTypeResponse roomTypeResponse = null;
    if (classroom.getRoomType() != null) {
      roomTypeResponse = RoomTypeMapper.toResponse(classroom.getRoomType());
    }

    return new ClassroomResponse(
        classroom.getId(),
        classroom.getName(),
        classroom.getCapacity(),
        classroom.getEquipment(),
        classroom.getFloor(),
        classroom.getCreatedAt(),
        roomTypeResponse
    );
  }

  public static List<ClassroomResponse> toResponse(List<Classroom> classrooms) {
    LOGGER.debug("Converting Classroom List to DTO");
    return classrooms.stream().map(ClassroomMapper::toResponse).toList();
  }

  public static ResponseList<ClassroomResponse> toResponse(Page<Classroom> classrooms) {
    LOGGER.debug(
        "Mapping Page of Teachers entities to ResponseList<TeacherResponse>. Total teachers: {}",
        classrooms.getTotalElements());
    List<Classroom> classroomList = classrooms.getContent();
    List<ClassroomResponse> classroomResponses = toResponse(classroomList);

    return new ResponseList<>(classrooms.getTotalElements(),
        classrooms.getNumber(), classrooms.getSize(), classroomResponses);
  }

}
