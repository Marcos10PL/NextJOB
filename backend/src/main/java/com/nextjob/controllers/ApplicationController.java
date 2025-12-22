package com.nextjob.controllers;

import com.nextjob.dtos.ApplicationListDto;
import com.nextjob.entities.Application;
import com.nextjob.entities.JobSeeker;
import com.nextjob.entities.User;
import com.nextjob.repositories.ApplicationRepository;
import com.nextjob.repositories.JobSeekerRepository;
import com.nextjob.repositories.UserRepository;
import com.nextjob.services.ApplicationService;
import com.nextjob.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationController(
            ApplicationService applicationService,
            UserRepository userRepository,
            JobSeekerRepository jobSeekerRepository,
            ApplicationRepository applicationRepository

    ) {
        this.applicationService = applicationService;
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.applicationRepository = applicationRepository;
    }

    @PostMapping("/job-announcements/{jobId}")
    public ResponseEntity<Void> apply(
            @PathVariable Integer jobId,
            Authentication authentication
    ) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        applicationService.apply(jobId, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public List<ApplicationListDto> myApplications(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        JobSeeker seeker = jobSeekerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("JobSeeker profile not found"));

        List<Application> applications = applicationRepository.findByJobSeekerId(seeker.getId());

        return applications.stream()
                .map(ApplicationListDto::fromEntity)
                .toList();
    }

    @GetMapping("/employer")
    public List<ApplicationListDto> employerApplications(
            Authentication authentication
    ) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        return applicationService.getApplicationsForEmployer(user.getId());
    }
}
