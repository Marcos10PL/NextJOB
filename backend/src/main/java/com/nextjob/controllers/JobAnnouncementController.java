package com.nextjob.controllers;

import com.nextjob.dtos.JobAnnouncementCreateRequest;
import com.nextjob.entities.User;
import com.nextjob.repositories.JobAnnouncementRepository;
import com.nextjob.repositories.UserRepository;
import com.nextjob.services.JobAnnouncementService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequestMapping("/api/job-announcements")
public class JobAnnouncementController {

    private final JobAnnouncementService service;
    private final UserRepository userRepo;

    public JobAnnouncementController(JobAnnouncementService service, UserRepository userRepo) {
        this.service = service;
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody JobAnnouncementCreateRequest request,
            Authentication authentication
    ) {
        User author = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Integer id = service.create(request, author);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }
}
