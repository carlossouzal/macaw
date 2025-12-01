package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.controller.parameters.ClassroomCriteria;
import edu.maplewood.master_schedule.entity.Classroom;
import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.repository.ClassroomRepository;
import edu.maplewood.master_schedule.repository.specification.ClassroomSpecification;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomService.class);
  private final ClassroomRepository repository;
  private final RoomTypeService roomTypeService;

  @Autowired
  public ClassroomService(ClassroomRepository repository, RoomTypeService roomTypeService) {
    this.repository = repository;
    this.roomTypeService = roomTypeService;
  }

  public List<Classroom> findAll() {
    LOGGER.debug("Retrieving all classrooms from the repository");
    return repository.findAll();
  }

  public Page<Classroom> find(ClassroomCriteria criteria) {
    LOGGER.debug("Retrieving classrooms from the repository with criteria {}", criteria);
    RoomType roomType = null;
    if (criteria.roomTypeId() != null) {
      roomType = roomTypeService.findById(criteria.roomTypeId());
    }
    Specification<Classroom> specs = ClassroomSpecification.builder()
        .name(criteria.name())
        .capacityMin(criteria.capacityMin())
        .capacityMax(criteria.capacityMax())
        .equipment(criteria.equipment())
        .floor(criteria.floor())
        .available(criteria.available())
        .roomType(roomType)
        .build();

    Pageable pageable = PageRequest.of(criteria.page(), criteria.size(),
        criteria.sortDirection().equals("ASC") ?
            Sort.by(criteria.sortBy()).ascending() :
            Sort.by(criteria.sortBy()).descending()
    );

    return repository.findAll(specs, pageable);
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
