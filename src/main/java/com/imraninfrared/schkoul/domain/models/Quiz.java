package com.imraninfrared.schkoul.domain.models;

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
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String syllabus;
    private Date date;

    private float marksAchieved;
    private float totalMarks;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private SchkoulUser createdBy;

    @Override
    public String toString() {
        return "Assignments{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
