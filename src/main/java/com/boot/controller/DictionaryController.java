package com.boot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.entity.Dictionary;
import com.boot.repository.DictionaryRepository;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
/**
 * 字典管理
 * 标签
 */
@Controller
@RequestMapping("/private/dictionary")
public class DictionaryController {
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@ResponseBody
	@RequestMapping("/list")
	public BaseMessage<?>  dictionaryList(String name, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Dictionary> pageList = dictionaryRepository.findTopListDictionary();
			return MessageHandler.createSuccessVo(pageList,"操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("/list/list")
	public  BaseMessage<?>  dictionaryListList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Dictionary> list = dictionaryRepository.findAll();
			return MessageHandler.createSuccessVo(list,"操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("/dell")
	public  BaseMessage<?>  dell(Integer id, HttpServletRequest request, HttpServletResponse response) {
		try {
			//判断是否绑定数据
			dictionaryRepository.deleteById(id);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("/save")
	public  BaseMessage<?>  save(Dictionary q, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (q.getPid()==null) q.setPid(-1);
			List<Dictionary> list = dictionaryRepository.findByPidAndName(q.getPid(),q.getName());
			if(list.size()>0)return MessageHandler.createFailedVo("同级下标签名称不可重复");
			List<Dictionary> list2 = dictionaryRepository.findByPidAndCode(q.getPid(),q.getName());
			if(list2.size()>0)return MessageHandler.createFailedVo("同级下标签编码不可重复");
			dictionaryRepository.save(q);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
}
