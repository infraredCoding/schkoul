package com.imraninfrared.schkoul.assignments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequestDTO {
    private String title;
    private String details;
    private Date date;
    private boolean isDone;
    private Long courseId;
}
