package com.imraninfrared.schkoul.base.services;

import com.imraninfrared.schkoul.assignments.repositories.AssignmentRepository;
import com.imraninfrared.schkoul.assignments.services.AssignmentService;
import com.imraninfrared.schkoul.domain.dto.agenda.AgendaResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface CoreService {
    List<AgendaResponseDTO> getAgendaOfMonth();
}
