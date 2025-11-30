package com.nextjob.controllers;

import com.nextjob.entities.WorkloadType;
import com.nextjob.repositories.WorkloadTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workload-types")
public class WorkloadTypeController {

    private final WorkloadTypeRepository repo;

    public WorkloadTypeController(WorkloadTypeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<WorkloadType> getAll() {
        return repo.findAll();
    }
}