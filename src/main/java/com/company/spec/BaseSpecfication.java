package com.company.spec;

import com.company.entity.BaseEntity;
import com.company.entity.CommentEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BaseSpecfication {

    public static Specification<BaseEntity> isNotNull(String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(field)));
    }

    public static Specification<BaseEntity> id(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }


    public static Specification<BaseEntity> date(LocalDate date1, LocalDate date2) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdDate"), date1, date2));
    }

    public static Specification<BaseEntity> equal(String field, Integer value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value));
    }
}
