package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), user.getLoginpswd());
		Map<String,Object> jsonMap =new HashMap<String,Object>();
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		jsonMap.put("flag",true);
		jsonMap.put("url","html/main.html");
//		}
		// 返回
		return jsonMap;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest req) {
		String contextPath=req.getContextPath();
		// 校验用户名和密码
		Map<String,Object> jsonMap =new HashMap<String,Object>();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		subject.getSession().removeAttribute(Const.LOGIN_USER);
		jsonMap.put("flag",true);
		jsonMap.put("url",contextPath);
		// 返回
		return jsonMap;
	}
}
