package com.imraninfrared.schkoul.repository;

import com.imraninfrared.schkoul.domain.models.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignments, Long> {
    List<Assignments> findByCourse_Id(Long id);

    @Query(
        "select a from Assignments a " +
        "Join a.createdBy u where u.username = :username " +
        "and a.isDone = false"
    )
    List<Assignments> findByIsDoneFalse(String username);

    @Query(
        "SELECT a from Assignments a " +
        "JOIN a.createdBy u " +
        "where u.username = :username " +
        "and YEAR (a.date) = YEAR(CURRENT_DATE) AND MONTH(a.date) = MONTH(CURRENT_DATE)"
    )
    List<Assignments> findAssignmentsForCurrentMonth(String username);
}
