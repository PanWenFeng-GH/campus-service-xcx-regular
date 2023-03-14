package com.boot.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.boot.entity.Role;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>,JpaSpecificationExecutor<Role>{

	@Query(value="select r.role_code from t_user u  LEFT JOIN t_role r on r.id = u.type where u.id=?1 ",nativeQuery=true)
	String findRoleCodeByUserId(Integer userId);

	@Modifying
	@Query(value="insert into t_role_menu(role_id,menu_id) values(?1,?2)",nativeQuery=true)
	void insertRoleMenu(Integer roleId,Integer menuId);

}
