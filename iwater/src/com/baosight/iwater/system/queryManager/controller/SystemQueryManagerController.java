package com.baosight.iwater.system.queryManager.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/system/queryManager")
@Api(value = "/system/queryManager", description = "基础信息相关操作")
public class SystemQueryManagerController {
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
	@RequestMapping(value="/query",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "基础信息properties查询") 
	@ApiOperation(value="查询基础信息", httpMethod ="POST",  notes ="查询基础信息")
	public String query(HttpServletRequest request){
		State state=new BaseState();
		PropertiesManager propertiesmanager=new PropertiesManager();
		try{
			String propertiesName="bsproperty.properties";
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
	@RequestMapping(value="/update",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "基础参数修改") 
	@ApiOperation(value="修改基础参数", httpMethod ="POST",  notes ="基础参数")
	@ApiImplicitParam(name = "params", value = "{pic_local:'test',pic_remote:'test'...}", paramType="query", required = true)  
	public String update(HttpServletRequest request){
		State state=new BaseState();
		PropertiesManager propertiesmanager=new PropertiesManager();
		try{
			String stk=request.getParameter("params");
			Map<String,String> parmMap = JSONObject.fromObject(stk);
			String propertiesName="bsproperty.properties";
			propertiesmanager.createProperties(parmMap,propertiesName,"",false);
			return state.addState(AppInfo.SUCCESS,"");
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
}
