package com.personal.onlineclass.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CourseResponse {
    private String courseId;
    private TeacherResponse teacherResponse;
    private String title;
    private String description;
    private Long price;
}
