package com.imraninfrared.schkoul.controllers;

import com.imraninfrared.schkoul.domain.dto.QuizRequestDTO;
import com.imraninfrared.schkoul.domain.models.Assignments;
import com.imraninfrared.schkoul.domain.models.Quiz;
import com.imraninfrared.schkoul.services.quiz.QuizService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuiz(@AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>(quizService.getQuizzes(user.getUsername()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@AuthenticationPrincipal UserDetails user, @RequestBody QuizRequestDTO quiz) {
        return new ResponseEntity<>(quizService.createQuiz(quiz, user.getUsername()), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Quiz> updateAssignment(@PathVariable("id") Long id, @RequestBody Quiz quiz) {
        boolean res = quizService.updateQuiz(quiz);
        if (res) {
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        }
        return new ResponseEntity<>(quiz, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable("id") Long id) {
        boolean res = quizService.deleteQuiz(id);
        if (res) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
