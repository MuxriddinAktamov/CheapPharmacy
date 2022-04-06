package com.company.repository;

import com.company.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer>, JpaSpecificationExecutor<CompanyEntity> {

    Optional<CompanyEntity> findByPhone(String phone);

    Optional<CompanyEntity> findByEmail(String email);

}
