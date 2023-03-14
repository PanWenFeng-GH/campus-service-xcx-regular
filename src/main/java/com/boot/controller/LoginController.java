package com.boot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.boot.entity.User;
import com.boot.repository.UserRepository;
import com.boot.util.FilesUtils;
import com.boot.util.JwtTokenUtil;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import com.boot.vo.Token;
/**
 * 登录相关
 */
@Controller
public class LoginController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("")
	public String test() {
		return "login";
	}
	
	/**
	 * 后台
	 */
	@GetMapping("/login")
	public String main() {
		return "login";
	}
	
	@GetMapping("/admin")
	public String admin() {
		
		return "redirect:login";
	}
	
	@ResponseBody
	@PostMapping("/login")
	public BaseMessage<?>  loginP(String username,String password) throws Exception {
		if (StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
			return MessageHandler.createFailedVo("用户或密码不能为空！");
		}
		User user = userRepository.findByUserName(username);
		Map<String, Object> map2 = new HashMap<String, Object>();
		if (user != null) {
			if (password.equals(user.getPassword())) {
				String token = JwtTokenUtil.createSign(user.getId(),0,user.getUsername(), user.getName(),user.getId()+"");
				map2.put("token", token);
				map2.put("username", user.getUsername());
				map2.put("type", user.getType());
				return MessageHandler.createSuccessVo(map2, "登录成功");
			} else {
				return MessageHandler.createFailedVo("用户密码不正确");
			}
		} else {
			return MessageHandler.createFailedVo("用户不存在");
		}
	}
	@ResponseBody
	@RequestMapping(value = "/login/checkToken")
	public BaseMessage<?> checkToken(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		try {
			Token to = JwtTokenUtil.getBusinesserToken();
			if (to != null) {
				User user = userRepository.findById(to.getBusinessId()).get();
				if (user != null) {
					return MessageHandler.createSuccessVo("token验证成功！");
				}
			} else {
				return MessageHandler.createFailedVo("token失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取文件
	 * 图片
	 */
	@RequestMapping("getFile/{name}")
	public void getFile(HttpServletResponse response, @PathVariable("name") String name) {
		try {
			FilesUtils.fileIn(response, name, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 上传文件
	 */
	@ResponseBody
	@RequestMapping("upload")
	public Object upload(HttpServletRequest request, MultipartFile file) {
		String name = "";
		try {
			//String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			String fileName = file.getOriginalFilename();
			//String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			//name = uuid + fileTyle;
			name = fileName;
			FilesUtils.fileOut(request, file, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createSuccessVo(name, "上传成功");
	}
	
}
