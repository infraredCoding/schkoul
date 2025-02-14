package com.imraninfrared.schkoul.assignments.repositories;

import com.imraninfrared.schkoul.assignments.models.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignments, Long> {
    List<Assignments> findByCourse_Id(Long id);
    List<Assignments> findByIsDoneFalse();
}
