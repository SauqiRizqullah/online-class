package com.personal.onlineclass.dto.request;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequest {

    @NotBlank(message = "Name can't be null!!!")
    private String teacherName;

    @NotBlank(message = "Email must be provided!!!")
    @Email(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email format is invalid"
    )
    private String email;

    @NotBlank(message = "Contact number must be provided!!!")
    @Pattern(regexp = "^08\\d{9,11}$", message = "Mobile phone number's pattern must be started by 08 and has 9 until 12 digits")
    private String contactNumber;

    @NotBlank(message = "Field can't be null!!!")
    @Enumerated(EnumType.STRING)
    private String field;
}
