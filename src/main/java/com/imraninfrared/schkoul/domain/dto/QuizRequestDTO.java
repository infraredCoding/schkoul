package com.imraninfrared.schkoul.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class QuizRequestDTO {
    private String title;
    private String syllabus;
    private Date date;
    private float marksAchieved;
    private float totalMarks;
    private Long courseId;
}
