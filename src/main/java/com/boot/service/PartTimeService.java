package com.boot.service;

import com.boot.entity.PartTime;
import com.boot.entity.Set;
import com.boot.vo.BaseMessage;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface PartTimeService {

	Page<PartTime> list(HttpServletRequest request, PartTime data);

	PartTime findOneById(String id);

	BaseMessage<?> save(PartTime data);

	BaseMessage<?> delete(String id);

	List<PartTime> pagelist(PartTime data);

	int countDate(Date date);
}
