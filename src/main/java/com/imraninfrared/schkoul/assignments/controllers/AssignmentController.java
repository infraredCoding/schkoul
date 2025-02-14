package com.imraninfrared.schkoul.assignments.controllers;

import com.imraninfrared.schkoul.assignments.models.Assignments;
import com.imraninfrared.schkoul.assignments.dto.AssignmentRequestDTO;
import com.imraninfrared.schkoul.assignments.services.AssignmentService;
import com.imraninfrared.schkoul.course.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/assignments")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<List<Assignments>> getUnfinishedAssignments() {
        return new ResponseEntity<>(assignmentService.getUnfinishedAssignments(), HttpStatus.OK);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<List<Assignments>> getAssignmentsOfCourse(@PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(assignmentService.getAssignmentsOfCourse(courseId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Assignments> addAssignment(@RequestBody AssignmentRequestDTO assignmentDTO) {
        return new ResponseEntity<>(assignmentService.createAssignment(assignmentDTO), HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Assignments> updateAssignment(@PathVariable("id") Long id, @RequestBody Assignments assignment) {
        boolean res = assignmentService.updateAssignment(assignment);
        if (res) {
            return new ResponseEntity<>(assignment, HttpStatus.OK);
        }
        return new ResponseEntity<>(assignment, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Assignments> deleteAssignment(@PathVariable("id") Long id) {
        boolean res = assignmentService.deleteAssignment(id);
        if (res) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("{id}")
    public ResponseEntity<String> toggleAssignmentStatus(@PathVariable("id") Long id) {
        assignmentService.toggleAssignmentStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
