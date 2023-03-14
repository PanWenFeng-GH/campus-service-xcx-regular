package com.boot.intecepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.boot.repository.TWechatUserRepository;
import com.boot.util.JwtTokenUtil;
import com.boot.vo.MessageHandler;

@Component
public class ApiIntecepter extends HandlerInterceptorAdapter {
	public static final String TOKEN_KEY = "token";
	@Autowired
	private TWechatUserRepository wechatUserRepository;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean result = true;
		System.out.println("===api====");
		/*try {
			String token = JwtTokenUtil.getTokenString();
			System.out.println("===token===="+token);
			result = JwtTokenUtil.verify(token);
			if(!result) throw new  Exception("操作失败：token无效");
			boolean b = wechatUserRepository.existsById(JwtTokenUtil.getBusinesserToken().getUserId());
			if(!b) result = false;
			if(!result) throw new  Exception("操作失败：暂无权限");
		}catch (Exception e) {
			e.printStackTrace();
			result = false;
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json,charset=utf-8");
			response.getWriter().print(JSON.toJSONString(MessageHandler.createFailedVo(e.getMessage())));
		}*/
		return result;
	}
}
