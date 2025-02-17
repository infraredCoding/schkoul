package com.imraninfrared.schkoul.services.course;

import com.imraninfrared.schkoul.domain.models.Course;
import com.imraninfrared.schkoul.repository.CourseRepository;
import com.imraninfrared.schkoul.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<Course> getCourses(String username) {
        return courseRepository.findCourses(username);
    }

    @Override
    public Course addCourse(Course course, String username) {
        course.setCreatedBy(userRepository.findByUsername(username).orElse(null));
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
