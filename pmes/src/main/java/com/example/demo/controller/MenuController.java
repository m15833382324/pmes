package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;


@Controller
@RequestMapping("/menu")
public class MenuController {
	// 需要访问数据库,查询菜单数据
	// 通过Service
	@Resource
	private MenuService menuService;
	
	@RequestMapping("/init")
	@ResponseBody
	public Object init(){
		//List<Menu> roots = menuService.init();
		List<Menu> roots = menuService.initRootMenu();
		
		return roots;
	}
}









