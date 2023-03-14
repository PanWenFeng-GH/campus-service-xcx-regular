package com.boot.repository;

import com.boot.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public interface SetRepository extends JpaRepository<Set, String>,JpaSpecificationExecutor<Set> {

}
