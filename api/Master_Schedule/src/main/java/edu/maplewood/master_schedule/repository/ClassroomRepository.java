package edu.maplewood.master_schedule.repository;

import edu.maplewood.master_schedule.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long>,
    JpaSpecificationExecutor<Classroom> {

}
