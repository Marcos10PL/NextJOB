package com.nextjob.repositories;

import com.nextjob.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findByJobSeekerId(Integer jobSeekerId);

    @Query("""
                select a
                from Application a
                where a.jobAnnouncement.author.id = :userId
            """)
    List<Application> findForEmployer(Integer userId);

    boolean existsByJobAnnouncementIdAndJobSeekerId(
            Integer jobAnnouncementId,
            Integer jobSeekerId
    );
}

