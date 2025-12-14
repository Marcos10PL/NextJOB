package com.nextjob.repositories;

import com.nextjob.entities.JobAnnouncement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAnnouncementRepository
        extends JpaRepository<JobAnnouncement, Integer>,
        JpaSpecificationExecutor<JobAnnouncement> {
}