package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.repository.RoomTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RoomTypeService.class);
  private RoomTypeRepository repository;

  @Autowired
  public RoomTypeService(RoomTypeRepository repository) {
    this.repository = repository;
  }

  public List<RoomType> findAll() {
    LOGGER.debug("Retrieving all room types from the repository");
    return repository.findAll();
  }

  public RoomType findById(Long id) {
    LOGGER.debug("Retrieving roomType with id {}", id);
    Optional<RoomType> roomType = repository.findById(id);
    if (roomType.isEmpty()) {
      throw new EntityNotFoundException("Classroom with id " + id + " not found");
    }
    return roomType.get();
  }
}
