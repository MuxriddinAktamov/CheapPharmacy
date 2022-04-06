package com.company.repository;

import com.company.entity.BaseEntity;
import com.company.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer>, JpaSpecificationExecutor<CommentEntity> {

    @Query("select s from CommentEntity s where s.medicine=:id")
    Page<CommentEntity> findByMedicineId(@Param("id") Integer id, Pageable pageable);

    @Query("select s from CommentEntity s where s.profile.id=:id")
    Page<CommentEntity> findByProfileId(@Param("id") Integer id, Pageable pageable);

    @Query("select s from CommentEntity s where s.apteka.id=:id")
    Page<CommentEntity> findByAptekaId(@Param("id") Integer id, Pageable pageable);
}
