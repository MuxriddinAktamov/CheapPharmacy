package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository  extends JpaRepository<RegionEntity, Integer>, JpaSpecificationExecutor<RegionEntity> {
}
