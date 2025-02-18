package com.imraninfrared.schkoul.services.course;

import com.imraninfrared.schkoul.domain.dto.course.CourseDetailsDTO;
import com.imraninfrared.schkoul.domain.dto.course.CourseListItemDTO;
import com.imraninfrared.schkoul.domain.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseListItemDTO> getCourses(String username);
    Optional<CourseDetailsDTO> getCourse(Long id);
    Course addCourse(Course course, String username);
    boolean updateCourse(Course course);
    boolean deleteCourse(Long id);
}
