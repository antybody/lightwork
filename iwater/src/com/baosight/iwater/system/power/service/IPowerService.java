package com.baosight.iwater.system.power.service;

import javax.servlet.http.HttpServletRequest;

public interface IPowerService {
	/**
	 * 根据主键删除 权限资源
	 * @param request
	 * @return
	 */
	String deleteByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 注册权限资源
	 * @param request
	 * @return
	 */
	String insert(HttpServletRequest request);
	
	
	
	/**
	 * 根据主键查询 权限资源
	 * @param request
	 * @return
	 */
	String selectByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 根据主键修改权限资源
	 * @param request
	 * @return
	 */
	String updateByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 查询全部权限资源   分页
	 * @param request
	 * @return
	 */
	String findAll(HttpServletRequest request);
	
	/**
	 * 查询所有权限资源
	 * @param request
	 * @return
	 */
	String getAll(HttpServletRequest request);
	
	/**
	 * 唯一性验证
	 * @param request
	 * @return
	 */
	String checkOnly(HttpServletRequest request);
}
