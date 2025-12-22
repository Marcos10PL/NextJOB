package com.nextjob.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record JobSeekerDto(
        @Size(min = 2, max = 100)
        String fullName,

        @Email(message = "Email must be valid")
        @Size(max = 50)
        String email,

        @Size(min = 8, max = 500)
        String message
) {
}
