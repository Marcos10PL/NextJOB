package com.nextjob.services;

import com.nextjob.dtos.CompanyDto;
import com.nextjob.entities.Company;
import com.nextjob.entities.Industry;
import com.nextjob.entities.User;
import com.nextjob.repositories.CompanyRepository;
import com.nextjob.repositories.IndustryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final IndustryRepository industryRepository;

    public CompanyService(CompanyRepository companyRepository, IndustryRepository industryRepository) {
        this.companyRepository = companyRepository;
        this.industryRepository = industryRepository;
    }

    public Company createCompany(CompanyDto dto, User owner) {
        if (companyRepository.existsByOwnerId(owner.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a company");
        }

        if (dto.industryId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Industry ID must be provided");
        }

        Industry industry = industryRepository.findById(dto.industryId())
                .orElseThrow(() -> new IllegalArgumentException("Industry with ID " + dto.industryId() + " not found"));

        Company company = new Company();
        company.setOwner(owner);
        company.setName(dto.name());
        company.setDescription(dto.description());
        company.setEmail(dto.email());
        company.setWebsite(dto.website());
        company.setAddress(dto.address());
        company.setCity(dto.city());
        company.setCountry(dto.country());
        company.setIndustry(industry);

        return companyRepository.save(company);
    }

    public Company updateMyCompany(CompanyDto dto, User user) {
        Company company = companyRepository.findByOwnerId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        if (dto.name() != null) company.setName(dto.name());
        if (dto.description() != null) company.setDescription(dto.description());
        if (dto.email() != null) company.setEmail(dto.email());
        if (dto.website() != null) company.setWebsite(dto.website());
        company.setAddress(dto.address());
        company.setCity(dto.city());
        company.setCountry(dto.country());

        if (dto.industryId() != null) {
            Industry industry = industryRepository.findById(dto.industryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Industry with ID " + dto.industryId() + " not found"));
            company.setIndustry(industry);
        }

        return companyRepository.save(company);
    }

    public Optional<Company> getCompanyByOwnerOptional(Integer ownerId) {
        return companyRepository.findByOwnerId(ownerId);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
