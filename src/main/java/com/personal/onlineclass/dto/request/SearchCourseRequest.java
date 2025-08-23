package com.personal.onlineclass.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCourseRequest {
    private Integer page;
    private Integer size;

    private String sortBy;
    private String direction;

    private String title;
}
