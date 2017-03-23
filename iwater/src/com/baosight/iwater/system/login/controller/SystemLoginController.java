package com.baosight.iwater.system.login.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.baosight.iwater.system.log.SystemControllerLog;
import com.baosight.iwater.system.login.service.ILoginService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/system/login")
@Api(value = "/system/login", description = "登录相关操作")
public class SystemLoginController {
	@Resource
	private ILoginService loginService;
	
	/**
	 * 登录判断
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/judge",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "用户登录") 
	@ApiOperation(value="用户登录验证", httpMethod ="POST",  notes ="用户登录验证") 
	@ApiImplicitParam(name = "params", value = "{user_code:123,user_pwd:123}", paramType="query", required = true)  
	public String judge(HttpServletRequest request){
		return loginService.selectByUserCode(request);
	}
	
	/**
	 * 得到用户菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getMenu",produces = "text/html;charset=UTF-8")
	@SystemControllerLog(description = "得到用户菜单") 
	@ApiOperation(value="得到用户菜单", httpMethod ="POST",  notes ="用户登录验证") 
	@ApiImplicitParam(name = "params", value = "{user_code:test}", paramType="query", required = true)  
	public String getMenu(HttpServletRequest request){
		return loginService.getMenu(request);
	}
	
//	@RequestMapping(value="/dispatch",method = RequestMethod.GET)
//	@SystemControllerLog(description = "登录跳转页面判断") 
//	@ApiOperation(value="页面ID", httpMethod ="GET",  notes ="登录按角色跳转") 
//	 public View dispatch(ModelMap model,HttpServletRequest request) {
//	        String path = request.getContextPath() ;
//	        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
//	          Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext()
//	                  .getAuthentication().getAuthorities());
//	          if (roles.contains("ROLE_ADMIN")) {
//	              return new RedirectView(basePath + "index");
//	          }
//	          return new RedirectView(basePath + "indexV1");
//	    }
}
