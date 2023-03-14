package com.boot.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.boot.entity.TWechatUser;
import com.boot.vo.BaseMessage;


public interface WechatUserService {

	Page<TWechatUser> setList(HttpServletRequest request, TWechatUser wechatUser);

	List<Map<String, Object>> newUsersPageList(Map<String, Object> paramsMap);

	long newUsersCount(Map<String, Object> paramsMap);

	List<Map<?, ?>> newUsersEchartsSex(String startDate, String endDate);

	List<Map<?, ?>> newUsersEchartsCityTop10(String startDate, String endDate);

	List<Map<?, ?>> newUsersEchartsRh30();

	List<Map<?, ?>> newUsersEchartsXz30();

	List<Map<String, Object>> newUsersExportList(String startDate,String endDate);

	BaseMessage<?> editPublish(String id, Integer hasPublish);

    BaseMessage<?> save(TWechatUser data);

	BaseMessage<?> dell(String id);

	TWechatUser detail(String id);

	List<TWechatUser> alllist(HttpServletRequest request, TWechatUser wechatUser);

	BaseMessage<?> open(String id);

	void reset(String id)throws Exception;
}
