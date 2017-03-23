package com.baosight.iwater.system.log.dao;

import java.util.List;
import java.util.Map;

import com.baosight.iwater.system.log.pojo.Log;

public interface logMapper {
	
	/**
	 * 查询所有  分页
	 * @param pageInfo
	 * @return  查询结果
	 */
	List<Log> findAll(Map<String,Object> map);
	
	/**
	 * 条件分页查询 mysql
	 * @param map
	 * @return
	 */
	List<Log> findAllSql(Map<String,Object> map);
	
	/**
	 * 条件分页查询 oracle
	 * @param map
	 * @return
	 */
	List<Log> findAllOrcl(Map<String,Object> map);
	
	/**
	 * 查询总记录数   供分页使用
	 * @return  查询结果
	 */
	int findAllSize(Map<String,Object> map);
	
	
	/**
	 * 新增日志
	 * @param log
	 * @return 执行结果
	 */
	int insert(Log log);

	/**
	 * 删除日志
	 * @param log
	 * @return 执行结果
	 */
	int del(Log log);

	/**
	 * 导出日志查询 mysql
	 * @param map
	 * @return
	 */
	List<Map<String,String>> downloadSql(Map<String, Object> map);
	
	/**
	 * 导出日志查询  oracle
	 * @param map
	 * @return
	 */
	List<Map<String,String>> downloadOrcl(Map<String, Object> map);
	
	/**
	 * 导出全部日志查询
	 * @param map
	 * @return
	 */
	List<Map<String,String>> download(Map<String, Object> map);
	
	/**
	 * 根据主键查询日志
	 * @param string
	 * @return
	 */
	Log selectByPrimaryKey(String string);
}
