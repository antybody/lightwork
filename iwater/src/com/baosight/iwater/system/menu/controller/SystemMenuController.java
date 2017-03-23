package com.baosight.iwater.system.menu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baosight.iwater.system.log.SystemControllerLog;
import com.baosight.iwater.system.menu.service.IMenuService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/system/menu")
@Api(value = "/system/menu", description = "菜单管理相关操作")
public class SystemMenuController {
	@Autowired
	private IMenuService menuService;
	/**
	 * 查询所有  分页   管理使用
	 * @return
	 */
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "菜单分页条件查询") 
	@ApiOperation(value="菜单分页条件查询", httpMethod ="POST",  notes ="分页参数,查询条件参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "params", value = "{start:1,pageSize:10}", paramType="query", required = true),
		@ApiImplicitParam(name = "param", value = "{menu_zhname:'test',menu_url:'test'...}", paramType="query", required = true)
	})
	public String list(HttpServletRequest request){
		return menuService.findAll(request);
	}
	
	/**
	 * 新增 
	 * @return
	 */
	@RequestMapping(value="/create",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "新增菜单") 
	@ApiOperation(value="新增菜单", httpMethod ="POST",  notes ="菜单参数")
	@ApiImplicitParam(name = "params", value = "{menu_zhname:'test',menu_code:'test'...}", paramType="query", required = true)  
	public String create(HttpServletRequest request){
		return menuService.insert(request);
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/del",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "删除菜单") 
	@ApiOperation(value="删除菜单", httpMethod ="POST",  notes ="菜单参数")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)  
	public String del(HttpServletRequest request){
		return menuService.deleteByPrimaryKey(request);
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "修改菜单") 
	@ApiOperation(value="修改菜单", httpMethod ="POST",  notes ="菜单参数")
	@ApiImplicitParam(name = "params", value = "{menu_zhname:'test',menu_code:'test'...}", paramType="query", required = true)  
	public String update(HttpServletRequest request){
		return menuService.updateByPrimaryKey(request);
	}
	
	/**
	 * 导出菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/download",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "导出菜单") 
	@ApiOperation(value="导出菜单", httpMethod ="POST",  notes ="查询条件参数")
	@ApiImplicitParam(name = "param", value = "{menu_zhname:'test',menu_code:'test'...}", paramType="query", required = true)  
	public String download(HttpServletRequest request,HttpServletResponse response){
		return menuService.download(request,response);
	}
	
	/**
	 * 根据主键查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/get",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询单个菜单") 
	@ApiOperation(value="查询单个菜单", httpMethod ="POST",  notes ="单个菜单信息")
	@ApiImplicitParam(name = "params", value = "{ui_id:'test'}", paramType="query", required = true)  
	public String get(HttpServletRequest request){
		return menuService.selectByPrimaryKey(request);
	}
	
	/**
	 * 查询项目对应的菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/parentMenu",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "查询项目对应的菜单") 
	@ApiOperation(value="查询项目对应的菜单", httpMethod ="POST",  notes ="项目信息")
	@ApiImplicitParam(name = "params", value = "{item_prefix:'test'}", paramType="query", required = true)
	public String parentMenu(HttpServletRequest request){
		return menuService.findParentMenu(request);
	}
	
	/**
	 * 得到properties文件中项目的前缀
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/menuItem",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "properties文件中项目前缀") 
	@ApiOperation(value="properties文件中项目前缀", httpMethod ="POST",  notes ="无传入参数")
	public String menuItem(HttpServletRequest request){
		return menuService.menuItem(request);
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
		return menuService.checkOnly(request);
	}
}
