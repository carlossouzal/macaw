package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.repository.ClassroomRepository;
import edu.maplewood.master_schedule.repository.specification.ClassroomSpecification;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomService.class);
  private final ClassroomRepository repository;

  @Autowired
  public ClassroomService(ClassroomRepository repository) {
    this.repository = repository;
  }

  public List<Classroom> findAll() {
    LOGGER.debug("Retrieving all classrooms from the repository");
    return repository.findAll();
  }

  public List<Classroom> nextAvailableFor(RoomType roomType) {
    LOGGER.debug("Finding the next available classroom");
    Specification<Classroom> specs = ClassroomSpecification.builder()
        .roomType(roomType)
        .available(true)
        .build();

    return repository.findAll(specs);
  }
}
