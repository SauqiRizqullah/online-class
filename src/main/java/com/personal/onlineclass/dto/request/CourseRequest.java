package com.personal.onlineclass.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequest {
    @NotBlank(message = "Course Title must be filled!!!")
    private String title;
    @NotBlank(message = "Description must be filled!!!")
    private String description;
    @NotNull(message = "Price must be filled!!!")
    private Long price;
}
