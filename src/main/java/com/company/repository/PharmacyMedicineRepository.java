package com.company.repository;

import com.company.entity.PharmacyEntity;
import com.company.entity.PharmacyMedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PharmacyMedicineRepository extends JpaRepository<PharmacyMedicineEntity, Integer>, JpaSpecificationExecutor<PharmacyMedicineEntity> {


    List<PharmacyMedicineEntity> findByMedicineId(Integer id);

    List<PharmacyMedicineEntity> findByAptekaId(Integer id);
}
