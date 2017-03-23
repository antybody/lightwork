package com.baosight.iwater.system.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.log.SystemControllerLog;
import com.baosight.iwater.system.user.service.IUserService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/system/user")
public class SystemUserController {
	@Resource
	private IUserService userService;
	

	/**
	 * 查询所有  分页
	 * @return
	 */
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "用户分页条件查询") 
	@ApiOperation(value="用户分页条件查询", httpMethod ="POST",  notes ="分页参数,查询条件参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "params", value = "{start:1,pageSize:10}", paramType="query", required = true),
		@ApiImplicitParam(name = "param", value = "{user_code:'test'...}", paramType="query", required = true)
	})
	public String list(HttpServletRequest request){
		return userService.findAll(request);
	}
	
	/**
	 * 新增 
	 * @return
	 */
	@RequestMapping(value="/create",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "新增用户") 
	@ApiOperation(value="新增用户", httpMethod ="POST",  notes ="用户参数")
	@ApiImplicitParam(name = "params", value = "{user_code:'test'...}", paramType="query", required = true)
	public String create(HttpServletRequest request){
		return userService.insert(request);
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "删除用户") 
	@ApiOperation(value="删除用户", httpMethod ="POST",  notes ="用户参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)
	public String del(HttpServletRequest request){
		return userService.deleteByPrimaryKey(request);
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "修改用户") 
	@ApiOperation(value="修改用户", httpMethod ="POST",  notes ="用户参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'...}", paramType="query", required = true)
	public String update(HttpServletRequest request){
		return userService.updateByPrimaryKey(request);
	}
	
	/**
	 * 根据主键查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询单个用户") 
	@ApiOperation(value="查询单个用户", httpMethod ="POST",  notes ="单个用户参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)
	public String get(HttpServletRequest request){
		return userService.selectByPrimaryKey(request);
	}
	
	/**
	 * 唯一性校验
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkOnly",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "唯一性校验") 
	@ApiOperation(value="用户相关信息唯一性校验", httpMethod ="POST",  notes ="相关用户参数")
	@ApiImplicitParam(name = "params", value = "{user_code:'test'...}", paramType="query", required = true)
	public String checkOnly(HttpServletRequest request){
		return userService.checkOnly(request);
	}
	
}
