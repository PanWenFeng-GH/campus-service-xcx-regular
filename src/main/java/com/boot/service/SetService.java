package com.boot.service;

import com.boot.entity.BBS;
import com.boot.entity.Set;
import com.boot.vo.BaseMessage;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SetService {

	Page<Set> list(HttpServletRequest request, Set data);

	Set findOneById(String id);

	BaseMessage<?> save(Set data);

	BaseMessage<?> delete(String id);

	List<Set> pagelist(Set data);

	Set getLaster();
}
