package com.baosight.iwater.system.user.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.user.pojo.RelUserOrg;
import com.baosight.iwater.system.user.pojo.RelUserRole;
import com.baosight.iwater.system.user.pojo.User;

public interface UserMapper {
	/**
	 * 根据主键 删除
	 * @param id
	 * @return  执行结果
	 */
	int deleteByPrimaryKey(User user);
	
	/**
	 * 添加 
	 * @param org
	 * @return  执行结果
	 */
	int insert(User user);
	
	/**
	 * 查询用户 所属组织机构
	 * @param user_code
	 * @return
	 */
	List<RelUserOrg> sel_user_org(String user_code);

	/**
	 * 查询用户 角色
	 * @param user_code
	 * @return
	 */
	List<RelUserRole> sel_user_role(String user_code);
	
	/**
	 * 添加用户组织机构关系
	 * @param relUserOrg
	 * @return
	 */
	int insertUserOrg(RelUserOrg relUserOrg);
	
	/**
	 * 添加用户角色关系
	 * @param relUserRole
	 * @return
	 */
	int insertUserRole(RelUserRole relUserRole);
	
	/**
	 * 删除角色关系
	 * @param relUserRole
	 * @return
	 */
	int del_user_role(RelUserRole relUserRole);
	
	/**
	 * 删除组织机构关系
	 * @param relUserRole
	 * @return
	 */
	int del_user_org(RelUserOrg relUserOrg);
	
	/**
	 * 根据主键   查询
	 * @param id
	 * @return  查询结果
	 */
	User selectByPrimaryKey(String id);
	
	/**
	 * 根据主键 修改
	 * @param org
	 * @return  执行结果
	 */
	int updateByPrimaryKey(User user);
	
	/**
	 * 条件查询分页  mysql
	 * @param map
	 * @return
	 */
	List<User> findAllSql(Map<String,Object> map);
	
	/**
	 * 条件查询分页  oracle
	 * @param map
	 * @return
	 */
	List<User> findAllOrcl(Map<String,Object> map);
	
	/**
	 * 查询总记录数   供分页使用
	 * @return
	 */
	int findAllSize(Map<String,Object> map);
	
	/**
	 * 唯一性验证  新增 或 修改使用  (根据ui_id)
	 * @param request
	 * @return
	 */
	int checkOnly(User user);
}