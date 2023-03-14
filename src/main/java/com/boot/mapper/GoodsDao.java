package com.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.entity.Goods;

import java.util.List;

public interface GoodsDao extends BaseMapper<Goods>{

	List<Goods> pagelist(Goods data);
}
