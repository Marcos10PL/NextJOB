package com.nextjob.services;

import com.nextjob.dtos.CreateIndustryDto;
import com.nextjob.dtos.IndustryDto;
import com.nextjob.entities.Industry;
import com.nextjob.repositories.IndustryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService {

    private final IndustryRepository industryRepository;

    public IndustryService(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    public Page<IndustryDto> findAll(Pageable pageable) {
        return industryRepository.findAll(pageable)
                .map(ind -> new IndustryDto(ind.getId(), ind.getName()));
    }

    public IndustryDto findById(Integer id) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Industry not found"));
        return new IndustryDto(industry.getId(), industry.getName());
    }

    public IndustryDto create(CreateIndustryDto dto) {
        if (industryRepository.findByName(dto.name()).isPresent()) {
            throw new IllegalArgumentException("Industry already exists");
        }

        Industry newIndustry = new Industry(dto.name());
        Industry saved = industryRepository.save(newIndustry);
        return new IndustryDto(saved.getId(), saved.getName());
    }

    public IndustryDto update(Integer id, CreateIndustryDto dto) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Industry not found"));
        industry.setName(dto.name());
        Industry updated = industryRepository.save(industry);
        return new IndustryDto(updated.getId(), updated.getName());
    }

    public void delete(Integer id) {
        industryRepository.deleteById(id);
    }
}
