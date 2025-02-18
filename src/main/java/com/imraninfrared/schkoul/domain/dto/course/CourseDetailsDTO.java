package com.imraninfrared.schkoul.domain.dto.course;

import com.imraninfrared.schkoul.domain.models.Assignments;
import com.imraninfrared.schkoul.domain.models.MarksDistribution;
import com.imraninfrared.schkoul.domain.models.Quiz;
import lombok.Data;

import java.util.List;

@Data
public class CourseDetailsDTO {
    private Long id;
    private String title;
    private String code;
    private String faculty;

    private List<Assignments> assignmentsList;

    private List<MarksDistribution> marksDistributionList;

    private List<Quiz> quizList;
}
