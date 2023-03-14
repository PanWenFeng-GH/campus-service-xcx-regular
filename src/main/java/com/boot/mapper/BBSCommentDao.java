package com.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.entity.BBSComment;
import com.boot.entity.Feedback;

import java.util.List;

public interface BBSCommentDao extends BaseMapper<BBSComment>{

	List<BBSComment> pagelist(BBSComment data);
}
