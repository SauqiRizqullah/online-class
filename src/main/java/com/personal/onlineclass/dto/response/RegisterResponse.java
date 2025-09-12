package com.personal.onlineclass.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RegisterResponse {
    private String teacherId;
    private String email;
    private String teacherName;
    private String username;
    private String field;
    private List<String> roles;
}
