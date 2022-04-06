package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer>, JpaSpecificationExecutor<ProfileEntity> {
    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByLogin(String login);

    Optional<ProfileEntity> findByPhone(String phone);

    List<ProfileEntity> findByAptekaId(Integer id);


    Optional<ProfileEntity> findByLoginAndPswd(String login, String pswd);


}
