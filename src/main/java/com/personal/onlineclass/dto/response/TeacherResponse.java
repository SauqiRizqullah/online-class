package com.personal.onlineclass.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
public class TeacherResponse {
    private String teacherId;
    private String teacherName;
    private String email;
    private String contactNumber;
    private String field;
}
