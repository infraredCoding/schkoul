package com.imraninfrared.schkoul.services.common;

import com.imraninfrared.schkoul.domain.models.Assignments;
import com.imraninfrared.schkoul.domain.models.Quiz;
import com.imraninfrared.schkoul.repository.AssignmentRepository;
import com.imraninfrared.schkoul.domain.dto.agenda.AgendaResponseDTO;
import com.imraninfrared.schkoul.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CoreServiceImpl implements CoreService {

    private final AssignmentRepository assignmentRepository;
    private final QuizRepository quizRepository;

    @Override
    public List<AgendaResponseDTO> getAgendaOfMonth(String username) {
        List<AgendaResponseDTO> agendas = new ArrayList<>();
        List<Assignments> assignments = assignmentRepository.findAssignmentsForCurrentMonth(username);
        List<Quiz> quizzes = quizRepository.findQuizzesForCurrentMonth(username);

        for (Quiz quiz : quizzes) {
            agendas.add(new AgendaResponseDTO(
               quiz.getId(), quiz.getTitle(), quiz.getDate(), "quiz", quiz.getCourse(), true
            ));
        }

        for (Assignments assignment : assignments) {
            agendas.add(new AgendaResponseDTO(
                assignment.getId(), assignment.getTitle(), assignment.getDate(),
                "assignment", assignment.getCourse(), assignment.isDone()
            ));
        }
        return agendas.stream()
                .sorted(Comparator.comparing(AgendaResponseDTO::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<AgendaResponseDTO> getAgendaOfSelectedMonth(String username, int year, int month) {
        List<AgendaResponseDTO> agendas = new ArrayList<>();
        List<Assignments> assignments = assignmentRepository.findAssignmentsForMonth(username, year, month);
        List<Quiz> quizzes = quizRepository.findQuizzesForMonth(username, year, month);

        for (Quiz quiz : quizzes) {
            agendas.add(new AgendaResponseDTO(
                    quiz.getId(), quiz.getTitle(), quiz.getDate(), "quiz", quiz.getCourse(), true
            ));
        }

        for (Assignments assignment : assignments) {
            agendas.add(new AgendaResponseDTO(
                    assignment.getId(), assignment.getTitle(), assignment.getDate(),
                    "assignment", assignment.getCourse(), assignment.isDone()
            ));
        }
        return agendas.stream()
                .sorted(Comparator.comparing(AgendaResponseDTO::getDate).reversed())
                .collect(Collectors.toList());
    }
}
