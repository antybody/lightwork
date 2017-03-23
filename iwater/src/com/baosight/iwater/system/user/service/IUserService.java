package com.baosight.iwater.system.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baosight.iwater.system.user.pojo.RelUserOrg;

public interface IUserService {
	/**
	 * 根据主键删除 用户
	 * @param request
	 * @return
	 */
	String deleteByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 添加 用户
	 * @param request
	 * @return
	 */
	String insert(HttpServletRequest request);
	
	
	
	/**
	 * 根据主键查询 用户
	 * @param request
	 * @return
	 */
	String selectByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 根据主键修改用户
	 * @param request
	 * @return
	 */
	String updateByPrimaryKey(HttpServletRequest request);
	
	/**
	 * 查询全部用户   分页
	 * @param request
	 * @return
	 */
	String findAll(HttpServletRequest request);
	
	/**
	 * 唯一性验证
	 * @param request
	 * @return
	 */
	String checkOnly(HttpServletRequest request);
}
