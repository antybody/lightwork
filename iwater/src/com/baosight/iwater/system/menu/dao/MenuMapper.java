package com.baosight.iwater.system.menu.dao;

import java.util.List;
import java.util.Map;

import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.menu.pojo.Menu;
import com.baosight.iwater.system.menu.pojo.Power;
import com.baosight.iwater.system.user.pojo.User;

public interface MenuMapper {
	/**
	 * 删除前查询其关联数据数量 menu
	 * @param ui_id
	 * @return
	 */
	int delQueryMenu(String ui_id);
	
	/**
	 * 删除前查询其关联数据数量 power
	 * @param ui_id
	 * @return
	 */
	int delQueryPower(String ui_id);
	
	/**
	 * 根据主键 删除
	 * @param id
	 * @return  执行结果
	 */
	int deleteByPrimaryKey(Menu menu);
	
	/**
	 * 删除菜单时 同步删除资源中的菜单
	 * @param power
	 * @return
	 */
	int deletePower(Power power);
	
	/**
	 * 添加 
	 * @param org
	 * @return  执行结果
	 */
	int insert(Menu menu);
	
	/**
	 * 添加菜单时 同步添加资源
	 * @param power
	 * @return
	 */
	int insertPower(Power power);
	
	/**
	 * 根据主键   查询
	 * @param id
	 * @return  查询结果
	 */
	Menu selectByPrimaryKey(String id);
	
	/**
	 * 返回  子菜单的个数+2
	 * @param parent_menu
	 * @return
	 */
	int SonMenuNum();
	
	/**
	 * 根据主键 修改
	 * @param org
	 * @return  执行结果
	 */
	int updateByPrimaryKey(Menu menu);
	
	/**
	 * 修改菜单时  同步修改资源中的菜单
	 * @param power
	 * @return
	 */
	int updatePower(Power power);
	
	/**
	 * 查询  分页  oracle
	 * @param map  查询条件  分页条件
	 * @return
	 */
	List<Menu> findAllOrcl(Map<String,Object> map);
	
	/**
	 * 条件查询 分页  mysql
	 * @param map
	 * @return
	 */
	List<Menu> findAllSql(Map<String,Object> map); 
	
	/**
	 * 查询总记录数   供分页使用
	 * @param map   查询条件
	 * @return
	 */
	int findAllSize(Map<String,Object> map);
	
	
	/**
	 * 查询所有的父菜单(下拉选择使用)
	 * @return
	 */
	List<Menu> findParentMenu(Menu menu);
	
	/**
	 * 唯一性校验 菜单  新增 或 修改使用  (根据ui_id)
	 * @param request
	 * @return
	 */
	int checkOnly(Menu menu);
	
	/**
	 * 唯一性校验   权限资源
	 * @param power
	 * @return
	 */
	int checkOnlyPower(Power power);
}
