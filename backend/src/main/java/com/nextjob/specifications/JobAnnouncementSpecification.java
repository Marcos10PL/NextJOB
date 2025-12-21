package com.nextjob.specifications;

import com.nextjob.dtos.JobAnnouncementFilter;
import com.nextjob.entities.JobAnnouncement;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class JobAnnouncementSpecification {

    public static Specification<JobAnnouncement> withFilters(JobAnnouncementFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // title LIKE
            if (filter.title() != null && !filter.title().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("title")),
                        "%" + filter.title().toLowerCase() + "%"
                ));
            }

            // industry
            if (filter.industryIds() != null && !filter.industryIds().isEmpty()) {
                predicates.add(root.get("industry").get("id").in(filter.industryIds()));
            }

            // contractType
            if (filter.contractTypeIds() != null && !filter.contractTypeIds().isEmpty()) {
                predicates.add(root.get("contractType").get("id").in(filter.contractTypeIds()));
            }

            // workloadType
            if (filter.workloadTypeIds() != null && !filter.workloadTypeIds().isEmpty()) {
                predicates.add(root.get("workloadType").get("id").in(filter.workloadTypeIds()));
            }

            // workMode
            if (filter.workModeIds() != null && !filter.workModeIds().isEmpty()) {
                predicates.add(root.get("workMode").get("id").in(filter.workModeIds()));
            }

            // paymentType
            if (filter.paymentTypeIds() != null && !filter.paymentTypeIds().isEmpty()) {
                predicates.add(root.get("paymentType").get("id").in(filter.paymentTypeIds()));
            }

            // salary range
            if (filter.salaryMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("salaryMin"), filter.salaryMin()));
            }
            if (filter.salaryMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("salaryMax"), filter.salaryMax()));
            }

            // city LIKE
            if (filter.city() != null && !filter.city().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("city")), "%" + filter.city().toLowerCase() + "%"));
            }

            // country LIKE
            if (filter.country() != null && !filter.country().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("country")), "%" + filter.country().toLowerCase() + "%"));
            }

            // isCvRequired
            if (filter.isCvRequired() != null) {
                predicates.add(cb.equal(root.get("isCvRequired"), filter.isCvRequired()));
            }

            // isCompanyAnnouncement
            if (filter.isCompanyAnnouncement() != null) {
                if (filter.isCompanyAnnouncement()) {
                    predicates.add(cb.isNotNull(root.get("company")));
                } else {
                    predicates.add(cb.isNull(root.get("company")));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
