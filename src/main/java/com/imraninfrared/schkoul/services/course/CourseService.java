package com.imraninfrared.schkoul.services.course;

import com.imraninfrared.schkoul.domain.models.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourses();
    Course addCourse(Course course);
    boolean updateCourse(Course course);
    boolean deleteCourse(Long id);
}
