package com.baosight.iwater.system.organization.service.impl;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.system.db.manager.PropertiesManager;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.Common;
import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.menu.pojo.Menu;
import com.baosight.iwater.system.organization.dao.OrganizationMapper;
import com.baosight.iwater.system.organization.pojo.Organization;
import com.baosight.iwater.system.organization.service.IOrganizationService;
import com.baosight.iwater.system.power.pojo.Power;

//下面注解里面的参数  是给实例化后的对象起名
@Service("organizationService")
public class OrganizationServiceImpl implements IOrganizationService{
	
	/*
	 *	返回数据code说明：
	 *  AppInfo.NO_PARAM  没有获得所需要的参数    (传入数据格式不正确 或者 没有传入参数)
	 *  AppInfo.NOT_FIND  操作的数据不存在            (传入数据格式正确 内容有误)
	 *  AppInfo.SUCCESS   执行成功
	 */
	
	@Resource
	private OrganizationMapper orgDao;
	
	public String deleteByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Organization org=JSON.parseObject(stk,Organization.class);
			if(org==null || org.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);  //传入数据格式不正确
			}
			org.setUp_date(Common.getTime());
			//进行删除
			int flag=orgDao.deleteByPrimaryKey(org);
			if(flag==1){
				return state.addState(AppInfo.SUCCESS,null);   //删除成功 
			}
			return state.addState(AppInfo.NOT_FIND,null);	    //删除主键不存在	
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}	
	}

	public String insert(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Organization org=JSON.parseObject(stk,Organization.class);
			if(org==null){
				return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
			}
			org.setAdd_date(Common.getTime());
			org.setUi_id(Common.getUUID());
			orgDao.insert(org);
			return state.addState(AppInfo.SUCCESS,null);         //添加成功
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

	public String selectByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Map<String,Object> map=state.JsonToMap(stk);
			if(map==null || map.get("ui_id")==null){
				return state.addState(AppInfo.NO_PARAM,null);     //传入数据格式不正确
			}
			return state.addState(AppInfo.SUCCESS, orgDao.selectByPrimaryKey(map.get("ui_id").toString()));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	public String listZtree(HttpServletRequest request) {
		State state=new BaseState();
		try{
			return state.addState(AppInfo.SUCCESS, orgDao.findAllItem2());
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	public String updateByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Organization org=JSON.parseObject(stk,Organization.class);
			if(org==null || org.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);      //传入数据格式不正确
			}
			org.setUp_date(Common.getTime());
			//进行修改
			int flag=orgDao.updateByPrimaryKey(org);
			if(flag==1){
				return state.addState(AppInfo.SUCCESS,null);       //修改成功
			}
			return state.addState(AppInfo.NOT_FIND,null);			//修改主键不存在
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

	public String findAll(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("param"),"utf-8");  //分页参数
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");	//查询条件
			PageInfo pageInfo=JSON.parseObject(stk,PageInfo.class);
			if(pageInfo==null){ 
				return state.addState(AppInfo.NO_PARAM,null);        //传入数据格式不正确
			}
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("start",pageInfo.getStart());
			map.put("pageSize",pageInfo.getPageSize());
			map.put("end",pageInfo.getStart()+pageInfo.getPageSize());  //oracle 分页参数
			if(st!=null && !"\"\"".equals(st.trim())){  
				Organization menu=JSON.parseObject(st,Organization.class);
				map.putAll(state.ObjToMap(menu));
			}
			
			map.put("queryItem", getQueryItem(map));  //通用查询条件
			pageInfo.setTotalCount(orgDao.findAllSize(map));
			
			//判断当前数据库类型
			if(Common.dataBaseType()==1){
				state.addState(AppInfo.SUCCESS,orgDao.findAllSql(map));
			}
			if(Common.dataBaseType()==2){
				state.addState(AppInfo.SUCCESS,orgDao.findAllOrcl(map));
			}
			
			//返回  分页信息
			state.addInfo("pageInfo",pageInfo);
			//将信息返回
			String str=state.getJson(); 
			return str;
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
	//组合形成 查询语句的条件部分  (mysql分页,oracle分页,查询总记录数共同使用)
	public String getQueryItem(Map<String,Object> map){
		StringBuffer str=new StringBuffer(" and u.del_modified=1");
		if(Common.notEmpty(map.get("org_zhname"))){  //组织机构名称
			str.append(" and u.org_zhname like '%"+map.get("org_zhname")+"%'");
		}
		if(Common.notEmpty(map.get("org_code"))){  //组织机构代码
			str.append(" and u.org_code like '%"+map.get("org_code")+"%'");
		}
		if(Common.notEmpty(map.get("org_parent"))){  //父节点编码
			if(Common.dataBaseType()==1){
				str.append(" and ( u.org_parent='"+map.get("org_parent")
						+"' or u.ui_id='"+map.get("org_parent")+"')");
			}
			if(Common.dataBaseType()==2){   //oracle  迭代查询
				str.append(" start with u.ui_id ='" +map.get("org_parent")+
						"' connect by prior u.ui_id =u.org_parent");
			}
		}
		return str.toString();
	}	

	//properties文件中项目的前缀
		public String orgItem(HttpServletRequest request){
			State state=new BaseState();
			PropertiesManager manager=new PropertiesManager();
			List<Map<String, String>> map=orgDao.findAllItem();
			if(map==null){ 
				return state.addState(AppInfo.READ_PROPERTIES_ERROR,null);
			}
			return state.addState(AppInfo.SUCCESS,map);
		}

	
	
	public String selectAll(HttpServletRequest request){
		State state=new BaseState();
		return state.addState(AppInfo.SUCCESS,orgDao.selectAll());
	}
	
	public String checkOnly(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");   //相关条件
			Organization org=JSON.parseObject(st,Organization.class);
			return state.addState(AppInfo.SUCCESS,orgDao.checkOnly(org));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

}
