package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boot.entity.TData;


@Transactional
@Repository
public interface TDataRepository extends JpaRepository<TData, String>,JpaSpecificationExecutor<TData> {

	@Query(value="SELECT * FROM t_data where type=?1 and wechat_id=?2 ORDER BY date desc LIMIT 1",nativeQuery=true)
	TData newData(int type, String userId);
	
	@Query(value="SELECT * FROM t_data where type=?1 and wechat_id=?2 and mtype=?3 ORDER BY date desc LIMIT 1",nativeQuery=true)
	TData newData(Integer type, String userId, String mtype);
	
	@Query(value="SELECT * FROM t_data where type=?1 and wechat_id=?2 and DATE_FORMAT(date,'%Y-%m-%d')>=  DATE_FORMAT(date_add(now(), interval -7 day),'%Y-%m-%d')  ORDER BY date desc",nativeQuery=true)
	List<TData> get7DataByType(int type, String userId);

	@Query(value="SELECT * FROM t_data where type=?1 and wechat_id=?2 and mtype=?3 and DATE_FORMAT(date,'%Y-%m-%d')>=  DATE_FORMAT(date_add(now(), interval -7 day),'%Y-%m-%d')  ORDER BY date desc",nativeQuery=true)
	List<TData> get7DataByType(Integer type, String userId, String mtype);
	
	@Query(value="SELECT * FROM t_data where type=?1 and wechat_id=?2 and DATE_FORMAT(date,'%Y-%m-%d') =  DATE_FORMAT(now(),'%Y-%m-%d')  ORDER BY date desc LIMIT 1",nativeQuery=true)
	TData getNowData(Integer type, String userId);

	@Query(value="SELECT * FROM t_data where type=?1 and wechat_id=?2 and mtype=?3  and DATE_FORMAT(date,'%Y-%m-%d') =  DATE_FORMAT(now(),'%Y-%m-%d')  ORDER BY date desc LIMIT 1",nativeQuery=true)
	TData getNowData(Integer type, String userId, String mtype);

	
	
}
