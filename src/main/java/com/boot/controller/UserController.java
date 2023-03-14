package com.boot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.entity.User;
import com.boot.repository.UserRepository;
import com.boot.util.PageRequestHelper;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
/**
 * 用户控制器
 */
@Controller
@RequestMapping("/private/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@ResponseBody
	@RequestMapping("/list")
	public  BaseMessage<?>  userList(String username, HttpServletRequest request, HttpServletResponse response) {
		try {
			Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
			Specification<User> spec = new Specification<User>() {
				private static final long serialVersionUID = 3348042767886904924L;
				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					if (StringUtil.isNotBlank(username)) {
						list.add(cb.like(root.get("username"), "%" + username + "%"));
					}
					Predicate[] p2 = new Predicate[list.size()];
					query.where(cb.and(list.toArray(p2)));
					return query.getRestriction();
				}
			};
			Page<User> pageList = userRepository.findAll(spec, pageable);
			return MessageHandler.createSuccessVo(pageList.getContent(),"操作成功",
					(int) pageList.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("/dell")
	public  BaseMessage<?>  dell(Integer id, HttpServletRequest request, HttpServletResponse response) {
		try {
			userRepository.deleteById(id);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("/save")
	public  BaseMessage<?>  save(User u, HttpServletRequest request, HttpServletResponse response) {
		try {
			userRepository.save(u);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	/**
	 * 修改密码 
	 */
	@ResponseBody
	@RequestMapping("/updatePassword")
	public  BaseMessage<?> updatePassword(String old_password,String new_password,HttpServletRequest request, HttpServletResponse response) {
		try {
			User u = userRepository.findById(1).get();
			if(u==null) return MessageHandler.createFailedVo("操作失败，用户不存在");
			if(!u.getPassword().equals(old_password)) return MessageHandler.createFailedVo("操作失败，旧的密码不正确");
			u.setPassword(new_password);
			userRepository.save(u);
			return MessageHandler.createSuccessVo("操作成功，请重新登录");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
}
