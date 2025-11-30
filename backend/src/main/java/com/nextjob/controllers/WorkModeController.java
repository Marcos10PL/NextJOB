package com.nextjob.controllers;

import com.nextjob.entities.WorkMode;
import com.nextjob.repositories.WorkModeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-modes")
public class WorkModeController {

    private final WorkModeRepository repo;

    public WorkModeController(WorkModeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<WorkMode> getAll() {
        return repo.findAll();
    }
}
