package com.nextjob.dtos;

import jakarta.validation.constraints.*;

public record JobAnnouncementCreateRequest(

        @NotNull Integer industryId,
        @NotNull Integer contractTypeId,
        @NotNull Integer workloadTypeId,
        @NotNull Integer workModeId,
        @NotNull Integer paymentTypeId,

        Integer companyId,

        @Size(min = 2, max = 100)
        String title,

        @Size(min = 8, max = 1500)
        String description,

        @NotNull Long salaryMin,
        @NotNull Long salaryMax,

        @NotNull Boolean isCvRequired,

        @Size(max = 50)
        String address,

        @Size(max = 50)
        String city,

        @Size(max = 50)
        String country
) {}
