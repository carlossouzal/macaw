package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.repository.SpecializationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecializationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpecializationService.class);
  private final SpecializationRepository repository;

  @Autowired
  public SpecializationService(SpecializationRepository repository) {
    this.repository = repository;
  }

  public List<Specialization> findAll() {
    LOGGER.debug("Retrieving all specializations from the repository");
    return repository.findAll();
  }

  public Specialization findById(Long id) {
    LOGGER.debug("Fetching a specialization from the repository");
    Optional<Specialization> specialization = repository.findById(id);
    if (specialization.isEmpty()) {
      throw new EntityNotFoundException("Specialization with id " + id + " not found");
    }

    return specialization.get();
  }
}
