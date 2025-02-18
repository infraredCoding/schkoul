package com.imraninfrared.schkoul.services.course;

import com.imraninfrared.schkoul.domain.dto.course.CourseDetailsDTO;
import com.imraninfrared.schkoul.domain.dto.course.CourseListItemDTO;
import com.imraninfrared.schkoul.domain.models.Course;
import com.imraninfrared.schkoul.repository.CourseRepository;
import com.imraninfrared.schkoul.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CourseListItemDTO> getCourses(String username) {
        List<Course> courses = courseRepository.findCourses(username);
        return courses.stream().map(
                course -> modelMapper.map(course, CourseListItemDTO.class
        )).toList();
    }

    @Override
    public Optional<CourseDetailsDTO> getCourse(Long id){
        Optional<Course> course = courseRepository.findById(id);
        return Optional.ofNullable(modelMapper.map(course, CourseDetailsDTO.class));
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
