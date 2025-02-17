package com.imraninfrared.schkoul.services.common;

import com.imraninfrared.schkoul.domain.dto.agenda.AgendaResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface CoreService {
    List<AgendaResponseDTO> getAgendaOfMonth(String username);
}
