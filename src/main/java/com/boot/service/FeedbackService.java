package com.boot.service;

import com.boot.entity.Feedback;
import com.boot.vo.BaseMessage;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FeedbackService {

	Page<Feedback> list(HttpServletRequest request, Feedback data);

	Feedback findOneById(String id);

	BaseMessage<?> save(Feedback data);

	BaseMessage<?> delete(String id);

	List<Feedback> pagelist(Feedback data);

}
