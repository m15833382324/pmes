package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Menu;


public interface MenuService {
	// 集合中的Menu对象,一定是树状菜单中的根节点. 且会级联查询所有的子节点.
	List<Menu> init();
	//
	List<Menu> initRootMenu();
}
