package com.nextjob.controllers;

import com.nextjob.entities.PaymentType;
import com.nextjob.repositories.PaymentTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment-types")
public class PaymentTypeController {

    private final PaymentTypeRepository repo;

    public PaymentTypeController(PaymentTypeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<PaymentType> getAll() {
        return repo.findAll();
    }
}