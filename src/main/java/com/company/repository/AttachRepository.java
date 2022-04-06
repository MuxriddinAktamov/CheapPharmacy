package com.company.repository;

import com.company.entity.AttachEntity;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttachRepository extends JpaRepository<AttachEntity, Integer> {
    Optional<AttachEntity> findByToken(String tokem);

    void deleteByToken(String token);

}
