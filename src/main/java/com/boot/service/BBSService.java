package com.boot.service;

import com.boot.entity.BBS;
import com.boot.vo.BaseMessage;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface BBSService {

	Page<BBS> list(HttpServletRequest request, BBS banner);

	BBS findOneById(String id);

	BaseMessage<?> save(BBS banner);

	BaseMessage<?> delete(String id);

	List<BBS> pagelist(BBS data);

    int countDate(Date date);
}
