package edu.maplewood.master_schedule.repository;

import edu.maplewood.master_schedule.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>,
    JpaSpecificationExecutor<Semester> {

}
