package edu.maplewood.master_schedule.service;

import edu.maplewood.master_schedule.controller.parameters.TeacherCriteria;
import edu.maplewood.master_schedule.entity.Specialization;
import edu.maplewood.master_schedule.entity.Teacher;
import edu.maplewood.master_schedule.repository.TeacherRepository;
import edu.maplewood.master_schedule.repository.specification.TeacherSpecification;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TeacherService.class);
  private final SpecializationService specializationService;
  private final TeacherRepository repository;

  @Autowired
  public TeacherService(TeacherRepository repository, SpecializationService specializationService) {
    this.repository = repository;
    this.specializationService = specializationService;
  }

  public List<Teacher> findAll() {
    LOGGER.debug("Retrieving all teachers from the repository");
    return repository.findAll();
  }

  public Page<Teacher> find(TeacherCriteria criteria) {
    LOGGER.debug("Fetching all teachers from the repository");
    Specialization specialization = null;
    if (criteria.specializationId() != null) {
      specialization = specializationService.findById(criteria.specializationId());
    }
    
    Specification<Teacher> specs = TeacherSpecification.builder()
        .name(criteria.name())
        .email(criteria.email())
        .maxDailyHoursBegin(criteria.maxDailyHoursBegin())
        .maxDailyHoursEnd(criteria.maxDailyHoursEnd())
        .createdAtBegin(criteria.createdAtBegin())
        .createdAtEnd(criteria.createdAtEnd())
        .specialization(specialization)
        .build();

    Pageable pageable = PageRequest.of(criteria.page(), criteria.size(),
        criteria.sortDirection().equals("ASC") ?
            Sort.by(criteria.sortBy()).ascending() :
            Sort.by(criteria.sortBy()).descending()
    );

    return repository.findAll(specs, pageable);
  }

  @Transactional(readOnly = true)
  public List<Teacher> nextsAvailableFor(Specialization specialization) {
    LOGGER.debug("Finding next available teacher for specialization: {}", specialization.getName());
    Specification<Teacher> specs = TeacherSpecification.builder()
        .specialization(specialization)
        .available(true)
        .build()
        .and((root, query, cb) -> {
          var csJoin = root.join("courseSections", JoinType.LEFT);
          query.groupBy(root.get("id"));
          query.orderBy(cb.asc(cb.count(csJoin)));
          return cb.conjunction();
        });
    ;

    List<Teacher> teachers = repository.findAll(specs);
    if (teachers.isEmpty()) {
      LOGGER.warn("No available teacher found for specialization: {}", specialization.getName());
      throw new NoSuchElementException("No available teacher found for specialization");
    }
    return teachers;
  }
}
