package com.nextjob.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CompanyDto(
        @Size(min = 2, message = "Name must be at least 2 characters")
        String name,
        @Size(min = 8, message = "Description name must be at least 8 characters")
        String description,

        @Email(message = "Email must be valid")
        String email,

        String website,
        Integer industryId,

        String address,
        String city,
        String country
) {}