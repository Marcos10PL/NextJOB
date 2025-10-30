package com.nextjob.controllers;

import com.nextjob.dtos.JobSeekerDto;
import com.nextjob.entities.JobSeeker;
import com.nextjob.entities.User;
import com.nextjob.repositories.UserRepository;
import com.nextjob.services.JobSeekerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/job-seekers")
public class JobSeekerController {
    private final UserRepository userRepository;
    private final JobSeekerService jobSeekerService;

    public JobSeekerController(UserRepository userRepository, JobSeekerService jobSeekerService) {
        this.userRepository = userRepository;
        this.jobSeekerService = jobSeekerService;
    }

    @PostMapping
    public ResponseEntity<JobSeekerDto> createJobSeeker(@RequestBody JobSeekerDto dto, Authentication authentication) {
        User owner = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobSeeker jobSeeker = jobSeekerService.createJobSeeker(dto, owner);

        return ResponseEntity.ok(new JobSeekerDto(
                jobSeeker.getFullName(),
                jobSeeker.getEmail(),
                jobSeeker.getMessage()
        ));
    }

    @PatchMapping("/me")
    public ResponseEntity<JobSeekerDto> updateJobSeeker(@RequestBody JobSeekerDto dto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        JobSeeker jobSeeker = jobSeekerService.updateJobSeeker(dto, user);

        JobSeekerDto JSdto = new JobSeekerDto(
                jobSeeker.getFullName(),
                jobSeeker.getEmail(),
                jobSeeker.getMessage()
        );

        return ResponseEntity.ok(JSdto);
    }

    @GetMapping("/me")
    public ResponseEntity<JobSeekerDto> getMyJobSeekerProfile(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        JobSeeker jobSeeker = jobSeekerService.getJobSeekerByUserId(user.getId());

        JobSeekerDto dto = new JobSeekerDto(
                jobSeeker.getFullName(),
                jobSeeker.getEmail(),
                jobSeeker.getMessage()
        );

        return ResponseEntity.ok(dto);
    }
}
