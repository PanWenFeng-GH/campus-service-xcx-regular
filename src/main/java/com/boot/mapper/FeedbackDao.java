package com.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.entity.Feedback;

import java.util.List;

public interface FeedbackDao extends BaseMapper<Feedback>{

	List<Feedback> pagelist(Feedback data);
}
