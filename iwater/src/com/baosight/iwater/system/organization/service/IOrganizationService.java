package com.baosight.iwater.system.organization.service;

import javax.servlet.http.HttpServletRequest;

public interface IOrganizationService {
	/**
	 * 根据主键删除 组织机构
	 * @param request
	 * @return
	 */
	String deleteByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 添加 组织机构
	 * @param request
	 * @return
	 */
	String insert(HttpServletRequest request);
	
	/**
	 * 根据主键查询 组织机构
	 * @param request
	 * @return
	 */
	String selectByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 根据主键修改组织机构
	 * @param request
	 * @return
	 */
	String updateByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 查询全部组织机构   分页
	 * @param request
	 * @return
	 */
	String findAll(HttpServletRequest request);

	/**
	 * 查询父节点下拉框数据
	 * @param request
	 * @return
	 */
	String orgItem(HttpServletRequest request);

	String listZtree(HttpServletRequest request);

	
	/**
	 * 查询所有组织机构 不分页
	 * @param request
	 * @return
	 */
	String selectAll(HttpServletRequest request);
	
	/**
	 * 唯一性校验
	 * @param request
	 * @return
	 */
	String checkOnly(HttpServletRequest request);

}
