package com.baosight.iwater.system.role.dao;

import java.util.List;
import java.util.Map;

import com.baosight.iwater.system.role.pojo.Power;
import com.baosight.iwater.system.role.pojo.RelRolePower;
import com.baosight.iwater.system.role.pojo.Role;
import com.baosight.iwater.system.role.pojo.User;

public interface RoleMapper {
	/**
	 * 根据主键 删除
	 * @param id
	 * @return  执行结果
	 */
	int deleteByPrimaryKey(Role role);
	
	/**
	 * 添加 
	 * @param org
	 * @return  执行结果
	 */
	int insert(Role role);
	
	/**
	 * 添加 角色权限关系
	 * @param rrp
	 * @return
	 */
	int insertRolePower(RelRolePower rrp);
	
	/**
	 * 删除 某角色的所有权限关系
	 * @param rrp
	 * @return
	 */
	int deleteRolePower(RelRolePower rrp);
	
	/**
	 * 根据角色主键  查询某一角色的所有权限关系
	 * @param ui_id
	 * @return
	 */
	List<RelRolePower> selectRolePower(String ui_id);
	
	/**
	 * 根据主键   查询
	 * @param id
	 * @return  查询结果
	 */
	Role selectByPrimaryKey(String id);
	
	/**
	 * 根据主键 修改
	 * @param org
	 * @return  执行结果
	 */
	int updateByPrimaryKey(Role role);
	
	/**
	 * 条件分页查询  mysql
	 * @param map
	 * @return
	 */
	List<Role> findAllSql(Map<String,Object> map);
	
	/**
	 * 条件分页查询  oracle
	 * @param map
	 * @return
	 */
	List<Role> findAllOrcl(Map<String,Object> map);
	
	/**
	 * 查询所有 不分页
	 * @param map
	 * @return
	 */
	List<Role> findAll();
	
	/**
	 * 查询总记录数   供分页使用
	 * @return
	 */
	int findAllSize(Map<String,Object> map);
	
	/**
	 * 查询角色 用户 
	 * @param ui_id
	 * @return
	 */
	List<User> sel_role_user(String role_code);
	
	/**
	 * 查询角色 权限(这个是用户角色浏览页面使用)
	 * @param role_code
	 * @return
	 */
	List<Power> sel_role_power(String role_code);
	
	/**
	 * 唯一性验证  新增 或 修改使用  (根据ui_id)
	 * @param request
	 * @return
	 */
	int checkOnly(Role role);
}
