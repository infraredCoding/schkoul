package com.imraninfrared.schkoul.services.assignments;

import com.imraninfrared.schkoul.domain.dto.assignment.AssignmentRequestDTO;
import com.imraninfrared.schkoul.domain.models.Assignments;
import com.imraninfrared.schkoul.repository.AssignmentRepository;
import com.imraninfrared.schkoul.domain.models.Course;
import com.imraninfrared.schkoul.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Assignments> getUnfinishedAssignments() {
        return assignmentRepository.findByIsDoneFalse();
    }

    @Override
    public List<Assignments> getAssignmentsOfCourse(Long courseId) {
        return assignmentRepository.findByCourse_Id(courseId);
    }

    @Override
    public Assignments createAssignment(AssignmentRequestDTO assignmentDTO) {
        Course course = courseRepository.findById(assignmentDTO.getCourseId()).orElse(null);
        Assignments assignment = new Assignments();
        assignment.setTitle(assignmentDTO.getTitle());
        assignment.setDetails(assignmentDTO.getDetails());
        assignment.setCourse(course);
        assignment.setDate(assignmentDTO.getDate());
        return assignmentRepository.save(assignment);
    }

    @Override
    public boolean updateAssignment(Assignments assignment) {
        Optional<Assignments> _assignment = assignmentRepository.findById(assignment.getId());
        if(_assignment.isPresent()) {
            assignmentRepository.save(assignment);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAssignment(Long id) {
        Optional<Assignments> _assignment = assignmentRepository.findById(id);
        if(_assignment.isPresent()) {
            assignmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void toggleAssignmentStatus(Long id) {
        Optional<Assignments> _assignment = assignmentRepository.findById(id);
        if (_assignment.isPresent()) {
            Assignments assignment = _assignment.get();
            assignment.setDone(!assignment.isDone());
            assignmentRepository.save(assignment);
        }
    }
}
