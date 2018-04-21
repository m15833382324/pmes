package com.example.demo.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.OperateLog;
import com.example.demo.entity.User;
import com.example.demo.service.OperateLogService;
import com.example.demo.util.Const;
import com.example.demo.util.CusAccessObjectUtil;
import com.example.demo.util.DateFormatUtil;

@Aspect
@Component
public class LoginAop {
	private Logger log = Logger.getLogger(this.getClass());
	
	
	@Autowired
	private  HttpServletRequest request;
	@Autowired
	private OperateLogService opreate;
	
	@AfterReturning("execution(* com.example.demo.controller.LoginController.*(..))")  
	public void loginAop(JoinPoint joinPoint){
		insertLog(joinPoint);
	}
	@Before("execution(* com.example.demo.controller.LoginController.*(..))")
	public void logoutAop(JoinPoint joinPoint){
		insertLog(joinPoint);
	}
	
	private void insertLog(JoinPoint joinPoint){
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(Const.LOGIN_USER);
		if(user==null){
			return;
		}
		
		String operateip = CusAccessObjectUtil.getIP(request);
		String methodName = joinPoint.getSignature().getName();
		log.info("登入登出方法："+methodName);
		if(methodName.equals("login")){
			OperateLog operatelog = new OperateLog();
			String operatenode = "登入登出操作";
			String operatedate = DateFormatUtil.getCurrentDateStr();
			String operatemodel = methodName;
			String operatetype = "登录";
			operatelog.setLoginname(user.getLoginname());
			operatelog.setOperatedate(operatedate);
			operatelog.setOperatenode(operatenode);
			operatelog.setOperatemodel(operatemodel);
			operatelog.setOperatetype(operatetype);
			operatelog.setUserid(user.getId());
			operatelog.setOperateip(operateip);
			opreate.insertLog(operatelog);
			log.info("用户登入");
		}else if(methodName.equals("logout")){
			OperateLog operatelog = new OperateLog();
			String operatenode = "登入登出操作";
			String operatedate = DateFormatUtil.getCurrentDateStr();
			String operatemodel = methodName;
			String operatetype = "登出";
			operatelog.setLoginname(user.getLoginname());
			operatelog.setOperatedate(operatedate);
			operatelog.setOperatenode(operatenode);
			operatelog.setOperatemodel(operatemodel);
			operatelog.setOperatetype(operatetype);
			operatelog.setUserid(user.getId());
			operatelog.setOperateip(operateip);
			opreate.insertLog(operatelog);
			log.info("用户登出");
		}
	}
}
