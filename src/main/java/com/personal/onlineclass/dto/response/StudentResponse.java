package com.personal.onlineclass.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class StudentResponse {
    private String studendId;

    private String studentName;

    private String email;

    private String background;

    private String img;
}
