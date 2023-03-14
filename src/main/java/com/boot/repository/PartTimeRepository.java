package com.boot.repository;

import com.boot.entity.PartTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PartTimeRepository extends JpaRepository<PartTime, String>, JpaSpecificationExecutor<PartTime> {
}
