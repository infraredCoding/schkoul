package com.imraninfrared.schkoul.controllers;

import com.imraninfrared.schkoul.domain.models.Assignments;
import com.imraninfrared.schkoul.domain.dto.assignment.AssignmentRequestDTO;
import com.imraninfrared.schkoul.services.assignments.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/assignments")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<List<Assignments>> getUnfinishedAssignments(@AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(assignmentService.getUnfinishedAssignments(user.getUsername()), HttpStatus.OK);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<List<Assignments>> getAssignmentsOfCourse(@PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(assignmentService.getAssignmentsOfCourse(courseId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Assignments> addAssignment(@RequestBody AssignmentRequestDTO assignmentDTO, @AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(assignmentService.createAssignment(assignmentDTO, user.getUsername()), HttpStatus.CREATED);
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
    public ResponseEntity<?> deleteAssignment(@PathVariable("id") Long id) {
        boolean res = assignmentService.deleteAssignment(id);
        if (res) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("{id}")
    public ResponseEntity<?> toggleAssignmentStatus(@PathVariable("id") Long id) {
        assignmentService.toggleAssignmentStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
