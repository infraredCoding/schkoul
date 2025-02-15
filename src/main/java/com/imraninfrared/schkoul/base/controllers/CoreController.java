package com.imraninfrared.schkoul.base.controllers;

import com.imraninfrared.schkoul.base.services.CoreService;
import com.imraninfrared.schkoul.domain.dto.agenda.AgendaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "http://localhost:5173")
@Service
@RequiredArgsConstructor
public class CoreController {
    private final CoreService coreService;

    @GetMapping("dashboard")
    public ResponseEntity<List<AgendaResponseDTO>> dashboard() {
        return new ResponseEntity<>(coreService.getAgendaOfMonth(), HttpStatus.OK);
    }
}
