package com.imraninfrared.schkoul.course.services;

import com.imraninfrared.schkoul.course.models.Course;
import com.imraninfrared.schkoul.course.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public boolean updateCourse(Course course) {
        Optional<Course> _course = courseRepository.findById(course.getId());
        if(_course.isPresent()) {
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCourse(Long id) {
        Optional<Course> _course = courseRepository.findById(id);
        if(_course.isPresent()) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
