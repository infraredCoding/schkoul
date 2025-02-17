package com.imraninfrared.schkoul.services.course;

import com.imraninfrared.schkoul.domain.models.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourses(String username);
    Course addCourse(Course course, String username);
    boolean updateCourse(Course course);
    boolean deleteCourse(Long id);
}
