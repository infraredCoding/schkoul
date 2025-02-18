package com.imraninfrared.schkoul.services.quiz;

import com.imraninfrared.schkoul.domain.dto.QuizRequestDTO;
import com.imraninfrared.schkoul.domain.models.Quiz;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface QuizService {
    List<Quiz> getQuizzes(String username);
    Quiz createQuiz(QuizRequestDTO quiz, String username);
    boolean updateQuiz(Quiz quiz);
    boolean deleteQuiz(Long id);
}
