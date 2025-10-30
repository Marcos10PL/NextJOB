package com.nextjob.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record JobSeekerDto(
        @Size(min = 2, message = "Full name must be at least 2 characters")
        String fullName,

        @Email(message = "Email must be valid")
        String email,

        @Size(min = 8, message = "Message name must be at least 10 characters")
        String message
) {
}
