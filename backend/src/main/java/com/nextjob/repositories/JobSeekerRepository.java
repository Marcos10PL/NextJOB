package com.nextjob.repositories;

import com.nextjob.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker,Integer> {
    boolean existsByUserId(Integer userId);

    Optional<JobSeeker> findByUserId(Integer userId);
}
