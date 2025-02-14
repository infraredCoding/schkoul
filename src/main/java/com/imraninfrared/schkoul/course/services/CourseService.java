package com.imraninfrared.schkoul.course.services;

import com.imraninfrared.schkoul.course.models.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourses();
    Course addCourse(Course course);
    boolean updateCourse(Course course);
    boolean deleteCourse(Long id);
}
