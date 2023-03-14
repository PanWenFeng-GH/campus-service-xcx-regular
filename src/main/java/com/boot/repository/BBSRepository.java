package com.boot.repository;

import com.boot.entity.BBS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BBSRepository extends JpaRepository<BBS, String>, JpaSpecificationExecutor<BBS> {

	@Query(value = "SELECT s.* FROM c_bbs s WHERE s.id=?1 ", nativeQuery = true)
	BBS findOneById(String id);
}
