package com.nextjob.mappers;

import com.nextjob.dtos.JobAnnouncementListResponse;
import com.nextjob.entities.JobAnnouncement;

public class JobAnnouncementMapper {

    public static JobAnnouncementListResponse toListDto(JobAnnouncement job) {
        return new JobAnnouncementListResponse(
                job.getId(),
                job.getTitle(),
                job.getCity(),
                job.getCountry(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getAuthor() != null ? job.getAuthor().getId() : null,
                job.getAuthor() != null ? job.getAuthor().getFullName() : null,
                job.getCompany() != null ? job.getCompany().getId() : null,
                job.getCompany() != null ? job.getCompany().getName() : null,
                job.getIndustry() != null ? job.getIndustry().getName() : null,
                job.getContractType() != null ? job.getContractType().getName() : null,
                job.getWorkloadType() != null ? job.getWorkloadType().getName() : null,
                job.getWorkMode() != null ? job.getWorkMode().getName() : null,
                job.getPaymentType() != null ? job.getPaymentType().getName() : null,
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
}