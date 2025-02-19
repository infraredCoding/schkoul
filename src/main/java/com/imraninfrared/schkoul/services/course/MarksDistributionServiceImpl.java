package com.imraninfrared.schkoul.services.course;

import com.imraninfrared.schkoul.domain.models.MarksDistribution;
import com.imraninfrared.schkoul.repository.CourseRepository;
import com.imraninfrared.schkoul.repository.MarksDistributionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MarksDistributionServiceImpl implements MarksDistributionService{
    private final MarksDistributionRepository marksDistributionRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<MarksDistribution> getMarksDistribution(Long courseId) {
        return marksDistributionRepository.findByCourse_Id(courseId);
    }

    @Override
    public MarksDistribution createMarksDistribution(MarksDistribution marksDistribution, Long courseId) {
        marksDistribution.setCourse(courseRepository.findById(courseId).orElse(null));
        return marksDistributionRepository.save(marksDistribution);
    }

    @Override
    public boolean updateMarks(Long id, float marks) {
        Optional<MarksDistribution> _md = marksDistributionRepository.findById(id);
        if (_md.isPresent()) {
            MarksDistribution md = _md.get();
            md.setMarksAchieved(marks);
            marksDistributionRepository.save(md);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMarksDistribution(MarksDistribution marksDistribution) {
        Optional<MarksDistribution> _md = marksDistributionRepository.findById(marksDistribution.getId());
        if (_md.isPresent()) {
            MarksDistribution md = _md.get();
            md.setAssessmentType(marksDistribution.getAssessmentType());
            md.setWeight(marksDistribution.getWeight());
            marksDistributionRepository.save(md);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMarksDistribution(Long id) {
        Optional<MarksDistribution> _md = marksDistributionRepository.findById(id);
        if (_md.isPresent()) {
            marksDistributionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
