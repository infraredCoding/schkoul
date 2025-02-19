package com.imraninfrared.schkoul.services.course;

import com.imraninfrared.schkoul.domain.models.MarksDistribution;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface MarksDistributionService {
    List<MarksDistribution> getMarksDistribution(Long courseId);
    MarksDistribution createMarksDistribution(MarksDistribution marksDistribution, Long courseId);
    boolean updateMarks(Long id, float marks);
    boolean updateMarksDistribution(MarksDistribution marksDistribution);
    boolean deleteMarksDistribution(Long id);
}
