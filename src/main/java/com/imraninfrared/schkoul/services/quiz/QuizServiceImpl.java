package com.imraninfrared.schkoul.services.quiz;

import com.imraninfrared.schkoul.domain.dto.QuizRequestDTO;
import com.imraninfrared.schkoul.domain.models.Course;
import com.imraninfrared.schkoul.domain.models.Quiz;
import com.imraninfrared.schkoul.repository.CourseRepository;
import com.imraninfrared.schkoul.repository.QuizRepository;
import com.imraninfrared.schkoul.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Quiz> getQuizzes(String username) {
        return quizRepository.findAllQuizzes(username);
    }

    @Override
    public Quiz createQuiz(QuizRequestDTO quizRequestDTO, String username) {
        Course course = courseRepository.findById(quizRequestDTO.getCourseId()).orElse(null);
        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequestDTO.getTitle());
        quiz.setDate(quizRequestDTO.getDate());
        quiz.setSyllabus(quizRequestDTO.getSyllabus());
        quiz.setTotalMarks(quizRequestDTO.getTotalMarks());
        quiz.setCourse(course);
        quiz.setCreatedBy(userRepository.findByUsername(username).orElse(null));
        return quizRepository.save(quiz);
    }

    @Override
    public boolean updateQuiz(Quiz quiz) {
        Optional<Quiz> _quiz = quizRepository.findById(quiz.getId());
        if (_quiz.isPresent()) {
            quizRepository.save(quiz);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteQuiz(Long id) {
        Optional<Quiz> _quiz = quizRepository.findById(id);
        if (_quiz.isPresent()) {
            quizRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
