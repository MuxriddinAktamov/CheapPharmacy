package com.company.repository;

import com.company.entity.MedicineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
public interface MedicineRepository extends JpaRepository<MedicineEntity, Integer>, JpaSpecificationExecutor<MedicineEntity> {

    List<MedicineEntity> findByName(String name, Pageable pageable);

    Page<MedicineEntity> findByCompanyId(Integer id, Pageable pageable);
}
