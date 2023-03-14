package com.boot.service;

import com.boot.entity.TCode;
import com.boot.vo.BaseMessage;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CodeService {

	Page<TCode> list(HttpServletRequest request, TCode data);

	TCode findOneById(String id);

	BaseMessage<?> save(TCode data);

	BaseMessage<?> delete(String id);

	List<TCode> pagelist(TCode data);

}
