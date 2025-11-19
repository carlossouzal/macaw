package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.RoomType;
import edu.maplewood.master_schedule.repository.RoomTypeRepository;
import java.util.List;
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
}
