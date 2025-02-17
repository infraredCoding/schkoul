package com.imraninfrared.schkoul.repository;

import com.imraninfrared.schkoul.domain.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(
        "select c from Course c " +
        "Join c.createdBy u where u.username = :username"
    )
    List<Course> findCourses(String username);
}
