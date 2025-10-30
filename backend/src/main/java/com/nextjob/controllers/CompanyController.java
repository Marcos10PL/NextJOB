package com.nextjob.controllers;

import com.nextjob.dtos.CompanyDto;
import com.nextjob.entities.Company;
import com.nextjob.entities.User;
import com.nextjob.mappers.CompanyMapper;
import com.nextjob.repositories.UserRepository;
import com.nextjob.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final UserRepository userRepository;

    public CompanyController(CompanyService companyService, UserRepository userRepository) {
        this.companyService = companyService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto dto, Authentication authentication) {
        User owner = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Company company = companyService.createCompany(dto, owner);
        return ResponseEntity.ok(CompanyMapper.toDto(company));
    }

    @PatchMapping("/me")
    public CompanyDto updateMyCompany(@RequestBody CompanyDto dto, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Company company = companyService.updateMyCompany(dto, user);
        return CompanyMapper.toDto(company);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getCompanies(@RequestParam(required = false) Integer ownerId) {
        if (ownerId != null) {
            return companyService.getCompanyByOwnerOptional(ownerId)
                    .map(company -> ResponseEntity.ok(List.of(CompanyMapper.toDto(company))))
                    .orElse(ResponseEntity.notFound().build());
        }

        List<CompanyDto> allCompanies = companyService.getAllCompanies()
                .stream()
                .map(CompanyMapper::toDto)
                .toList();
        return ResponseEntity.ok(allCompanies);
    }
}
