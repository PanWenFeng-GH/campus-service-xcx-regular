package com.boot.service;

import com.boot.entity.Banner;
import com.boot.vo.BaseMessage;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BannerService {

	Page<Banner> list(HttpServletRequest request, Banner banner);

	Banner findOneById(String id);

	BaseMessage<?> save(Banner banner);

	List<Banner> findByType(String type);

	BaseMessage<?> delete(String id);

}
