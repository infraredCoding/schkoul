package com.imraninfrared.schkoul.controllers;

import com.imraninfrared.schkoul.domain.dto.course.AchievedMarksRequestDTO;
import com.imraninfrared.schkoul.domain.dto.course.CourseDetailsDTO;
import com.imraninfrared.schkoul.domain.dto.course.CourseListItemDTO;
import com.imraninfrared.schkoul.domain.models.Course;
import com.imraninfrared.schkoul.domain.models.MarksDistribution;
import com.imraninfrared.schkoul.services.course.CourseService;
import com.imraninfrared.schkoul.services.course.MarksDistributionService;
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
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final MarksDistributionService marksDistributionService;

    @GetMapping
    public ResponseEntity<List<CourseListItemDTO>> getCourses(@AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(courseService.getCourses(user.getUsername()), HttpStatus.OK);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseDetailsDTO> getCourseById(@PathVariable("courseId") Long courseId) {
        Optional<CourseDetailsDTO> course = courseService.getCourse(courseId);
        if (course.isPresent()) {
            System.out.println(course);
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

    @PostMapping("{courseId}/marks-distribution")
    public ResponseEntity<MarksDistribution> addMarksDistribution(@RequestBody MarksDistribution marksDistribution, @PathVariable Long courseId) {
        return new ResponseEntity<>(marksDistributionService.createMarksDistribution(marksDistribution, courseId), HttpStatus.CREATED);
    }

    @PatchMapping("marks-distribution/{id}")
    public ResponseEntity<MarksDistribution> updateMarksDistribution(@RequestBody MarksDistribution marksDistribution, @PathVariable Long id) {
        boolean res = marksDistributionService.updateMarksDistribution(marksDistribution);
        if (res) {
            return new ResponseEntity<>(marksDistribution, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("update-marks/{id}")
    public ResponseEntity<?> updateMarks(@RequestBody AchievedMarksRequestDTO requestDTO, @PathVariable Long id) {
        boolean res = marksDistributionService.updateMarks(id, requestDTO.getMarksAchieved());
        if (res) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("marks-distribution/{id}")
    public ResponseEntity<MarksDistribution> deleteMarksDistribution(@PathVariable Long id) {
        boolean res = marksDistributionService.deleteMarksDistribution(id);
        if (res) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
