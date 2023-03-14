package com.boot.service;
 
import com.boot.entity.TWechatUser;
 
/**
 * 
 *
 */
public interface ApiLoginService {
	public TWechatUser getOpenId(String code, String dataSource)throws Exception;
}