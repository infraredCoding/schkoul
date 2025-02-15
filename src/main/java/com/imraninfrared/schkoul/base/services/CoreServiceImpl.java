package com.imraninfrared.schkoul.base.services;

import com.imraninfrared.schkoul.assignments.models.Assignments;
import com.imraninfrared.schkoul.assignments.repositories.AssignmentRepository;
import com.imraninfrared.schkoul.domain.dto.agenda.AgendaResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CoreServiceImpl implements CoreService{

    private final AssignmentRepository assignmentRepository;

    @Override
    public List<AgendaResponseDTO> getAgendaOfMonth() {
        List<Assignments> assignments = assignmentRepository.findAssignmentsForCurrentMonth();
        return assignments.stream()
            .map(assignment -> new AgendaResponseDTO(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDate(),
                "assignment",
                assignment.getCourse(),
                assignment.isDone()
            )).collect(Collectors.toList());
    }
}
