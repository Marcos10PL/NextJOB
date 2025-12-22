package com.nextjob.services;

import com.nextjob.dtos.ApplicationListDto;
import com.nextjob.entities.Application;
import com.nextjob.entities.JobAnnouncement;
import com.nextjob.entities.JobSeeker;
import com.nextjob.mappers.ApplicationMapper;
import com.nextjob.repositories.ApplicationRepository;
import com.nextjob.repositories.JobAnnouncementRepository;
import com.nextjob.repositories.JobSeekerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobAnnouncementRepository jobAnnouncementRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            JobAnnouncementRepository jobAnnouncementRepository,
            JobSeekerRepository jobSeekerRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.jobAnnouncementRepository =  jobAnnouncementRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    public void apply(Integer jobId, Integer userId) {
        JobAnnouncement job = jobAnnouncementRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getAuthor().getId().equals(userId) ||
                (job.getCompany() != null && job.getCompany().getOwner().getId().equals(userId))) {
            throw new IllegalArgumentException("You cannot apply to your own job");
        }

        if (applicationRepository.existsByJobAnnouncementIdAndJobSeekerId(jobId, userId)) {
            throw new IllegalArgumentException("Already applied");
        }

        JobSeeker seeker = jobSeekerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("JobSeeker profile not found"));

        Application application = new Application();
        application.setJobAnnouncement(job);
        application.setJobSeeker(seeker);
        applicationRepository.save(application);
    }

    public List<ApplicationListDto> getApplicationsForEmployer(Integer userId) {
        return applicationRepository.findForEmployer(userId).stream()
                .map(ApplicationMapper::toListDto)
                .toList();
    }
}
