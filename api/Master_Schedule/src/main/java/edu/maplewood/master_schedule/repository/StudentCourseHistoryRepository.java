package edu.maplewood.master_schedule.repository;

import edu.maplewood.master_schedule.entity.StudentCourseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseHistoryRepository extends JpaRepository<StudentCourseHistory, Long> {

}
