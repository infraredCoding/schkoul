package com.imraninfrared.schkoul.assignments.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imraninfrared.schkoul.course.models.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Assignments {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String details;
    private Date date;
    private boolean isDone = false;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;
}
