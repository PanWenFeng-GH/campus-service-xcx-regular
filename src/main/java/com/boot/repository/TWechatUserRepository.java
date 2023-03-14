package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boot.entity.TWechatUser;


@Transactional
@Repository
public interface TWechatUserRepository extends JpaRepository<TWechatUser, String>,JpaSpecificationExecutor<TWechatUser> {
	
	@Query(value="select * from t_wechat_user t where t.wx_openid=?1",nativeQuery=true)
	TWechatUser findByWxOpenId(String openid);

	@Query(value="select * from t_wechat_user t where t.phone=?1",nativeQuery=true)
	TWechatUser findByPhone(String phone);
	
	@Query(value="select * from t_wechat_user t where t.wx_unionid=?1",nativeQuery=true)
	TWechatUser findByWxUnionid(String unionid);
	
	@Query(value="select * from t_wechat_user t where t.qq_openid=?1",nativeQuery=true)
	TWechatUser findByQQOpenId(String openid);

	@Query(value="select * from t_wechat_user t where t.user_id=?1",nativeQuery=true)
	TWechatUser findByAppleUserId(String userID);

	@Modifying
	@Query(value="update t_wechat_user t set has_publish =?2 where t.id=?1",nativeQuery=true)
	void editPublish(String id, Integer hasPublish);

	@Modifying
	@Query(value="update t_wechat_user t set is_dell = 1 where t.id=?1",nativeQuery=true)
	void dell(String id);

	@Query(value="select * from t_wechat_user t where t.is_dell=0 order by create_time desc",nativeQuery=true)
	List<TWechatUser> alllist();

	@Modifying
	@Query(value="update t_wechat_user t set is_dell = 0 where t.id=?1",nativeQuery=true)
	void open(String id);

	@Query(value="select count(1) from t_wechat_user t where t.phone=?1 ",nativeQuery=true)
	Integer countByPhone(String phone);
}
