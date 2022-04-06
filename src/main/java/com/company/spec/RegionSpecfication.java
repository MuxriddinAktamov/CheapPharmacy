package com.company.spec;

import com.company.entity.PharmacyEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.enums.ProfileStatus;
import com.company.enums.RegionStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RegionSpecfication {
    public static Specification<RegionEntity> isNotNull(String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(field)));
    }
    public static Specification<RegionEntity> id(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field),id));
    }
    public static Specification<RegionEntity> date(LocalDate date1, LocalDate date2) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdDate"), date1,date2));
    }
    public static Specification<RegionEntity> equal(String field, String value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field),value));
    }
    public static Specification<RegionEntity> status(RegionStatus status) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }
}
