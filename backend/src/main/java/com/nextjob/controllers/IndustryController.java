package com.nextjob.controllers;

import com.nextjob.dtos.CreateIndustryDto;
import com.nextjob.dtos.IndustryDto;
import com.nextjob.services.IndustryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/industries")
public class IndustryController {

    private final IndustryService industryService;

    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    @GetMapping
    public Page<IndustryDto> getIndustries(Pageable pageable) {
        return industryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryDto> getOne(@PathVariable int id) {
        return ResponseEntity.ok(industryService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<IndustryDto> create(@RequestBody CreateIndustryDto dto) {
        return ResponseEntity.ok(industryService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<IndustryDto> update(@PathVariable int id, @RequestBody CreateIndustryDto dto) {
        return ResponseEntity.ok(industryService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        industryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
