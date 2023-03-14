package com.boot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.entity.TData;
import com.boot.entity.TWechatUser;
import com.boot.repository.TDataRepository;
import com.boot.repository.TWechatUserRepository;
import com.boot.service.DataService;
import com.boot.util.PageRequestHelper;
import com.boot.util.StatusUtil;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/private/data")
public class TDataController extends BaseController {

	@Autowired
	private TDataRepository dataRepository;
	@Autowired
	private DataService dataService;
	@Autowired
	private TWechatUserRepository wechatUserRepository;
	@ResponseBody
	@PostMapping("/list")
	public  BaseMessage<?> list(String name,String type, HttpServletRequest request, HttpServletResponse response) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
		Page<TData> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		List<TData> list = dataService.pagelist(name,type);
		return MessageHandler.createSuccessVo(list,"操作成功",(int)page.getTotal());
	}
	/*@ResponseBody
	@RequestMapping("/list")
	public  BaseMessage<?>  userList(String username, HttpServletRequest request, HttpServletResponse response) {
		try {
			Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
			Specification<TData> spec = new Specification<TData>() {
				private static final long serialVersionUID = 3348042767886904924L;
				@Override
				public Predicate toPredicate(Root<TData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					if (StringUtil.isNotBlank(username)) {
						list.add(cb.like(root.get("username"), "%" + username + "%"));
					}
					Predicate[] p2 = new Predicate[list.size()];
					query.where(cb.and(list.toArray(p2)));
					return query.getRestriction();
				}
			};
			Page<TData> pageList = dataRepository.findAll(spec, pageable);
			return MessageHandler.createSuccessVo(pageList.getContent(),"操作成功",
					(int) pageList.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}*/
	
	@RequestMapping("/edit")
	public String edit(String id,HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		try {
			TData d = null;
			if(id!=null) {
				d = dataRepository.getOne(id);
			}
			model.put("data", d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "page/data_edit1";
	}
	@RequestMapping("/edit3")
	public String edit3(String id,HttpServletRequest request, HttpServletResponse response,ModelMap model) {
		try {
			TData d = null;
			if(id!=null) {
				d = dataRepository.getOne(id);
			}
			model.put("data", d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "page/data_edit3";
	}
	@ResponseBody
	@RequestMapping("/dell")
	public  BaseMessage<?>  dell(String id, HttpServletRequest request, HttpServletResponse response) {
		try {
			dataRepository.deleteById(id);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("/save")
	public  BaseMessage<?>  save(TData u, HttpServletRequest request, HttpServletResponse response) {
		try {
			if(StringUtil.isNotBlank(u.getId())) {
				TData old = dataRepository.getOne(u.getId());
				old.setValue(u.getValue());
				old.setValue2(u.getValue2());
				u = old;
			}
			//TODO 数据状态
			TWechatUser user = wechatUserRepository.getOne(u.getWechatId());
			u.setStatus(StatusUtil.getStatus(u,user));
			dataRepository.save(u);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
}
