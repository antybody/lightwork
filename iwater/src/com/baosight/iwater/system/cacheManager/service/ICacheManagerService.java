package com.baosight.iwater.system.cacheManager.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICacheManagerService {
	List<Map<String,String>> findAllFlushCache(String sql);
	String findAll(HttpServletRequest request);
	String findAllTest();

	int deleteById(Map<String,String> map,String sql);
	int updateById(Map<String,String> map,String sql);
	int save(Map<String,String> map,String sql); 
	int deleteById(Map<String,String> map);
	int updateById(Map<String,String> map);
	int save(Map<String,String> map);
	String selectByPrimaryKey(HttpServletRequest request);
	String download(HttpServletRequest request, HttpServletResponse response);
	List<Map<String, String>> selectListByPrimaryKey(HttpServletRequest request);
} 
