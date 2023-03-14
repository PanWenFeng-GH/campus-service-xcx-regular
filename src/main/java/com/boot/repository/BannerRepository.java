package com.boot.repository;

import com.boot.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, String>, JpaSpecificationExecutor<Banner> {

	@Query(value = "SELECT s.* FROM c_banner s WHERE s.id=?1 ", nativeQuery = true)
	Banner findOneById(String id);
	
	@Query(value="SELECT * FROM c_banner b WHERE b.type=?1 ORDER BY b.sort ASC", nativeQuery = true)
	List<Banner> findByType(String type);

	Banner findByIdAndType(String id, String type);

	void deleteByIdAndType(String id, String type);
	
}
