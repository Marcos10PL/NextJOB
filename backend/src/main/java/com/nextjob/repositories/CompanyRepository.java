package com.nextjob.repositories;


import com.nextjob.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByOwnerId(Integer ownerId);

    boolean existsByOwnerId(Integer ownerId);
}
