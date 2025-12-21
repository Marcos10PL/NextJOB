package com.nextjob.dtos;

import java.time.LocalDateTime;

public record JobAnnouncementListResponse(
        Integer id,
        String title,
        String city,
        String country,
        Long salaryMin,
        Long salaryMax,
        Integer authorId,
        String authorName,
        Integer companyId,
        String companyName,
        String industryName,
        String contractType,
        String workloadType,
        String workMode,
        String paymentType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}