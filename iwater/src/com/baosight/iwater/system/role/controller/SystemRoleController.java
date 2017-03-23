package com.baosight.iwater.system.role.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.log.SystemControllerLog;
import com.baosight.iwater.system.role.service.IRoleService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/system/role")
public class SystemRoleController {
	
	@Resource
	private IRoleService roleService;
	/**
	 * 条件分页查询
	 * @return
	 */
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "分页条件查询") 
	@ApiOperation(value="分页条件查询", httpMethod ="POST",  notes ="分页参数,查询条件参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "params", value = "{start:1,pageSize:10}", paramType="query", required = true),
		@ApiImplicitParam(name = "param", value = "{role_zhname:'test'...}", paramType="query", required = true)
	}) 
	public String list(HttpServletRequest request){
		return roleService.findAll(request);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	@RequestMapping(value="/allList",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询所有") 
	@ApiOperation(value="查询所有", httpMethod ="POST",  notes ="")
	public String allList(HttpServletRequest request){
		return roleService.getAll(request);
	}
	
	/**
	 * 新增 
	 * @return
	 */
	@RequestMapping(value="/create",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "新增角色") 
	@ApiOperation(value="新增角色", httpMethod ="POST",  notes ="角色参数")
	@ApiImplicitParam(name = "params", value = "{role_zhname:'test'...}", paramType="query", required = true)
	public String create(HttpServletRequest request){
		return roleService.insert(request);
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "删除角色") 
	@ApiOperation(value="删除角色", httpMethod ="POST",  notes ="角色参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)
	public String del(HttpServletRequest request){
		return roleService.deleteByPrimaryKey(request);
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "修改角色") 
	@ApiOperation(value="修改角色", httpMethod ="POST",  notes ="角色参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test',role_zhname:'test'...}", paramType="query", required = true)
	public String update(HttpServletRequest request){
		return roleService.updateByPrimaryKey(request);
	}
	
	/**
	 * 根据主键查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询单个角色") 
	@ApiOperation(value="查询单个角色", httpMethod ="POST",  notes ="角色参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)
	public String get(HttpServletRequest request){
		return roleService.selectByPrimaryKey(request);
	}
	
	/**
	 * 根据角色代码  查询所拥有的权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getRolePower",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询角色 所拥有的权限") 
	@ApiOperation(value="查询角色 所拥有的权限", httpMethod ="POST",  notes ="角色代码")
	@ApiImplicitParam(name = "params", value = "{role_code:'test'}", paramType="query", required = true)
	public String getRolePower(HttpServletRequest request){
		return roleService.getRolePower(request);
	}
	
	/**
	 * 唯一性校验
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkOnly",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "唯一性校验") 
	@ApiOperation(value="角色相关信息唯一性校验", httpMethod ="POST",  notes ="相关角色参数")
	@ApiImplicitParam(name = "params", value = "{user_code:'test'...}", paramType="query", required = true)
	public String checkOnly(HttpServletRequest request){
		return roleService.checkOnly(request);
	}
	
	/**
	 * 查询角色用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getRoleUser",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询角色用户") 
	@ApiOperation(value="查询角色用户", httpMethod ="POST",  notes ="角色参数")
	@ApiImplicitParam(name = "params", value = "{role_code:'test'}", paramType="query", required = true)
	public String selRoleUser(HttpServletRequest request){
		return roleService.selRoleUser(request);
	}
}
