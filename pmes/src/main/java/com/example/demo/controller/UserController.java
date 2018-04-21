package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.vo.Page;
import com.example.demo.vo.RequestParameter;
import com.example.demo.vo.Result;


/**
 * 用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	public UserController() {

	}

	/**
	 * @param user
	 * @return String
	 */
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest req) {
		// 校验用户名和密码
		User loginUser = userService.login(user);
		// 如果登录成功,将登录用户的信息保存在Session中.
		HttpSession session = req.getSession(true);
		if(loginUser == null){
			session.setAttribute("flag", "用户名或密码错误,请重新登录");
			return "redirect:http://127.0.0.1:8080/pmes";
			
		}
		session.setAttribute("loginUser", loginUser);
		
		// 返回
		return "main";
	}

	/**
	 * 用户维护功能,首页面跳转
	 * 
	 * @return String
	 */
	@RequestMapping("/index")
	public String toIndex() {
		return "user/index";
	}

	/**
	 * 用户分页查询方法 AJAX访问 返回对象需要进行JSON数据转换
	 * 
	 * @param pageno
	 * @param pagesize
	 * @return Object
	 */
	@RequestMapping("/getUsersByPage")
	@ResponseBody
	public Object getUsersByPage(
			@RequestParam("page")int pageno, 
			@RequestParam("rows")int pagesize) {
		// 分页查询
		Page<User> result = userService.getUsersByPage(pageno, pagesize);
		
		return result;
	}

	/**
	 * 新增用户页面跳转
	 * 
	 * @return String
	 */
	@RequestMapping("/toAdd")
	public String toAddUser() {
		
		return "user/add";
	}

	
	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return Object
	 */
	@RequestMapping("/addUser")
	@ResponseBody
	public Object addUser(User user) throws Exception {
		// Thread.sleep(1000);
//		System.out.println(user);
		Result result = new Result();
		try{
			// 处理请求数据.
			int count = userService.addUser(user);
			
			result.setSuccess(count == 1);
			/*if(result.isSuccess()){
				// 发送邮件通知
				// 1. 设定收件人,抄送,暗送,邮件内容
				// 邮件收件人
				mailMessage.setTo(user.getEmail());
				// 邮件内容
				mailMessage.setText("用户 " + user.getUsername() + " , 您的帐号已经开通"
						+ ", 登录名为 : " + user.getLoginname() + 
						" ; 登录密码为 : " + user.getLoginpswd() + 
						". 请尽快登录系统并修改密码!");
				
				// 2. 使用邮件发送工具,发送邮件
				mailSender.send(mailMessage);
			}*/
		}catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			
		}
		
		return result;
	}
	
	/**
	 * 查询当前级别用户的上级数据
	 */
	@RequestMapping("/getManagers")
	@ResponseBody
	public Object getManagers(String level){
		List<User> managers = userService.getManagers(level);
		return managers;
	}

	/**
	 * 删除用户,服务方法参数使用自定义类型
	 */
	@RequestMapping("/deleteUsersByParams")
	@ResponseBody
	public Object deleteUsers(RequestParameter params) {
		Result r = new Result();
		
		try{
			userService.deleteUsers(params);
			
			r.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
	
	/**
	 * 删除用户
	 * 
	 * @param ids
	 * @return Object
	 */
	@RequestMapping("/deleteUsers")
	@ResponseBody
	public Object deleteUsers(String[] ids) {
		Result r = new Result();
		try{
			userService.deleteUsers(ids);
			
			r.setSuccess(true);
			
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
			
		}
		return r;
	}

	/**
	 * 修改用户页面跳转 需要根据ID查询用户并展示
	 * 
	 * @param id
	 *            - 要修改的用户的ID
	 * @return String
	 */
	@RequestMapping("/toEditUser")
	public String toModifyUser(Integer id, HttpServletRequest req) {
		User user = userService.getUserById4Modify(id);
		
		req.setAttribute("editUser", user);
		
		return "user/edit";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return Object
	 */
	@RequestMapping("/modifyUser")
	@ResponseBody
	public Object modifyUser(User user) {
		Result r = new Result();
		try{
			userService.modifyUser(user);
			r.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
}






