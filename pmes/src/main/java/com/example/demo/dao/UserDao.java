package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
import com.example.demo.vo.RequestParameter;

//@Mapper
@Mapper
public interface UserDao {

	/**
	 * @param user
	 * @return com.bjpowernode.pmes.entity.User
	 */
	public User getUserByLoginnameAndLoginpswd(User user);

	/**
	 * 分页查询用户
	 * 
	 * @param params
	 * @return java.util.List
	 */
	public java.util.List<User> getUsersByPage(java.util.Map<String, Object> params);

	/**
	 * 查询所有用户总数量
	 * 
	 * @return int
	 */
	public int getUsersCount();

	/**
	 * @param user
	 * @return int
	 */
	public int insertUser(User user);

	/**
	 * 删除用户, 使用delete from t_user where id in ()
	 * 
	 * @param params
	 *            - Map中key值为ids,代表要循环的数组 value是数组对象.数组中保存要删除的用户ID
	 * @return Integer
	 */
	public Integer deleteUsers(java.util.Map params);

	/**
	 * @param id
	 * @return com.bjpowernode.pmes.entity.User
	 */
	public User getUserById(Integer id);

	/**
	 * @param user
	 * @return int
	 */
	public int updateUser(User user);

	public List<User> selectManagersByLevel(String levelParam);

	public int deleteUsersByParam(RequestParameter params);
	/**
	 * 根据登录名称查询用户;
	 * @param username
	 * @return
	 */
	public User findUserByUserName(String username);
}
