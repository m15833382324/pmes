package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Const;


@Controller
public class LoginController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(User user, HttpServletRequest req) {
		// 校验用户名和密码
		Map<String,Object> jsonMap =new HashMap<String,Object>();
		User loginUser = userService.login(user);
		HttpSession session = req.getSession(true);
		if(loginUser == null){
			jsonMap.put("flag",false);
			jsonMap.put("msg", "用户名或密码错误,请重新登录");
		}else{
			session.setAttribute(Const.LOGIN_USER, loginUser);
			jsonMap.put("flag",true);
			jsonMap.put("url","html/main.html");
		}
		// 返回
		return jsonMap;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest req) {
		String contextPath=req.getContextPath();
		// 校验用户名和密码
		Map<String,Object> jsonMap =new HashMap<String,Object>();
		HttpSession session = req.getSession(true);
		
		session.removeAttribute(Const.LOGIN_USER);
		
		jsonMap.put("flag",true);
		jsonMap.put("url",contextPath);
		// 返回
		return jsonMap;
	}
}
