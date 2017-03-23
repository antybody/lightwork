package com.baosight.iwater.system.organization.dao;

import java.util.List;
import java.util.Map;

import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.menu.pojo.Menu;
import com.baosight.iwater.system.organization.pojo.Organization;

public interface OrganizationMapper {
	/**
	 * 根据主键 删除
	 * @param id
	 * @return  执行结果
	 */
	int deleteByPrimaryKey(Organization org);
	
	/**
	 * 添加 
	 * @param org
	 * @return  执行结果
	 */
	int insert(Organization org);
	
	/**
	 * 根据主键   查询
	 * @param id
	 * @return  查询结果
	 */
	Organization selectByPrimaryKey(String id);
	
	/**
	 * 根据主键 修改
	 * @param org
	 * @return  执行结果
	 */
	int updateByPrimaryKey(Organization org);
	
	/**
	 * 唯一性校验
	 * @param org
	 * @return
	 */
	int checkOnly(Organization org);
	
	/**
	 * 查询  分页
	 * @param map  查询条件  分页条件
	 * @return
	 */
	List<Organization> findAll(Map<String,Object> map);
	
	/**
	 * 条件分页查询 oracle
	 * @param map
	 * @return
	 */
	List<Organization> findAllOrcl(Map<String,Object> map);
	
	/**
	 * 条件分页查询  mysql
	 * @param map
	 * @return
	 */
	List<Organization> findAllSql(Map<String,Object> map);
	
	/**
	 * 查询总记录数   供分页使用   
	 * @param map   查询条件
	 * @return
	 */
	int findAllSize(Map<String,Object> map);
	/**
	 * 查询所有节点   供下拉框使用
	 * @param map   
	 * @return
	 */
	List<Map<String, String>> findAllItem();
	List<Organization> findAllItem2();

	
	/**
	 * 查询所有  不分页
	 * @return
	 */
	List<Organization> selectAll();
}
