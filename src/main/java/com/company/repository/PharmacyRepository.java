package com.company.repository;

import com.company.entity.PharmacyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PharmacyRepository extends JpaRepository<PharmacyEntity, Integer>, JpaSpecificationExecutor<PharmacyEntity> {

    Optional<PharmacyEntity> findByPhone(String phone);

    Optional<PharmacyEntity> findByEmail(String email);

}
