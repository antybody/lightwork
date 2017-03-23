package com.baosight.iwater.system.cacheManager.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;  
import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.define.QuartzJob;
import com.baosight.iwater.define.QuartzManager;
import com.baosight.iwater.system.cacheManager.service.ICacheManagerService;
import com.baosight.iwater.system.cacheManager.controller.Task;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.log.SystemControllerLog;
import com.baosight.iwater.system.organization.pojo.Organization;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/system/cacheManager")
@Api(value = "/system/cacheManager", description = "缓存管理")
public class SystemCacheManagerController {
	@Resource
	private ICacheManagerService cacheManagerService;
	
	private State state=new BaseState();
	/*
	 * method=RequestMethod.GET 支持get方式 
	 *  method=RequestMethod.POST 支持post方式
	 *  当写一种的时候，另一种方式请求会报 Request method 'GET' not supported
	 *  经过测试  不写两种都支持 
	 *  但是需要注意的是 如果直接将参数附加在url后面容易出现中文乱码的情况
	 */
	
	/**
	 * 查询
	 * 在后面加上编码格式  避免出现中文乱码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "缓存列表") 
	public String list(HttpServletRequest request){
		//标准数据
		//String str=state.addState(AppInfo.SUCCESS,cacheManagerService.findAll(request));         //标准数据
		return cacheManagerService.findAll(request);
	}
	/**
	 * 查询测试用，用到缓存的列表
	 * 在后面加上编码格式  避免出现中文乱码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/listTest",produces = "text/html;charset=UTF-8")
	public String listTest(){
		//标准数据
		//String str=state.addState(AppInfo.SUCCESS,cacheManagerService.findAll(request));         //标准数据
		return cacheManagerService.findAllTest();
	}
	/**
	 * 立即刷新
	 * 在后面加上编码格式  避免出现中文乱码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/refreshNow",produces = "text/html;charset=UTF-8")
	public String refreshNow(HttpServletRequest request){
		List<Map<String,String>> findAllList =cacheManagerService.selectListByPrimaryKey(request);
		//获得传入数据
		String cachePackage=findAllList.get(0).get("cache_package");//待刷新的包名
		String cacheRole=findAllList.get(0).get("cache_role");//角色名cache_idname
		String cacheIdname=findAllList.get(0).get("cache_idname");//
		String cacheType=findAllList.get(0).get("cache_type");//缓存类型
		String cacheRate=findAllList.get(0).get("cache_rate");
		String cacheID=findAllList.get(0).get("cache_id");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//parmMap.put("up_date",df.format(new Date()));
		//标准数据
		String job_name = cachePackage+"."+cacheIdname+"-"+cacheRole; 
		Task job = new Task(cachePackage,cacheRole,cacheIdname,cacheType,cacheID);
        job.refreshNow(cachePackage,cacheRole,cacheIdname,cacheType,cacheID);
        String str=state.addState(AppInfo.SUCCESS,null);
		return str;
	}  
	/**
	 * 定时器启动
	 * 在后面加上编码格式  避免出现中文乱码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/addTimer",produces = "text/html;charset=UTF-8") 
	public String addTimer(HttpServletRequest request){
		List<Map<String,String>> findAllList =cacheManagerService.selectListByPrimaryKey(request);
		//获得传入数据
		String cachePackage=findAllList.get(0).get("cache_package");//待刷新的包名
		String cacheRole=findAllList.get(0).get("cache_role");//角色名cache_idname
		String cacheIdname=findAllList.get(0).get("cache_idname");//
		String cacheType=findAllList.get(0).get("cache_type");//缓存类型
		String cacheRate=findAllList.get(0).get("cache_rate");
		String cacheID=findAllList.get(0).get("cache_id");
		//标准数据
		String job_name = cachePackage+"."+cacheIdname+"-"+cacheRole; 
		Task job = new Task(cachePackage,cacheRole,cacheIdname,cacheType,cacheID);
        System.out.println("【定时器启动】开始...");    
        
        QuartzManager.addJob(job_name, QuartzJob.class,cacheRate,job); 
        //QuartzManager.addJob(job_name+"2", QuartzJob.class,cacheRate,job); 
        String str=state.addState(AppInfo.SUCCESS,null);
		return str;
	}
	/**
	 * 删除定时器   
	 * 在后面加上编码格式  避免出现中文乱码
	 * @param id 
	 * @return 
	 */
	@RequestMapping(value="/removeTimer",produces = "text/html;charset=UTF-8")
	public String removeTimer(HttpServletRequest request){
		List<Map<String,String>> findAllList =cacheManagerService.selectListByPrimaryKey(request);
		//获得传入数据
		String cachePackage=findAllList.get(0).get("cache_package");//待刷新的包名
		String cacheRole=findAllList.get(0).get("cache_role");//角色名cache_idname
		String cacheIdname=findAllList.get(0).get("cache_idname");//
		String cacheType=findAllList.get(0).get("cache_type");//缓存类型
		String cacheRate=findAllList.get(0).get("cache_rate");
		//标准数据
		String job_name = cachePackage+"."+cacheIdname+"-"+cacheRole;  
        System.out.println("【删除定时器】");    
        QuartzManager.removeJob(job_name); 
        String str=state.addState(AppInfo.SUCCESS,null);
		return str;
	}
	/**
	 * 新增 
	 * @return
	 */
	@RequestMapping(value="/create",produces = "text/html;charset=UTF-8")
	public String create(HttpServletRequest request){
		//获得传入数据
		String parms="";
		try {
			parms = URLDecoder.decode(request.getParameter("params"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String parmStr="{\"cache_id\":\"1\",\"add_date\":\"1\",\"up_date\":\"\",\"user_info\":\"whb\",\"cache_state\":\"0\",\"cache_menu\":\"1\",\"cache_menu_id\":\"1\",\"cache_package\":\"com.baosight.iwater.system.cacheManager.dao.CacheManagerMapper\",\"cache_role\":\"rolename\",\"cache_idname\":\"findAll\",\"cache_type\":\"1\",\"cache_rate\":\"1\",\"cache_refreshtime\":\"\",\"cache_modified\":\"1\"}";
		//转换为对象
		Map<String,String> parmMap = JSONObject.fromObject(parms);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		parmMap.put("add_date",df.format(new Date()));
		parmMap.put("cache_id",UUID.randomUUID().toString().replace("-",""));
		parmMap.put("cache_state","0");
		//parmMap.put("cache_rate","*/20 * * * * ?");//待确认
		cacheManagerService.save(parmMap);
		//返回信息
		//String str=new JsonMain().setInfo(1, 14, 14, 0, null,1);   //页面显示
		String str=state.addState(AppInfo.SUCCESS,null);
		return str;
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del",produces = "text/html;charset=UTF-8")
	public String del(HttpServletRequest request){
		//获得传入数据
		String parmStr="";
		try {
			parmStr = URLDecoder.decode(request.getParameter("params"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String parmStr="{\"cache_id\":\"6996dcd6b06a49f6b8c94b3c9588bf5e\",\"cache_menu_id\":\"2\"}";
		//转换为对象
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Map<String,String> parmMap = JSONObject.fromObject(parmStr);
		parmMap.put("up_date",df.format(new Date()));
		cacheManagerService.deleteById(parmMap);
		//返回信息
		String str=state.addState(AppInfo.SUCCESS,null);
		return str;
	}
	/**
	 * 根据主键查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get",produces = "text/html;charset=UTF-8")
	public String get(HttpServletRequest request){
		return cacheManagerService.selectByPrimaryKey(request);
	}
	/**
	 * 导出菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/download",produces = "text/html;charset=UTF-8")
	public String download(HttpServletRequest request,HttpServletResponse response){
		return cacheManagerService.download(request,response);
	}
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update",produces = "text/html;charset=UTF-8")
	public String update(HttpServletRequest request){
		//获得传入数据
		String parms="";
		try {
			parms = URLDecoder.decode(request.getParameter("params"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String parmStr="{\"cache_id\":\"1\",\"add_date\":\"1\",\"up_date\":\"\",\"user_info\":\"whb\",\"cache_state\":\"0\",\"cache_menu\":\"1\",\"cache_menu_id\":\"1\",\"cache_package\":\"com.baosight.iwater.system.cacheManager.dao.CacheManagerMapper\",\"cache_role\":\"rolename\",\"cache_idname\":\"findAll\",\"cache_type\":\"1\",\"cache_rate\":\"1\",\"cache_refreshtime\":\"\",\"cache_modified\":\"1\"}";
				//转换为对象
		Map<String,String> parmMap = JSONObject.fromObject(parms);
		//转换为对象
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//Map<String,String> parmMap = JSONObject.fromObject(parmStr);
		parmMap.put("up_date",df.format(new Date()));
		cacheManagerService.updateById(parmMap);
		//返回信息
		String str=state.addState(AppInfo.SUCCESS,null);
		return str;
	}
}
