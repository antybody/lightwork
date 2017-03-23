package com.baosight.iwater.system.login.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ILoginService {
	/**
	 * 登录验证
	 * @param request
	 * @return
	 */
	String selectByUserCode(HttpServletRequest request);
	
	/**
	 * 查询访问路径 对应的角色
	 * @return
	 */
	List<List<String>> selRolePower();
	
	/**
	 * 得到用户的菜单
	 * @param request
	 * @return
	 */
	String getMenu(HttpServletRequest request);
}
