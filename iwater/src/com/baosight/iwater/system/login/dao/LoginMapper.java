package com.baosight.iwater.system.login.dao;

import java.util.List;
import java.util.Map;

import com.baosight.iwater.system.login.pojo.Power;
import com.baosight.iwater.system.login.pojo.User;

public interface LoginMapper {
	//根据账号查询用户对象
	User selectByUserCode(String user_code);
	
	//根据账号查询用户角色
	List<String> selRoleByUserCode(String user_code);
	
	//得到所有角色对应的访问资源
	List<Power> selectRolePower();
	
	//根据路径返回对应接口的token  不是接口地址 返回 null
	String getToken(String url);
	
	//查询用户的菜单   (用户-角色-菜单)
	List<Map<String,String>> getUserMenu(String user_code);
	
	//得到所有的菜单(最高管理员角色)
	List<Map<String,String>> getAllMenu();
	
	//判断用户是否是最高管理员
	int isAdmin(String user_code);
}
