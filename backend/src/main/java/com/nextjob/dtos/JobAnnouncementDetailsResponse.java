package com.nextjob.dtos;

import java.time.LocalDateTime;

public record JobAnnouncementDetailsResponse(
        Integer id,
        String title,
        String description,

        Long salaryMin,
        Long salaryMax,
        Boolean isCvRequired,

        String city,
        String country,
        String address,

        LocalDateTime updatedAt,

        String industryName,
        String contractTypeName,
        String workloadTypeName,
        String workModeName,
        String paymentTypeName,

        CompanyDetailsDto company,
        AuthorDto author
) {}
