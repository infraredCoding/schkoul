package com.imraninfrared.schkoul.domain.dto.agenda;

import com.imraninfrared.schkoul.course.models.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaResponseDTO {
    public Long id;
    public String title;
    public Date date;
    public String type;
    public Course course;
    public boolean isCompleted;
}
