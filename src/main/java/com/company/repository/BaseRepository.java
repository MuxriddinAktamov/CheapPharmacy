package com.company.repository;

import com.company.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseRepository extends JpaRepository<BaseEntity, Integer>, JpaSpecificationExecutor<BaseEntity> {


    @Query("select s from BaseEntity s where s.medicine.id=:id")
    Page<BaseEntity> findByMedicineId(@Param("id") Integer id, Pageable pageable);

    List<BaseEntity> findByMedicineId(Integer id);

    List<BaseEntity> findByProfileId(Integer id);



    @Query("select s from BaseEntity s where s.profile.id=:id")
    Page<BaseEntity> findByProfileId(@Param("id") Integer id, Pageable pageable);

    @Query("select s from BaseEntity s where s.pharmacy.id=:id")
    Page<BaseEntity> findByPharmacyId(@Param("id") Integer id, Pageable pageable);


}
