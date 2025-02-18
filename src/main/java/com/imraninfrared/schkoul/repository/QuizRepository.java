package com.imraninfrared.schkoul.repository;

import com.imraninfrared.schkoul.domain.models.Assignments;
import com.imraninfrared.schkoul.domain.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query(
            "SELECT q from Quiz q " +
            "JOIN q.createdBy u " +
            "where u.username = :username " +
            "and YEAR (q.date) = YEAR(CURRENT_DATE) AND MONTH(q.date) = MONTH(CURRENT_DATE)"
    )
    List<Quiz> findQuizzesForCurrentMonth(String username);

    @Query(
        "SELECT q from Quiz q " +
        "JOIN q.createdBy u " +
        "where u.username = :username "
    )
    List<Quiz> findAllQuizzes(String username);
}
