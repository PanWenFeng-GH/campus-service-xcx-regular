package com.boot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.boot.entity.Menu;
@Transactional
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>,JpaSpecificationExecutor<Menu>{
	
	@Query(value=" select t from  Menu t where t.supId='-1' and t.status='0' order by sort asc ")
	List<Menu> findTopAll()throws Exception;

	@Query(value="select count(1)count from t_user t LEFT JOIN t_role r on r.id= t.type LEFT JOIN t_role_menu m on m.role_id= r.id where t.id=?1 and m.id is not null",nativeQuery=true)
	Integer countByUserId(Integer userId);

	@Query(value="select DISTINCT m.* from t_menu m left join t_role_menu r on r.menu_id=m.id where r.role_id=?1 and m.status =0",nativeQuery=true)
	List<?> getRoleMenuById(Integer id);
	
	@Modifying
	@Query(value="delete from t_role_menu where role_id=?1",nativeQuery=true)
	void dellByRoleId(Integer id);
	
	@Query(value =  "SELECT DISTINCT m.*  FROM t_menu m " + 
					"	LEFT JOIN t_role_menu r ON r.menu_id = m.id \n" + 
					"	LEFT JOIN t_user u ON u.role_id=r.role_id\n" + 
					"WHERE u.id=?1 AND m.STATUS = 0 and m.p_id='-1' order by m.sort asc", nativeQuery = true)
	List<Menu> findTopByServerAndUserId(Integer userId);
}
