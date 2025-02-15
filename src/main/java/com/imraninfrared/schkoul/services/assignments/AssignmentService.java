package com.imraninfrared.schkoul.services.assignments;

import com.imraninfrared.schkoul.domain.dto.assignment.AssignmentRequestDTO;
import com.imraninfrared.schkoul.domain.models.Assignments;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface AssignmentService {
    List<Assignments> getUnfinishedAssignments();
    List<Assignments> getAssignmentsOfCourse(Long courseId);
    Assignments createAssignment(AssignmentRequestDTO assignmentDTO);
    boolean updateAssignment(Assignments assignment);
    boolean deleteAssignment(Long id);
    void toggleAssignmentStatus(Long id);
}
