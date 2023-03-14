package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boot.entity.TCode;


@Transactional
@Repository
public interface TCodeRepository extends JpaRepository<TCode, String>,JpaSpecificationExecutor<TCode> {

}
