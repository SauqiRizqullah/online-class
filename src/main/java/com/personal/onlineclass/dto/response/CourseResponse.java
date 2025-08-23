package com.personal.onlineclass.dto.response;

import com.personal.onlineclass.entity.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CourseResponse {
    private String courseId;
    private Teacher teacher;
    private String title;
    private String description;
    private Long price;
}
