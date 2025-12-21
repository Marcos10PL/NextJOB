package com.nextjob.controllers;

import com.nextjob.dtos.JobAnnouncementCreateRequest;
import com.nextjob.dtos.JobAnnouncementDetailsResponse;
import com.nextjob.dtos.JobAnnouncementFilter;
import com.nextjob.dtos.JobAnnouncementListResponse;
import com.nextjob.entities.JobAnnouncement;
import com.nextjob.entities.User;
import com.nextjob.repositories.JobAnnouncementRepository;
import com.nextjob.repositories.UserRepository;
import com.nextjob.services.JobAnnouncementService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping
    public Page<JobAnnouncementListResponse> list(
            JobAnnouncementFilter filter,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return service.listDto(filter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobAnnouncementDetailsResponse> getOne(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.getDetails(id));
    }
}
