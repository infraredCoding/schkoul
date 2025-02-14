package com.imraninfrared.schkoul.course.repositories;

import com.imraninfrared.schkoul.course.models.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> { }
