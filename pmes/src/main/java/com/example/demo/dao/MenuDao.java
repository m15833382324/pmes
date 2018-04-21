package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Menu;

//@Mapper
@Repository
public interface MenuDao {
	// 查询所有的菜单,不考虑级联查询,由内存维护对象关系.
	List<Menu> getAllMenus();
	
	// 查询所有的根节点,并级联查询所有的子节点. 根节点就是pid值为0的菜单
	List<Menu> getAllRootMenus(Integer root);
}
