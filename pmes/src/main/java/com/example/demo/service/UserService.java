package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;
import com.example.demo.vo.Page;
import com.example.demo.vo.RequestParameter;


public interface UserService {

	/**
	 * 用户登录, 如果返回值为null,代表当前登录用户不存在,或密码错误
	 * 
	 * @param user
	 * @return com.bjpowernode.pmes.entity.User
	 */
	public User login(User user);

	/**
	 * 用户分页查询
	 * 
	 * @param pageno
	 * @param pagesize
	 * @return com.bjpowernode.pmes.vo.Page
	 */
	public Page<User> getUsersByPage(int pageno, int pagesize);

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return int
	 */
	public int addUser(User user);

	/**
	 * 删除用户
	 * 
	 * @param ids
	 * @return int
	 */
	public int deleteUsers(String[] ids);

	/**
	 * 根据ID查询用户,提供给修改用户使用.
	 * 
	 * @param id
	 * @return com.bjpowernode.pmes.entity.User
	 */
	public User getUserById4Modify(Integer id);

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return int
	 */
	public int modifyUser(User user);

	/**
	 * 获取上级员工信息.
	 * @param level
	 * @return
	 */
	public List<User> getManagers(String level);
	// 删除用户
	public int deleteUsers(RequestParameter params);
}
