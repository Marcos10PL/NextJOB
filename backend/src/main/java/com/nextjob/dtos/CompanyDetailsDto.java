package com.nextjob.dtos;

public record CompanyDetailsDto(
        Integer id,
        String name,
        String description,
        String email,
        String website,
        String industryName,
        String address,
        String city,
        String country
) {}