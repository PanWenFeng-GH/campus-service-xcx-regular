package com.boot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.entity.TWechatUser;
import com.boot.service.WechatUserService;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;


@Controller
@RequestMapping("/private/wechat/")
public class WechatUserController extends BaseController{
	@Autowired
	private WechatUserService wechatUserService;
	
	/**
	 * 列表展示
	 */
	/*@RequestMapping("list")
	public String list(ModelMap map,HttpServletRequest request,TWechatUser wechatUser) {
		Page<TWechatUser> pageList = wechatUserService.setList(request,wechatUser);
		map.put("pageList", pageList);
		map.put("wechatUser", wechatUser);
		return getPath("/wechat/wechat_list");
		
	}*/
	@ResponseBody
	@RequestMapping("list")
	public BaseMessage<?> list(ModelMap map,HttpServletRequest request,TWechatUser wechatUser) {
		Page<TWechatUser> pageList = wechatUserService.setList(request,wechatUser);
		return MessageHandler.createSuccessVo(pageList.getContent(),"操作成功",(int) pageList.getTotalElements());
	}
	
	@ResponseBody
	@RequestMapping("alllist")
	public BaseMessage<?> alllist(ModelMap map,HttpServletRequest request,TWechatUser wechatUser) {
		List<TWechatUser> alllist = wechatUserService.alllist(request,wechatUser);
		return MessageHandler.createSuccessVo(alllist,"操作成功");
	}
	/**
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public BaseMessage<?> editPublish(String id,Integer hasPublish) {
		return wechatUserService.editPublish(id,hasPublish);
	}

	@RequestMapping("/save")
	@ResponseBody
	public BaseMessage<?> save(@RequestBody TWechatUser data) {
//		return wechatUserService.save(data);
		return wechatUserService.save(data);
//		return MessageHandler.createSuccessVo("操作成功");
	}

	@RequestMapping("/dell")
	@ResponseBody
	public BaseMessage<?> dell(String id) {
		return wechatUserService.dell(id);
	}
	
	@RequestMapping("/open")
	@ResponseBody
	public BaseMessage<?> open(String id) {
		return wechatUserService.open(id);
	}
	
	@RequestMapping("/detail")
	@ResponseBody
	public BaseMessage<?> detail(String id) {
		TWechatUser twu = wechatUserService.detail(id);
		return MessageHandler.createSuccessVo(twu,"操作成功");
	}

	@RequestMapping("/reset")
	@ResponseBody
	public BaseMessage<?> reset(String id) throws Exception {
		wechatUserService.reset(id);
		return MessageHandler.createSuccessVo("操作成功");
	}
}
