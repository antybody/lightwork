package com.baosight.iwater.system.cacheManager.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.baosight.iwater.define.CacheManager;
import com.baosight.iwater.define.SerializeUtil;
import com.baosight.iwater.system.cacheManager.dao.CacheManagerMapper;

public class Task {
	private String cachePackage;//待刷新的包名
	private String cacheRole;//角色名cache_idname
	private String cacheIdname;//
	private String cacheType;//1，刷新2，同步   
	private String cacheID;
	
	public Task(String cachePackage,String cacheRole,String cacheIdname,String cacheType,String cacheID){
		this.cachePackage=cachePackage;
		this.cacheRole=cacheRole;
		this.cacheIdname=cacheIdname;
		this.cacheType=cacheType;
		this.cacheID=cacheID;
	}
	public void show(){
		CacheManager cacheManager=new CacheManager();
		try { 
			//java反射机制
			//Class c = Class.forName(cachePackage.split("dao")[0]+"service.impl"+cachePackage.split("dao")[1].replaceAll("Mapper","ServiceImpl"));
			Class c = Class.forName("com.baosight.iwater.system.cacheManager.service.impl.CacheManagerServiceImpl");
			Object o=c.newInstance();	
		if(cacheType.equals("1")){
			   //System.out.println(cachePackage.split("dao")[0]+"service.impl"+cachePackage.split("dao")[1].replaceAll("Mapper","ServiceImpl"));
				  Method m  = c.getDeclaredMethod("findAllFlushCache",String.class);
				  Object retValue=m.invoke(o,cachePackage+"."+cacheIdname+"FlushCache");
	       
		}else if(cacheType.equals("2")){
			String key=cachePackage+"."+cacheIdname+"-"+cacheRole;
				if(cacheManager.jedis.get(key.getBytes("utf-8"))!=null){
					List<Map<String, String>> findAllList =(List<Map<String, String>>) SerializeUtil.unserialize(cacheManager.jedis.get(key.getBytes("utf-8")));
					for(int i=0;i<findAllList.size();i++){
						Map<String,String> map=findAllList.get(i);
						if(map.get("state")!=null&&map.get("state").equals("1")){//新增
							 Method m2  = c.getDeclaredMethod("save",Map.class,String.class);
							 m2.invoke(o,map,cachePackage+".save");
						}else if(map.get("state")!=null&&map.get("state").equals("2")){//删除
							 Method m3  = c.getDeclaredMethod("deleteById",Map.class,String.class);
							 m3.invoke(o,map,cachePackage+".deleteById");
						}else if(map.get("state")!=null&&map.get("state").equals("3")){//修改
							 Method m4  = c.getDeclaredMethod("updateById",Map.class,String.class);
							 m4.invoke(o,map,cachePackage+".updateById");
						}
					}
					String jsonStr=JSONArray.fromObject(findAllList).toString();
					Method m5  = c.getDeclaredMethod(cacheIdname+"FlushCache",String.class);
					m5.invoke(o,cachePackage+"."+cacheIdname+"FlushCache");
				}
			
		}
		
		String parmStr6="{\"cache_id\":\"1\",\"cache_refreshtime\":\"\"}";
		//转换为对象
		Map<String,String> map6 = JSONObject.fromObject(parmStr6);
		//转换为对象
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//Map<String,String> parmMap = JSONObject.fromObject(parmStr);
		map6.put("cache_refreshtime",df.format(new Date()));
		map6.put("cache_id",cacheID);
		Method m6  = c.getDeclaredMethod("updateRefreshTime",Map.class,String.class);
		m6.invoke(o,map6,"com.baosight.iwater.system.cacheManager.dao.CacheManagerMapper.updateRefreshTime");
		 
		  }catch (Exception e) {
			  e.printStackTrace();
		 }
	}
	public void refreshNow(String cachePackage2, String cacheRole2,String cacheIdname2, String cacheType2, String cacheID2) {
		try { 
			CacheManager cacheManager=new CacheManager();
			//java反射机制
			//Class c = Class.forName(cachePackage2.split("dao")[0]+"service.impl"+cachePackage2.split("dao")[1].replaceAll("Mapper","ServiceImpl"));
			Class c = Class.forName("com.baosight.iwater.system.cacheManager.service.impl.CacheManagerServiceImpl");
			Object o=c.newInstance();	
		if(cacheType2.equals("1")){
			   //System.out.println(cachePackage.split("dao")[0]+"service.impl"+cachePackage.split("dao")[1].replaceAll("Mapper","ServiceImpl"));
				  Method m  = c.getDeclaredMethod(cacheIdname2+"FlushCache",String.class);
				  Object retValue=m.invoke(o,cachePackage2+"."+cacheIdname2+"FlushCache");
	       
		}else if(cacheType2.equals("2")){
			String key=cachePackage2+"."+cacheIdname2+"-"+cacheRole2;
				if(cacheManager.jedis.get(key.getBytes("utf-8"))!=null){
					List<Map<String, String>> findAllList =(List<Map<String, String>>) SerializeUtil.unserialize(cacheManager.jedis.get(key.getBytes("utf-8")));
					for(int i=0;i<findAllList.size();i++){
						Map<String,String> map=findAllList.get(i);
						if(map.get("state")!=null&&map.get("state").equals("1")){//新增
							 Method m2  = c.getDeclaredMethod("save",Map.class,String.class);
							 m2.invoke(o,map,cachePackage2+".save");
						}else if(map.get("state")!=null&&map.get("state").equals("2")){//删除
							 Method m3  = c.getDeclaredMethod("deleteById",Map.class,String.class);
							 m3.invoke(o,map,cachePackage2+".deleteById");
						}else if(map.get("state")!=null&&map.get("state").equals("3")){//修改
							 Method m4  = c.getDeclaredMethod("updateById",Map.class,String.class);
							 m4.invoke(o,map,cachePackage2+".updateById");
						}
					}
					String jsonStr=JSONArray.fromObject(findAllList).toString();
					Method m5  = c.getDeclaredMethod(cacheIdname2+"FlushCache",String.class);
					m5.invoke(o,cachePackage2+"."+cacheIdname2+"FlushCache");
				}
			
		}
		String parmStr6="{\"cache_id\":\"1\",\"cache_refreshtime\":\"\"}";
		//转换为对象
		Map<String,String> map6 = JSONObject.fromObject(parmStr6);
		//转换为对象
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//Map<String,String> parmMap = JSONObject.fromObject(parmStr);
		map6.put("cache_refreshtime",df.format(new Date()));
		map6.put("cache_id",cacheID2);
		Method m6  = c.getDeclaredMethod("updateRefreshTime",Map.class,String.class);
		m6.invoke(o,map6,"com.baosight.iwater.system.cacheManager.dao.CacheManagerMapper.updateRefreshTime");
		 
		  }catch (Exception e) {
			  e.printStackTrace();
		 }
	}
	
	
}
