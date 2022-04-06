package com.company.spec;

import com.company.entity.CommentEntity;
import com.company.entity.PharmacyMedicineEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PharmacyMedicineSpecfication {
    public static Specification<PharmacyMedicineEntity> isNotNull(String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(field)));
    }
    public static Specification<PharmacyMedicineEntity> id(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field),id));
    }
    public static Specification<PharmacyMedicineEntity> date(LocalDate date1, LocalDate date2) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdDate"), date1,date2));
    }
    public static Specification<PharmacyMedicineEntity> equal(String field, String value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field),value));
    }
}
