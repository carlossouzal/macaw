package edu.maplewood.master_schedule.repository;

import edu.maplewood.master_schedule.entity.Course;
import edu.maplewood.master_schedule.entity.Semester;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  List<Course> findBySemesterOrder(Semester.OrderInYear order);
}
