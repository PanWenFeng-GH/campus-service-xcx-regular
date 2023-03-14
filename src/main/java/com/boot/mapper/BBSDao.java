package com.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.entity.BBS;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BBSDao extends BaseMapper<BBS>{

	List<BBS> pagelist(BBS data);

    int countDate(@Param("date") Date date);
}
