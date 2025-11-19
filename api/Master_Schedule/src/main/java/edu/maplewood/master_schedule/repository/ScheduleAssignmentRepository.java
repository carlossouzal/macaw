package edu.maplewood.master_schedule.repository;

import edu.maplewood.master_schedule.entity.ScheduleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleAssignmentRepository extends JpaRepository<ScheduleAssignment, Long> {

}
