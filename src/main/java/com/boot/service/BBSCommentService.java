package com.boot.service;

import com.boot.entity.BBSComment;
import com.boot.vo.BaseMessage;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BBSCommentService {

	Page<BBSComment> list(HttpServletRequest request, BBSComment data);

	BBSComment findOneById(String id);

	BaseMessage<?> save(BBSComment data);

	BaseMessage<?> delete(String id);

	List<BBSComment> pagelist(BBSComment data);

}
