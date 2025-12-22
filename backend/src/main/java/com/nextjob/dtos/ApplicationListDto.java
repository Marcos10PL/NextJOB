package com.nextjob.dtos;

import com.nextjob.entities.Application;

import java.time.LocalDateTime;

public record ApplicationListDto(
        Integer id,
        Integer jobAnnouncementId,
        String jobSeekerFullName,
        String jobSeekerEmail,
        String jobSeekerMessage,
        LocalDateTime appliedAt
) {
    public static ApplicationListDto fromEntity(Application application) {
        return new ApplicationListDto(
                application.getId(),
                application.getJobAnnouncement().getId(),
                application.getJobSeeker().getFullName(),
                application.getJobSeeker().getEmail(),
                application.getJobSeeker().getMessage(),
                application.getAppliedAt()
        );
    }
}