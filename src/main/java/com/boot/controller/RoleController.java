package com.boot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.entity.Menu;
import com.boot.entity.Role;
import com.boot.repository.MenuRepository;
import com.boot.repository.RoleRepository;
import com.boot.repository.UserRepository;
import com.boot.service.UserService;
import com.boot.util.PageRequestHelper;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
/**
 * 角色管理
 */
@Controller
@RequestMapping("/private/role")
public class RoleController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MenuRepository menuRepository;
	/**
	 * 角色
	 */
	@ResponseBody
	@RequestMapping("list")
	public BaseMessage<?> roleList(String roleName, HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		try {
			Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
			Specification<Role> spec = new Specification<Role>() {
				private static final long serialVersionUID = -2643319141958017989L;

				@Override
				public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					if (StringUtil.isNotBlank(roleName)) {
						List<Predicate> list = new ArrayList<Predicate>();
						list.add(cb.like(root.get("roleName"), "%" + roleName + "%"));
						Predicate[] p2 = new Predicate[list.size()];
						query.where(cb.and(list.toArray(p2)));
						return query.getRestriction();
					} else {
						return null;
					}
				}
			};
			Page<Role> pageList = roleRepository.findAll(spec, pageable);
			return MessageHandler.createSuccessVo(pageList.getContent(),"操作成功",
					(int) pageList.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("list/list")
	public BaseMessage<?> roleListList( HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		try {
			List<Role> list = roleRepository.findAll();
			return MessageHandler.createSuccessVo(list,"操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}

	@RequestMapping("edit")
	@ResponseBody
	public BaseMessage<?> roleEdit(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		try {
			Role role = null;
			if (id != null) {
				role = roleRepository.findById(id).get();
			}
			map.put("role", role);
			return MessageHandler.createSuccessVo(role, "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}

	@ResponseBody
	@RequestMapping("getMenu")
	public BaseMessage<?> getMenu(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		try {
			Map<String, Object> m = new HashMap<>();
			List<Menu> listMap = menuRepository.findTopAll();
			m.put("listMap", listMap);
			// 获取角色 已选菜单
			System.out.println(id);
			List<?> roleList = menuRepository.getRoleMenuById(id);
			System.out.println(roleList);
			m.put("roleList", roleList);
			return MessageHandler.createSuccessVo(m, "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}

	@ResponseBody
	@RequestMapping("save")
	public BaseMessage<?> roleAdd(Role role, String ztreeMenu, HttpServletRequest request, HttpServletResponse response,
			ModelMap map) {
		try {
			System.out.println(ztreeMenu);
			userService.roleAdd(role, ztreeMenu);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}

	@ResponseBody
	@RequestMapping("dell")
	public BaseMessage<?> roleDell(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		try {
			// 判断是否有用户绑定
			int count = userRepository.countUserRole(id);
			if (count <= 0) {
				roleRepository.deleteById(id);
				return MessageHandler.createSuccessVo("操作成功!");
			} else {
				return MessageHandler.createFailedVo("已有用户绑定当前角色");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
}
