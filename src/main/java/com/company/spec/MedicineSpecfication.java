package com.company.spec;

import com.company.entity.CompanyEntity;
import com.company.entity.MedicineEntity;
import com.company.enums.MedicineKategory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MedicineSpecfication {
    public static Specification<MedicineEntity> isNotNull(String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(field)));
    }

    public static Specification<MedicineEntity> id(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }

    public static Specification<MedicineEntity> kategory(String field, MedicineKategory kategory) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), kategory));
    }

    public static Specification<MedicineEntity> price(String field, Double id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }

    public static Specification<MedicineEntity> date(LocalDate date1, LocalDate date2) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdDate"), date1, date2));
    }

    public static Specification<MedicineEntity> equal(String field, String value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value));
    }
}
