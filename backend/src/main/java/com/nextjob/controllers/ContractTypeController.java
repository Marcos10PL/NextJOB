package com.nextjob.controllers;

import com.nextjob.entities.ContractType;
import com.nextjob.repositories.ContractTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contract-types")
public class ContractTypeController {

    private final ContractTypeRepository contractTypeRepo;

    public ContractTypeController(ContractTypeRepository contractTypeRepo) {
        this.contractTypeRepo = contractTypeRepo;
    }

    @GetMapping
    public List<ContractType> getAllContractTypes() {
        return contractTypeRepo.findAll();
    }
}
