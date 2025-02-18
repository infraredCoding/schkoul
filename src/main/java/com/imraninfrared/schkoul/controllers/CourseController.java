package com.imraninfrared.schkoul.controllers;

import com.imraninfrared.schkoul.domain.dto.course.CourseDetailsDTO;
import com.imraninfrared.schkoul.domain.dto.course.CourseListItemDTO;
import com.imraninfrared.schkoul.domain.models.Course;
import com.imraninfrared.schkoul.services.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/course")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseListItemDTO>> getCourses(@AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(courseService.getCourses(user.getUsername()), HttpStatus.OK);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseDetailsDTO> getCourseById(@PathVariable("courseId") Long courseId) {
        Optional<CourseDetailsDTO> course = courseService.getCourse(courseId);
        if (course.isPresent()) {
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course, @AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(courseService.addCourse(course, user.getUsername()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable int id) {
        boolean res = courseService.updateCourse(course);
        if (res) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id) {
        boolean res = courseService.deleteCourse(id);
        if (res) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
