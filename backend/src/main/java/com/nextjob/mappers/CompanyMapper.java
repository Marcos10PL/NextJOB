package com.nextjob.mappers;

import com.nextjob.dtos.CompanyDto;
import com.nextjob.entities.Company;

public class CompanyMapper {
    public static CompanyDto toDto(Company company) {
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getDescription(),
                company.getEmail(),
                company.getWebsite(),
                company.getIndustry() != null ? company.getIndustry().getId() : null,
                company.getAddress(),
                company.getCity(),
                company.getCountry()
        );
    }
}