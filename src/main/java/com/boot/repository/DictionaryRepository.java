package com.boot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.boot.entity.Dictionary;

@Transactional
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Integer>,JpaSpecificationExecutor<Dictionary>{

	@Query(value = " select t from  Dictionary t where t.pid=-1")
	List<Dictionary> findTopListDictionary();

	List<Dictionary> findByPidAndName(Integer pid, String name);

	List<Dictionary> findByPidAndCode(Integer pid, String name);
}
