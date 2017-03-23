package com.baosight.iwater.system.role.service;

import javax.servlet.http.HttpServletRequest;

public interface IRoleService {
	/**
	 * 根据主键删除 角色
	 * @param request
	 * @return
	 */
	String deleteByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 添加 角色
	 * @param request
	 * @return
	 */
	String insert(HttpServletRequest request);
	
	/**
	 * 根据主键查询 角色
	 * @param request
	 * @return
	 */
	String selectByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 根据主键修改角色
	 * @param request
	 * @return
	 */
	String updateByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 条件分页查询角色
	 * @param request
	 * @return
	 */
	String findAll(HttpServletRequest request);
	
	/**
	 * 查询所有角色
	 * @param request
	 * @return
	 */
	String getAll(HttpServletRequest request);
	
	/**
	 * 查询角色 所拥有的权限
	 * @param request
	 * @return
	 */
	String getRolePower(HttpServletRequest request);
	
	/**
	 * 唯一性验证
	 * @param request
	 * @return
	 */
	String checkOnly(HttpServletRequest request);
	
	/**
	 * 查询角色 用户  
	 * @param request
	 * @return
	 */
	String selRoleUser(HttpServletRequest request);
}
