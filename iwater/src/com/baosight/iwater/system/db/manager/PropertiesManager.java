package com.baosight.iwater.system.db.manager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;




import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baosight.iwater.core.security.LightWorkSecurityMetadataSource;

/**
 * Properties文件 管理
 * @author liuwendong
 *
 */
public class PropertiesManager {
	
	private static Logger logger = Logger.getLogger(PropertiesManager.class);

	/**
	 * 自行指定目录   生成Properties文件
	 * 如果文件已经存在  就会在已存在的文件上追加或者重写 
	 * warn:  请谨慎使用 会改变项目中对应的properties文件
	 * 
	 * @param obj		类对象  Map集合(Properties的键值)
	 * @param savePath	Properties文件路径 (保存路径+文件名)
	 * @param note		文件顶部注释
	 * @param isAdd		true (在之前的内容基础上追加)  false (重写)
	 */
	public void buildProperties(Object obj,String savePath,String note,Boolean isAdd){
		try{
			//保存属性到properties文件  true表示追加打开
			FileOutputStream oFile = new FileOutputStream(savePath, isAdd);
			//Object --> json字符串  -->JSONObject
			JSONObject json=JSON.parseObject(JSON.toJSONString(obj));
			//生成properties实例
			Properties pro = new Properties();
			//遍历json   将其中的属性作为 properties 键值
			if(json!=null){
				for(String str:json.keySet()){
					if(json.get(str)!=null){
						pro.setProperty(str,json.get(str).toString());
					}
				}
			}
			pro.store(oFile,note);
			oFile.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 在当前编译项目的指定目录   生成Properties文件
	 * 如果文件已经存在  就会在已存在的文件上追加或者重写  
	 * warn:  请谨慎使用 会改变项目中对应的properties文件
	 * 
	 * @param obj      			类对象  Map集合(Properties的键值)
	 * @param propertiesName	Properties文件名称 (保存路径已经指定)
	 * @param note				文件顶部注释
	 * @param isAdd				true 在之前的内容基础上追加  false 重写
	 */
	public void createProperties(Object obj,String propertiesName,String note,Boolean isAdd){
		//这个是获取此文件编译后的路径地址 (注意是编译路径)
		String path = this.getClass().getClassLoader().getResource("/config").getPath(); 
		StringBuffer str=new StringBuffer(path);
		
		//获得当前项目的目录  E:\javaWork\iwater
		//StringBuffer str=new StringBuffer(System.getProperty("user.dir"));
		//str.append("/src/main/resources/");
		
		str.append(propertiesName);
		buildProperties(obj, str.toString(), note, isAdd);
	}
	
	
	/**
	 * 将项目中Properties文件内容 转换为Map集合
	 * (注意:这里读取的文件经过编译)
	 * 
	 * @param 	propertiesName   Properties文件名
	 * @return	null 找不到指定的文件 或者其他异常
	 */
	public Map<String,String> readProperties(String propertiesName){
		try{
			Properties prop = new Properties(); 
			
			//这个是获取此文件编译后的路径地址 (注意是编译路径)
			String path = this.getClass().getClassLoader().getResource("/config").getPath(); 
			logger.debug("----------转换前的路径"+path);
			path = URLDecoder.decode(path,"utf-8");
			logger.debug("----------转换后的路径"+path);
			//获得当前项目的目录  E:\javaWork\iwater
			//StringBuffer str=new StringBuffer(System.getProperty("user.dir"));
			StringBuffer str=new StringBuffer(path);
			//str.append("/src/main/resources/");
			str.append(propertiesName);
			 //读取属性文件a.properties
			InputStream in = new BufferedInputStream (new FileInputStream(str.toString()));
			prop.load(in);     ///加载属性列表
			Iterator<String> it=prop.stringPropertyNames().iterator();
			Map<String,String> map=new HashMap<String,String>();
			while(it.hasNext()){
			    String key=it.next();
			    map.put(key,prop.getProperty(key));
			}
			in.close();
			return map;
		}
		catch(Exception  e){
			e.printStackTrace();
			return null;
		}
	}
	
}
