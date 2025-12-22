package com.nextjob.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @Size(min = 2, max = 100)
        String fullName,

        @Email(message = "Email must be valid")
        @Size(max = 50)
        String email,

        @Size(max = 50)
        String address,

        @Size(max = 50)
        String city,

        @Size(max = 50)
        String country
) {}