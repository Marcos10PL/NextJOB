package com.nextjob.mappers;

import com.nextjob.dtos.AuthorDto;
import com.nextjob.dtos.CompanyDetailsDto;
import com.nextjob.dtos.JobAnnouncementDetailsResponse;
import com.nextjob.entities.JobAnnouncement;

public class JobAnnouncementDetailsMapper {

    public static JobAnnouncementDetailsResponse toDetailsDto(JobAnnouncement job) {
        return new JobAnnouncementDetailsResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getCvRequired(),
                job.getCity(),
                job.getCountry(),
                job.getAddress(),
                job.getUpdatedAt(),
                job.getIndustry().getName(),
                job.getContractType().getName(),
                job.getWorkloadType().getName(),
                job.getWorkMode().getName(),
                job.getPaymentType().getName(),
                job.getCompany() != null
                        ? new CompanyDetailsDto(
                        job.getCompany().getId(),
                        job.getCompany().getName(),
                        job.getCompany().getDescription(),
                        job.getCompany().getEmail(),
                        job.getCompany().getWebsite(),
                        job.getCompany().getIndustry() != null ? job.getCompany().getIndustry().getName() : null,
                        job.getCompany().getAddress(),
                        job.getCompany().getCity(),
                        job.getCompany().getCountry()
                 ) : null,
                 new AuthorDto(
                    job.getAuthor().getId(),
                    job.getAuthor().getFullName(),
                    job.getAuthor().getEmail(),
                    job.getAuthor().getAddress(),
                    job.getAuthor().getCity(),
                    job.getAuthor().getCountry()
                 )
        );
    }

}
