package com.imraninfrared.schkoul.controllers;

import com.imraninfrared.schkoul.domain.dto.auth.LoginRequestDTO;
import com.imraninfrared.schkoul.domain.dto.auth.LoginResponseDTO;
import com.imraninfrared.schkoul.domain.dto.auth.RegisterRequestDTO;
import com.imraninfrared.schkoul.domain.models.SchkoulUser;
import com.imraninfrared.schkoul.domain.utils.JwtUtils;
import com.imraninfrared.schkoul.repository.UserRepository;
import com.imraninfrared.schkoul.services.auth.SchkoulUserDetailsService;
import com.imraninfrared.schkoul.services.common.CoreService;
import com.imraninfrared.schkoul.domain.dto.agenda.AgendaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@Service
@RequiredArgsConstructor
public class CoreController {
    private final CoreService coreService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SchkoulUserDetailsService schkoulUserDetailsService;
    private final JwtUtils jwtUtils;

    @GetMapping("dashboard")
    public ResponseEntity<List<AgendaResponseDTO>> dashboard(@AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(coreService.getAgendaOfMonth(user.getUsername()), HttpStatus.OK);
    }

    @GetMapping("agenda-of-month/{year}/{month}")
    public ResponseEntity<List<AgendaResponseDTO>> getItemsOfMonth(@AuthenticationPrincipal UserDetails user, @PathVariable("year") int year, @PathVariable("month") int month) {
        return new ResponseEntity<>(coreService.getAgendaOfSelectedMonth(user.getUsername(), year, month), HttpStatus.OK);
    }

    @PostMapping("auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already exist", HttpStatus.CONFLICT);
        }

        SchkoulUser user = new SchkoulUser();
        user.setUsername(registerRequestDTO.getUsername());
        user.setName(registerRequestDTO.getName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
            );

            String token = jwtUtils.generateToken(loginRequestDTO.getUsername());

            return new ResponseEntity<>(new LoginResponseDTO(token, loginRequestDTO.getUsername()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
