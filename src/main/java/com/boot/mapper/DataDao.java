package com.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.entity.TData;

public interface DataDao extends BaseMapper<TData>{

	List<TData> pagelist(@Param("name")String name,@Param("type")String type);

}
