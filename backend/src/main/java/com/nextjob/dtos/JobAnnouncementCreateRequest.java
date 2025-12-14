package com.nextjob.dtos;

import jakarta.validation.constraints.*;

public record JobAnnouncementCreateRequest(

        @NotNull Integer industryId,
        @NotNull Integer contractTypeId,
        @NotNull Integer workloadTypeId,
        @NotNull Integer workModeId,
        @NotNull Integer paymentTypeId,

        Integer companyId,

        @NotBlank String title,
        @NotBlank String description,

        @NotNull Long salaryMin,
        @NotNull Long salaryMax,

        @NotNull Boolean isCvRequired,

        String address,
        String city,
        String country
) {}
