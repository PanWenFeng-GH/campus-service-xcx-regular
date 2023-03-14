package com.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.entity.Feedback;
import com.boot.entity.TCode;

import java.util.List;

public interface CodeDao extends BaseMapper<TCode>{

	List<TCode> pagelist(TCode data);
}
