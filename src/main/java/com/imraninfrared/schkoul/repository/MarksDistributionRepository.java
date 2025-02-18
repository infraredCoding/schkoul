package com.imraninfrared.schkoul.repository;

import com.imraninfrared.schkoul.domain.models.MarksDistribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksDistributionRepository extends JpaRepository<MarksDistribution, Long> {
    List<MarksDistribution> findByCourse_Id(Long id);
}
