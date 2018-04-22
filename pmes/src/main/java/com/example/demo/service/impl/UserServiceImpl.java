
package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.DateFormatUtil;
import com.example.demo.vo.Page;
import com.example.demo.vo.RequestParameter;


@Service("userService")
public class UserServiceImpl implements UserService {

	/**
	 * 用于访问数据库
	 */
	@Resource
	private UserDao userDao;

	public UserServiceImpl() {

	}

	/**
	 * @param user
	 * @return com.bjpowernode.pmes.entity.User
	 */
	public User login(User user) {
		User loginUser = userDao.getUserByLoginnameAndLoginpswd(user);
		return loginUser;
	}

	/**
	 * @param pageno
	 * @param pagesize
	 * @return com.bjpowernode.pmes.vo.Page
	 */
	public Page<User> getUsersByPage(int pageno, int pagesize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startIndex", (pageno - 1) * pagesize);
		params.put("pagesize", pagesize);
		// 分页查询数据
		List<User> dataList = userDao.getUsersByPage(params);
		// 查询总计有多少用户数量
		int total = userDao.getUsersCount();
		
		Page<User> result = new Page<User>();
		result.setDataList(dataList);
		result.setTotal(total);
		
		return result;
	}

	/**
	 * @param user
	 * @return Integer
	 */
	public int addUser(User user) {
		// yyyy-MM-dd HH:mm:ss   new Date()
		user.setCreatetime(DateFormatUtil.getCurrentDateStr());
		
		int count = userDao.insertUser(user);
		if(count != 1){
			throw new RuntimeException("新增用户错误");
		}
		return count;
	}
	
	/**
	 * 删除用户
	 */
	public int deleteUsers(RequestParameter params){
		int count = 0;
		
		try{
			count = userDao.deleteUsersByParam(params);
			if(count != params.getUsers().size()){
				throw new RuntimeException();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return count;
	}

	/**
	 * @param ids
	 * @return int
	 */
	public int deleteUsers(String[] ids){
		int count = 0;
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			Integer[] paramIds = new Integer[ids.length];
			for(int i = 0; i < ids.length; i++){
				Integer id = Integer.parseInt(ids[i]);
				paramIds[i] = id;
			}
			params.put("ids", paramIds);
			
			count = userDao.deleteUsers(params);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(count != ids.length){
			throw new RuntimeException("删除用户数量于请求不一致");
		}
		
		return count;
	}

	@Override
	public List<User> getManagers(String level) {
		String levelParam = "";
		if(null == level){
			levelParam = "组长";
		}else if("职员".equals(level) || "1".equals(level)){
			levelParam = "组长";
		}else if("组长".equals(level) || "2".equals(level)){
			levelParam = "经理";
		}else{
			return null;
		}
		
		List<User> managers = userDao.selectManagersByLevel(levelParam);
		return managers;
	}

	/**
	 * @param id
	 * @return com.bjpowernode.pmes.entity.User
	 */
	public User getUserById4Modify(Integer id) {
		User user = userDao.getUserById(id);
		return user;
	}

	/**
	 * @param user
	 * @return int
	 */
	public int modifyUser(User user) {
		int count = userDao.updateUser(user);
		if(count != 1){
			throw new RuntimeException("更新失败, 更新记录数为 : " + count + " 条");
		}
		return count;
	}

	@Override
	public User findUserByUserName(String username) {
		// TODO Auto-generated method stub
		return userDao.findUserByUserName(username);
	}
}







