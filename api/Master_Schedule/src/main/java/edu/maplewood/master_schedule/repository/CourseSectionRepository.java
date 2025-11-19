package edu.maplewood.master_schedule.repository;

import edu.maplewood.master_schedule.entity.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSectionRepository extends JpaRepository<CourseSection, Long>,
    JpaSpecificationExecutor<CourseSection> {

}
