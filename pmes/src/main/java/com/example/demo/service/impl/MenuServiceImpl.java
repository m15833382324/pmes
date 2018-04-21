package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.dao.MenuDao;
import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;


@Service("menuService")
public class MenuServiceImpl implements MenuService {
	// 访问数据库, 通过DAO访问
	@Resource
	private MenuDao menuDao;
	
	public List<Menu> init(){
		List<Menu> roots = menuDao.getAllRootMenus(0);
		return roots;
	}
	
	public List<Menu> initRootMenu(){
		List<Menu> menus = menuDao.getAllMenus();
		// 集合中只保存根节点对象. 并且这个根节点对象必须维护关系.
		List<Menu> result = new ArrayList<Menu>();
		// 创建临时集合,key是菜单对象的ID,value是对应的菜单对象
		Map<Integer, Menu> temp = new HashMap<Integer, Menu>();
		for(Menu m : menus){
			temp.put(m.getId(), m);
		}
		
		// 循环查询结果集合.将每次循环的变量作为子节点. 在Map中查询父节点
		for(Menu child : menus){
			Integer parentId = child.getPid();
			Menu parent = temp.get(parentId);
			if(parent == null){
				result.add(child);
				continue;
			}
			if(parent.getChildren() == null){
				parent.setChildren(new ArrayList<Menu>());
			}
			parent.getChildren().add(child);
		}
		
		return result;
	}
}






