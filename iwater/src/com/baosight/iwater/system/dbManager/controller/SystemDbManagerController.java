package com.baosight.iwater.system.dbManager.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.db.manager.PropertiesManager;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.log.SystemControllerLog;
import com.baosight.iwater.system.organization.service.IOrganizationService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/system/dbManager")
@Api(value = "/system/dbManager", description = "数据库相关操作")
public class SystemDbManagerController {
	/*
	 *  method=RequestMethod.GET 支持get方式 
	 *  method=RequestMethod.POST 支持post方式
	 *  当写一种的时候，另一种方式请求会报 Request method 'GET' not supported
	 *  经过测试  不写两种都支持 
	 *  但是需要注意的是 如果直接将参数附加在url后面容易出现中文乱码的情况
	 *  
	 *  返回数据code说明：
	 *  AppInfo.NO_PARAM  没有获得所需要的参数    (传入数据格式不正确 或者 没有传入参数)
	 *  AppInfo.NOT_FIND  操作的数据不存在            (传入数据格式正确 内容有误)
	 *  AppInfo.SUCCESS   执行成功
	 */
	
	/**
	 * 查询所有 
	 * @return
	 */
	@RequestMapping(value="/queryRedis",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "redis配置信息properties查询") 
	@ApiOperation(value="查询redis配置信息", httpMethod ="POST",  notes ="查询redis配置信息")
	public String queryRedis(HttpServletRequest request){
		State state=new BaseState();
		PropertiesManager propertiesmanager=new PropertiesManager();
		try{
			String propertiesName="redis.properties";
			Map<String,String> map=propertiesmanager.readProperties(propertiesName);
			return state.addState(AppInfo.SUCCESS,map);
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateRedis",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "redis配置修改") 
	@ApiOperation(value="修改redis配置", httpMethod ="POST",  notes ="redis配置")
	@ApiImplicitParam(name = "params", value = "{redis_addr:'test',redis_port:'test'...}", paramType="query", required = true)  
	public String updateRedis(HttpServletRequest request){
		State state=new BaseState();
		PropertiesManager propertiesmanager=new PropertiesManager();
		try{
			String stk=request.getParameter("params");
			stk=stk.replaceAll(":","\":\""); 
			stk=stk.replaceAll(",","\",\"");
			stk="{@"+stk+"@}";
			//将@替换为"号
			stk=stk.replace("@", "\"");
			stk=stk.replace("%22","");
			stk=stk.replace("%20","");
			stk=stk.replace("%7B","");
			stk=stk.replace("%7D","");
			Map<String,String> parmMap = JSONObject.fromObject(stk);
			System.out.println(parmMap);
			String propertiesName="redis.properties";
			propertiesmanager.createProperties(parmMap,propertiesName,"",false);
			return state.addState(AppInfo.SUCCESS,"");
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	/**
	 * 查询所有 
	 * @return
	 */
	@RequestMapping(value="/queryDB",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "数据库配置信息properties查询") 
	@ApiOperation(value="查询数据库配置", httpMethod ="POST",  notes ="查询查询数据库配置")
	public String queryDB(HttpServletRequest request){
		State state=new BaseState();
		PropertiesManager propertiesmanager=new PropertiesManager();
		try{
			String propertiesName="jdbc.properties";
			Map<String,String> map=propertiesmanager.readProperties(propertiesName);
			return state.addState(AppInfo.SUCCESS,map);
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateDB",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "数据库配置修改") 
	@ApiOperation(value="数据库配置修改", httpMethod ="POST",  notes ="数据库配置")
	@ApiImplicitParam(name = "params", value = "{driver:'test',url:'test'...}", paramType="query", required = true)  
	public String updateDB(HttpServletRequest request){
		State state=new BaseState();
		PropertiesManager propertiesmanager=new PropertiesManager();
		try{
			String stk=request.getParameter("params");
			System.out.println(stk);
			stk=stk.replaceAll("&&","\",\""); 
			stk=stk.replaceAll("==","\":\"");
			stk=stk.replace("%22","");
			stk="{@"+stk+"@}";
			//将@替换为"号
			stk=stk.replace("@", "\"");
			//String stk2="{\"driver\":\"com.mysql.jdbc.Driver\",\"initialSize\":\"0\"}";
			Map<String,String> parmMap = JSONObject.fromObject(stk);
			System.out.println(parmMap);
			String propertiesName="jdbc.properties";
			propertiesmanager.createProperties(parmMap,propertiesName,"",false);
			return state.addState(AppInfo.SUCCESS,"");
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
}
