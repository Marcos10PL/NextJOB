package com.nextjob.mappers;

import com.nextjob.dtos.ApplicationListDto;
import com.nextjob.entities.Application;

public class ApplicationMapper {

    public static ApplicationListDto toListDto(Application a) {
        return new ApplicationListDto(
                a.getId(),
                a.getJobAnnouncement().getId(),
                a.getJobSeeker().getFullName(),
                a.getJobSeeker().getEmail(),
                a.getJobSeeker().getMessage(),
                a.getAppliedAt()
        );
    }

}