package com.baosight.iwater.system.power.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.log.SystemControllerLog;
import com.baosight.iwater.system.power.service.IPowerService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/system/power")
@Api(value = "/system/power", description = "权限资源管理相关操作")
public class SystemPowerController {
	
	@Resource
	private IPowerService powerService;
	
	/**
	 * 条件分页查询
	 * @return
	 */
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "权限资源分页条件查询") 
	@ApiOperation(value="权限资源分页条件查询", httpMethod ="POST",  notes ="分页参数,查询条件参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "params", value = "{start:1,pageSize:10}", paramType="query", required = true),
		@ApiImplicitParam(name = "param", value = "{power_code:'test'...}", paramType="query", required = true)
	})
	public String list(HttpServletRequest request){
		return powerService.findAll(request);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	@RequestMapping(value="/allList",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "权限资源全部查询") 
	@ApiOperation(value="权限资源全部查询", httpMethod ="POST",  notes ="")
	public String allList(HttpServletRequest request){
		return powerService.getAll(request);
	}
	
	/**
	 * 新增 
	 * @return
	 */
	@RequestMapping(value="/create",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "注册权限资源") 
	@ApiOperation(value="注册权限资源", httpMethod ="POST",  notes ="权限资源参数")
	@ApiImplicitParam(name = "params", value = "{power_code:'test'...}", paramType="query", required = true)
	public String create(HttpServletRequest request){
		return powerService.insert(request);
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "删除权限资源") 
	@ApiOperation(value="删除权限资源", httpMethod ="POST",  notes ="权限资源参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)
	public String del(HttpServletRequest request){
		return powerService.deleteByPrimaryKey(request);
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "修改权限资源") 
	@ApiOperation(value="修改权限资源", httpMethod ="POST",  notes ="权限资源参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'...}", paramType="query", required = true)
	public String update(HttpServletRequest request){
		return powerService.updateByPrimaryKey(request);
	}
	
	/**
	 * 根据主键查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询单个权限资源") 
	@ApiOperation(value="查询单个权限资源", httpMethod ="POST",  notes ="单个用户参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)
	public String get(HttpServletRequest request){
		return powerService.selectByPrimaryKey(request);
	}
	
	/**
	 * 唯一性校验
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkOnly",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "唯一性校验") 
	@ApiOperation(value="权限资源相关信息唯一性校验", httpMethod ="POST",  notes ="相关权限资源参数")
	@ApiImplicitParam(name = "params", value = "{power_code:'test'...}", paramType="query", required = true)
	public String checkOnly(HttpServletRequest request){
		return powerService.checkOnly(request);
	}
	
}
