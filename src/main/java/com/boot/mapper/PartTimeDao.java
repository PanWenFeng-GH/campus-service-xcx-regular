package com.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.entity.PartTime;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PartTimeDao extends BaseMapper<PartTime>{

	List<PartTime> pagelist(PartTime data);

    int countDate(@Param("date") Date date);
}
