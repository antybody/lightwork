package com.baosight.iwater.system.organization.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.log.SystemControllerLog;

import com.baosight.iwater.system.organization.service.IOrganizationService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/system/organization")
public class SystemOrganizationController {
	@Resource
	private IOrganizationService organizationService;
	
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
	 * 查询所有  分页
	 * @return
	 */
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "分页条件查询") 
	@ApiOperation(value="分页条件查询", httpMethod ="POST",  notes ="分页参数,条件查询参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "params", value = "{start:1,pageSize:10}", paramType="query", required = true),
		@ApiImplicitParam(name = "param", value = "{org_zhname:'test'...}", paramType="query", required = true)
	})
	public String list(HttpServletRequest request){
		return organizationService.findAll(request);

	}
	
	/**
	 * 新增 
	 * @return
	 */
	@RequestMapping(value="/create",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "新增组织机构") 
	@ApiOperation(value="新增组织机构", httpMethod ="POST",  notes ="组织机构参数")
	@ApiImplicitParam(name = "params", value = "{org_zhname:'test'...}", paramType="query", required = true) 
	public String create(HttpServletRequest request){
		return organizationService.insert(request);
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "删除组织机构") 
	@ApiOperation(value="删除组织机构", httpMethod ="POST",  notes ="组织机构参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true) 
	public String del(HttpServletRequest request){
		return organizationService.deleteByPrimaryKey(request);
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "修改组织机构") 
	@ApiOperation(value="修改组织机构", httpMethod ="POST",  notes ="组织机构参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test',user_code:'test'}", paramType="query", required = true) 
	public String update(HttpServletRequest request){
		return organizationService.updateByPrimaryKey(request);
	}
	
	/**
	 * 根据主键查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询单个组织机构") 
	@ApiOperation(value="查询单个组织机构", httpMethod ="POST",  notes ="组织机构参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true) 
	public String get(HttpServletRequest request){
		return organizationService.selectByPrimaryKey(request);
	}

	/**
	 * 根据主键查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listZtree",produces = "text/html;charset=UTF-8")
	public String listZtree(HttpServletRequest request){
		return organizationService.listZtree(request);
	}
	/**
	 * properties文件中项目的前缀
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/orgItem",produces = "text/html;charset=UTF-8")
	public String orgItem(HttpServletRequest request){
		return organizationService.orgItem(request);
	}

	
	/**
	 * 查询所有  供选择使用  不分页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/allList",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询所有组织机构") 
	@ApiOperation(value="查询所有组织机构", httpMethod ="POST",  notes ="无传入参数")
	public String selectAll(HttpServletRequest request){
		return organizationService.selectAll(request);
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
		return organizationService.checkOnly(request);
	}

}
