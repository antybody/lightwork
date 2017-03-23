package com.baosight.iwater.system.log.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.log.SystemControllerLog;
import com.baosight.iwater.system.log.service.ILogService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/system/log")
@Api(value = "/system/log", description = "日志管理相关操作")
public class SystemLogController {

	@Resource
	private ILogService logService;
	
	/**
	 * 查询所有  分页
	 * @return
	 */
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "日志分页条件查询") 
	@ApiOperation(value="日志分页条件查询", httpMethod ="POST",  notes ="分页参数,查询条件参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "params", value = "{start:1,pageSize:10}", paramType="query", required = true),
	})
	public String list(HttpServletRequest request){
		return logService.findAll(request);
	}
	
	/**
	 * 根据主键查询日志
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询单个日志") 
	@ApiOperation(value="查询单个日志", httpMethod ="POST",  notes ="单个日志信息")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)  
	public String get(HttpServletRequest request){
		return logService.selectByPrimaryKey(request);
	}
	
	
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "删除日志") 
	@ApiOperation(value="删除日志", httpMethod ="POST",  notes ="日志参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)
	public String del(HttpServletRequest request){
		return logService.del(request);
	}
	
	
	
	/**
	 * 导出菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/download",produces = "text/html;charset=UTF-8")
	public String download(HttpServletRequest request,HttpServletResponse response){
		return logService.download(request,response);
	}
	
	
	
}
