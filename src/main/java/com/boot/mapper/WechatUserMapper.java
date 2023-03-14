package com.boot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WechatUserMapper {

	List<Map<String, Object>> newUsersPageList(Map<String, Object> paramsMap);

	long newUsersCount(Map<String, Object> paramsMap);

	List<Map<?, ?>> newUsersEchartsSex(@Param("startDate")String startDate,
			@Param("endDate")String endDate);
	
	List<Map<?, ?>> newUsersEchartsCityTop10(@Param("startDate")String startDate,
			@Param("endDate")String endDate);
	
	List<Map<?, ?>> newUsersEchartsRh30();
	
	List<Map<?, ?>> newUsersEchartsXz30();

	List<Map<String, Object>> newUsersExportList(@Param("startDate")String startDate,@Param("endDate")String endDate);

}
