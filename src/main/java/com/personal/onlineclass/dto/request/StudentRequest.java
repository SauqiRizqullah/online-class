package com.personal.onlineclass.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequest {
    @NotBlank(message = "Name can't be null!!!")
    private String studentName;

    @NotBlank(message = "Email must be provided!!!")
    @Email(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email format is invalid"
    )
    private String email;

    @NotBlank(message = "Background profile must be filled")
    private String background;

    private String img;
}
