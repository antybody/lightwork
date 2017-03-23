package com.baosight.iwater.system.power.dao;

import java.util.List;
import java.util.Map;

import com.baosight.iwater.system.power.pojo.Power;

public interface PowerMapper {
	/**
	 * 根据主键 删除
	 * @param id
	 * @return  执行结果
	 */
	int deleteByPrimaryKey(Power power);
	
	/**
	 * 添加 
	 * @param org
	 * @return  执行结果
	 */
	int insert(Power power);
	
	/**
	 * 根据主键   查询
	 * @param id
	 * @return  查询结果
	 */
	Power selectByPrimaryKey(String id);
	
	/**
	 * 根据主键 修改
	 * @param org
	 * @return  执行结果
	 */
	int updateByPrimaryKey(Power power);
	
	/**
	 * 条件查询分页 mysql
	 * @param map
	 * @return  查询结果
	 */
	List<Power> findAllSql(Map<String,Object> map);
	
	/**
	 * 条件查询分页  oracle
	 * @param map
	 * @return
	 */
	List<Power> findAllOrcl(Map<String,Object> map);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<Power> findAll();
	
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
	int checkOnly(Power power);
}
