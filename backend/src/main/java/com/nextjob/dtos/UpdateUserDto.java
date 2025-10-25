package com.nextjob.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @Size(min = 2, message = "Full name must be at least 2 characters")
        String fullName,

        @Email(message = "Email must be valid")
        String email,

        String address,
        String city,
        String country
) {}