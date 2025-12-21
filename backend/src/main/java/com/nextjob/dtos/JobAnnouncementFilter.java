package com.nextjob.dtos;

import java.util.List;

public record JobAnnouncementFilter(
        String title,
        List<Integer> industryIds,
        List<Integer> contractTypeIds,
        List<Integer> workloadTypeIds,
        List<Integer> workModeIds,
        List<Integer> paymentTypeIds,
        Long salaryMin,
        Long salaryMax,
        String city,
        String country,
        Boolean isCvRequired,
        Boolean isCompanyAnnouncement
) {}