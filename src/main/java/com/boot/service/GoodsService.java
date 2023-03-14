package com.boot.service;

import com.boot.entity.Goods;
import com.boot.vo.BaseMessage;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GoodsService {

	Page<Goods> list(HttpServletRequest request, Goods data);

	Goods findOneById(String id);

	BaseMessage<?> save(Goods data);

	BaseMessage<?> delete(String id);

	List<Goods> pagelist(Goods data);

}
