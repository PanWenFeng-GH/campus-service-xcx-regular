package com.boot.api;

import com.boot.entity.Goods;
import com.boot.service.CodeService;
import com.boot.util.PageRequestHelper;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.boot.entity.TCode;
import com.boot.repository.TCodeRepository;

import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/code")
@Api(value = "数据集合", tags = "数据集合接口")
public class ApiCodeController {
	@Autowired
	private CodeService codeService;
	/*
	 */
	@ResponseBody
	@RequestMapping("/list")
	public BaseMessage<?> list(HttpServletRequest request, ModelMap map, TCode data) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Sort.Direction.DESC, "releaseTime"));
		Page<TCode> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		List<TCode> list = codeService.pagelist(data);
		return MessageHandler.createSuccessVo(list,"操作成功",(int)page.getTotal());
	}

	@RequestMapping("/detail")
	public BaseMessage<?> code(String id, ModelMap map) {
		TCode code  = codeService.findOneById(id);
		return MessageHandler.createSuccessVo(code,"获取成功");
	}

}
