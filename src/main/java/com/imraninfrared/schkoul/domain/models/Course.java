package com.imraninfrared.schkoul.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String code;
    private String faculty;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Assignments> assignmentsList;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Quiz> quizList;

    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private SchkoulUser createdBy;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<MarksDistribution> marksDistributionList;
}
