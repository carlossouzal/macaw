package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.controller.parameters.SemesterCriteria;
import edu.maplewood.master_schedule.entity.Semester;
import edu.maplewood.master_schedule.entity.Semester.OrderInYear;
import edu.maplewood.master_schedule.repository.SemesterRepository;
import edu.maplewood.master_schedule.repository.specification.SemesterSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SemesterService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SemesterService.class);
  private final SemesterRepository repository;

  @Autowired
  public SemesterService(SemesterRepository repository) {
    this.repository = repository;
  }

  public List<Semester> findAll() {
    LOGGER.debug("Retrieving all semesters from the repository");
    return repository.findAll();
  }

  public Page<Semester> find(SemesterCriteria criteria) {
    LOGGER.debug("Finding semesters with criteria: {}", criteria);
    OrderInYear order = null;
    if (criteria.orderInYear() != null) {
      order = Enum.valueOf(OrderInYear.class, criteria.orderInYear());
    }
    Specification<Semester> specification = SemesterSpecification.build(
        criteria.name(),
        criteria.yearMin(),
        criteria.yearMax(),
        order,
        criteria.startDateBegin(),
        criteria.startDateEnd(),
        criteria.endDateBegin(),
        criteria.endDateEnd(),
        criteria.isActive(),
        criteria.createdAtBegin(),
        criteria.createdAtEnd()
    );

    Pageable pageable = PageRequest.of(criteria.page(), criteria.size(),
        criteria.sortDirection().equals("ASC") ?
            org.springframework.data.domain.Sort.by(criteria.sortBy()).ascending() :
            org.springframework.data.domain.Sort.by(criteria.sortBy()).descending()
    );
    return repository.findAll(specification, pageable);
  }

  public Semester findById(Long id) throws EntityNotFoundException {
    LOGGER.debug("Finding semester by ID: {}", id);
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Semester not found with ID: " + id));
  }

  public Semester getActiveSemester() throws EntityNotFoundException {
    LOGGER.debug("Retrieving the active semester");
    Specification<Semester> specification = SemesterSpecification.build(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        true,
        null,
        null
    );

    return repository.findOne(specification)
        .orElseThrow(() -> new EntityNotFoundException("No active semester found."));
  }

  @Transactional
  public void create(Semester semester) {
    LOGGER.debug("Creating a new semester: {}", semester);

    //repository.save(semester);
  }
}
