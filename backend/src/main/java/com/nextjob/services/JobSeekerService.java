package com.nextjob.services;

import com.nextjob.dtos.JobSeekerDto;
import com.nextjob.entities.JobSeeker;
import com.nextjob.entities.User;
import com.nextjob.repositories.JobSeekerRepository;
import com.nextjob.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JobSeekerService {
    private final JobSeekerRepository jobSeekerRepository;
    private final UserRepository userRepository;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository, UserRepository userRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.userRepository = userRepository;
    }

    public JobSeeker createJobSeeker(JobSeekerDto dto, User user) {
        if(jobSeekerRepository.existsByUserId(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a job seeker profile");
        }

        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setUser(existingUser);
        jobSeeker.setFullName(dto.fullName());
        jobSeeker.setEmail(dto.email());
        jobSeeker.setMessage(dto.message());

        return jobSeekerRepository.save(jobSeeker);
    }

    public JobSeeker updateJobSeeker(JobSeekerDto dto, User user) {
        JobSeeker jobSeeker = jobSeekerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Seeker profile not found"));

        if (dto.fullName() != null) jobSeeker.setFullName(dto.fullName());
        if (dto.email() != null) jobSeeker.setEmail(dto.email());
        if (dto.message() != null) jobSeeker.setMessage(dto.message());

        return jobSeekerRepository.save(jobSeeker);
    }

    public JobSeeker getJobSeekerByUserId(Integer userId) {
        return jobSeekerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Seeker profile not found"));
    }
}
