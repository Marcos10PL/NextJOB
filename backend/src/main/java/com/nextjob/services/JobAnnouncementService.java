package com.nextjob.services;

import com.nextjob.dtos.JobAnnouncementCreateRequest;
import com.nextjob.dtos.JobAnnouncementDetailsResponse;
import com.nextjob.dtos.JobAnnouncementFilter;
import com.nextjob.dtos.JobAnnouncementListResponse;
import com.nextjob.entities.JobAnnouncement;
import com.nextjob.entities.User;
import com.nextjob.mappers.JobAnnouncementDetailsMapper;
import com.nextjob.mappers.JobAnnouncementMapper;
import com.nextjob.repositories.*;
import com.nextjob.specifications.JobAnnouncementSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobAnnouncementService {

    private final JobAnnouncementRepository jobRepo;
    private final IndustryRepository industryRepo;
    private final ContractTypeRepository contractRepo;
    private final WorkloadTypeRepository workloadRepo;
    private final WorkModeRepository workModeRepo;
    private final PaymentTypeRepository paymentRepo;
    private final CompanyRepository companyRepo;

    public JobAnnouncementService(
            JobAnnouncementRepository jobRepo,
            IndustryRepository industryRepo,
            ContractTypeRepository contractRepo,
            WorkloadTypeRepository workloadRepo,
            WorkModeRepository workModeRepo,
            PaymentTypeRepository paymentRepo,
            CompanyRepository companyRepo
    ) {
        this.jobRepo = jobRepo;
        this.industryRepo = industryRepo;
        this.contractRepo = contractRepo;
        this.workloadRepo = workloadRepo;
        this.workModeRepo = workModeRepo;
        this.paymentRepo = paymentRepo;
        this.companyRepo = companyRepo;
    }

    public Integer create(
        JobAnnouncementCreateRequest req,
        User author
    ) {
        JobAnnouncement job = new JobAnnouncement();

        job.setTitle(req.title());
        job.setDescription(req.description());
        job.setSalaryMin(req.salaryMin());
        job.setSalaryMax(req.salaryMax());
        job.setCvRequired(req.isCvRequired());
        job.setAuthor(author);

        job.setCity(req.city());
        job.setCountry(req.country());
        job.setAddress(req.address());

        job.setIndustry(industryRepo.getReferenceById(req.industryId()));
        job.setContractType(contractRepo.getReferenceById(req.contractTypeId()));
        job.setWorkloadType(workloadRepo.getReferenceById(req.workloadTypeId()));
        job.setWorkMode(workModeRepo.getReferenceById(req.workModeId()));
        job.setPaymentType(paymentRepo.getReferenceById(req.paymentTypeId()));

        if (req.companyId() != null) {
            job.setCompany(companyRepo.getReferenceById(req.companyId()));
        }

        return jobRepo.save(job).getId();
    }

    public Page<JobAnnouncementListResponse> listDto(
            JobAnnouncementFilter filter,
            Pageable pageable
    ) {
        return jobRepo
                .findAll(
                        JobAnnouncementSpecification.withFilters(filter),
                        pageable
                )
                .map(JobAnnouncementMapper::toListDto);
    }

    @Transactional(readOnly = true)
    public JobAnnouncementDetailsResponse getDetails(Integer id) {
        JobAnnouncement job = jobRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Job announcement not found: " + id
                ));

        return JobAnnouncementDetailsMapper.toDetailsDto(job);
    }
}