package com.imraninfrared.schkoul.domain.dto.course;

import lombok.Data;


@Data
public class CourseListItemDTO {
    private Long id;
    private String title;
    private String code;
    private String faculty;
}
