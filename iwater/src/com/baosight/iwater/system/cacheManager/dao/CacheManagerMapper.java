package com.baosight.iwater.system.cacheManager.dao;

import java.util.List;
import java.util.Map;

import com.baosight.iwater.system.menu.pojo.Menu;


public interface CacheManagerMapper {
    List<Map<String,String>> findAllFlushCache();
    List<Map<String,String>> findAll();  
    int save(Map<String, String> map);   
    int updateById(Map<String, String> map);
    int deleteById(Map<String, String> map);  
    List<Map<String,String>> selectByPrimaryKey(String string);
	List<Map<String, String>> findAllFlushCache(Map<String, Object> map);
	List<Map<String, String>> findAllFlushCacheTest(Map<String, Object> map);
	
	List<Map<String,String>> orclFindAllFlushCacheTest(Map<String,Object> map);  //oracle查询分页
	
	int findAllSize(Map<String,Object> map);
}