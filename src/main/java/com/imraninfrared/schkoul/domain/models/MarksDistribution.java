package com.imraninfrared.schkoul.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imraninfrared.schkoul.domain.enums.AssessmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class MarksDistribution {
    @Id
    @GeneratedValue
    private Long id;
    private AssessmentType assessmentType;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=true)
    @JsonIgnore
    private Course course;

    private float weight;

    private float marksAchieved;

    @Override
    public String toString() {
        return "Assignments{" +
                "id=" + id +
                ", title='" + assessmentType + '\'' +
                '}';
    }
}
