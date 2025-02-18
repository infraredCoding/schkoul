package com.imraninfrared.schkoul.services.course;

import com.imraninfrared.schkoul.domain.models.MarksDistribution;
import com.imraninfrared.schkoul.repository.MarksDistributionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MarksDistributionServiceImpl implements MarksDistributionService{
    private final MarksDistributionRepository marksDistributionRepository;

    @Override
    public List<MarksDistribution> getMarksDistribution(Long courseId) {
        return marksDistributionRepository.findByCourse_Id(courseId);
    }

    @Override
    public MarksDistribution createMarksDistribution(MarksDistribution marksDistribution) {
        return null;
    }

    @Override
    public boolean updateMarksDistribution(MarksDistribution marksDistribution) {
        return false;
    }

    @Override
    public boolean deleteMarksDistribution(Long id) {
        return false;
    }
}
