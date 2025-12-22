package com.nextjob.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CompanyDto(
        Integer id,

        @Size(min = 2, max = 100)
        String name,
        @Size(min = 8, max = 500)
        String description,

        @Email(message = "Email must be valid")
        String email,

        String website,
        Integer industryId,

        @Size(max = 50)
        String address,

        @Size(max = 50)
        String city,

        @Size(max = 50)
        String country
) {
}