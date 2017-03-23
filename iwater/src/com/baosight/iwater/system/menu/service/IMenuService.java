package com.baosight.iwater.system.menu.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IMenuService {
	/**
	 * 根据主键删除 菜单
	 * @param request
	 * @return
	 */
	String deleteByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 添加 菜单
	 * @param request
	 * @return
	 */
	String insert(HttpServletRequest request);
	
	/**
	 * 根据主键查询 菜单
	 * @param request
	 * @return
	 */
	String selectByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 导出菜单
	 * @param request
	 * @return
	 */
	String download(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 根据主键修改菜单
	 * @param request
	 * @return
	 */
	String updateByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 查询全部菜单   分页
	 * @param request
	 * @return
	 */
	String findAll(HttpServletRequest request);
	
	/**
	 * 查询所有的父菜单
	 * @param request
	 * @return
	 */
	String findParentMenu(HttpServletRequest request);
	
	/**
	 * properties文件中项目的前缀
	 * @param request
	 * @return
	 */
	String menuItem(HttpServletRequest request);
	
	/**
	 * 唯一性验证
	 * @param request
	 * @return
	 */
	String checkOnly(HttpServletRequest request);
}
