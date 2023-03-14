package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.entity.Menu;
import com.boot.repository.MenuRepository;
import com.boot.util.JwtTokenUtil;
@Controller
public class ManageController {
	@Autowired
	private MenuRepository menuRepository;
	
	@RequestMapping("/index")
	public String index(ModelMap model) {
		List<Menu> menuList = menuRepository.findTopByServerAndUserId(JwtTokenUtil.getBusinessId());
		model.put("menuList",menuList);
		return "index";
	}
	@RequestMapping("/private/home")
	public String home(ModelMap model) {
		return "home";
	}
}
