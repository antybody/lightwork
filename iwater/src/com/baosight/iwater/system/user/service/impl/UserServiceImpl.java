package com.baosight.iwater.system.user.service.impl;


import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.Common;
import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.menu.pojo.Menu;
import com.baosight.iwater.system.user.dao.UserMapper;
import com.baosight.iwater.system.user.pojo.RelUserOrg;
import com.baosight.iwater.system.user.pojo.RelUserRole;
import com.baosight.iwater.system.user.pojo.User;
import com.baosight.iwater.system.user.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserMapper userDao;

	public String deleteByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			User user=JSON.parseObject(stk,User.class);
			if(user==null || user.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);  //传入数据格式不正确
			}
			user.setUp_date(Common.getTime());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				user.setUser_info(obj.toString());
			}
			//进行删除
			int flag=userDao.deleteByPrimaryKey(user);
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

	@Transactional
	public String insert(HttpServletRequest request) {
		State state=new BaseState();
		try{
			//添加用户数据
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			User user=JSON.parseObject(stk,User.class);
			if(user==null){
				return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
			}
			user.setUi_id(Common.getUUID());
			user.setAdd_date(Common.getTime());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				user.setUser_info(obj.toString());
			}
			if(user.getUser_pwd()!=null){     //加密  
				user.setUser_pwd(Common.getPwd(user.getUser_pwd()));
			}
			userDao.insert(user);
			
			//添加用户组织机构关系
			Map<String,Object> map=state.JsonToMap(stk);
			String str=map.get("org_id").toString();
			String user_code=map.get("user_code").toString();
			String st[]={};
			if(str.length()>2){
				st=str.substring(1, str.length()-1).split(",");
			}
			// 测试回滚代码
//			String string  = null;
//		    if(string.equals("")) {
//		        int i = 0;
//		    }
			
			RelUserOrg relOrg=null;
			for(int i=0;i<st.length;i++){
				String org_code=st[i].substring(1,st[i].length()-1);
				relOrg=new RelUserOrg();
				relOrg.setUser_code(user_code);
				relOrg.setOrg_code(org_code);
				relOrg.setGmt_date(Common.getTime());
				userDao.insertUserOrg(relOrg);
			}
			
			//添加用户角色关系
			String stg=map.get("role_id").toString();
			String sg[]={};
			if(stg.length()>2){
				sg=stg.substring(1, stg.length()-1).split(",");
			}
			RelUserRole rel=null;
			for(int i=0;i<sg.length;i++){
				String role_code=sg[i].substring(1,sg[i].length()-1);
				rel=new RelUserRole();
				rel.setRole_code(role_code);
				rel.setUser_code(user_code);
				rel.setGmt_date(Common.getTime());
				userDao.insertUserRole(rel);
			}
			
			return state.addState(AppInfo.SUCCESS,null);         //添加成功
		}
		catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
			//throw new RuntimeException();
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
			User user=userDao.selectByPrimaryKey(map.get("ui_id").toString());
			if(user!=null){
				StringBuffer orgName=new StringBuffer("");
				StringBuffer roleName=new StringBuffer("");
				
				//组织机构
				JSONArray orgArray=new JSONArray();
				JSONObject orgObj=null;
				List<RelUserOrg> orgList=userDao.sel_user_org(user.getUser_code());
				if(orgList!=null){
					for(int j=0;j<orgList.size();j++){
						if(orgList.get(j).getOrg_zhname()!=null){
							orgName.append(orgList.get(j).getOrg_zhname());
							orgName.append(" ");
						}
						
						orgObj=new JSONObject();
						orgObj.put("org_code", orgList.get(j).getOrg_code());
						orgObj.put("org_zhname",orgList.get(j).getOrg_zhname());
						orgArray.add(j, orgObj);
					}
				}
				
				//角色
				JSONArray roleArray=new JSONArray();
				JSONObject roleObj=null;
				List<RelUserRole> roleList=userDao.sel_user_role(user.getUser_code());
				if(roleList!=null){
					for(int z=0;z<roleList.size();z++){
						if(roleList.get(z).getRole_zhname()!=null){
							roleName.append(roleList.get(z).getRole_zhname());
							roleName.append(" ");
						}
						
						roleObj=new JSONObject();
						roleObj.put("role_code",roleList.get(z).getRole_code());
						roleObj.put("role_zhname",roleList.get(z).getRole_zhname());
						roleArray.add(z,roleObj);
					}
				}
				
				user.setOrg_zhname(orgName.toString());
				user.setOrg_id(orgArray.toString());
				user.setRole_id(roleArray.toJSONString());
				user.setRole_zhname(roleName.toString());
				/*
				 * 参数说明
				 * role_id   存放完整的用户角色信息   (role_code,role_zhname键值对)
				 * org_id	存放完整的用户组织机构信息(org_code,org_zhname键值对)
				 * role_zhname   拼接的角色字符串(用于显示)
				 * org_zhname    拼接的组织机构字符串(用于显示)
				 */
			}
			return state.addState(AppInfo.SUCCESS, user);
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
	@Transactional
	public String updateByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			User user=JSON.parseObject(stk,User.class);
			if(user==null || user.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);      //传入数据格式不正确
			}
			if(user.getUser_pwd()!=null){		//进行加密
				user.setUser_pwd(Common.getPwd(user.getUser_pwd()));
			}
			user.setUp_date(Common.getTime());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				user.setUser_info(obj.toString());
			}
			//进行修改
			int flag=userDao.updateByPrimaryKey(user);
			
			//修改用户的组织机构关系
			//1.首先删除用户的组织机构关系
			RelUserOrg relUserOrg=new RelUserOrg();
			relUserOrg.setGmt_date(Common.getTime());
			relUserOrg.setUser_code(user.getUser_code());
			userDao.del_user_org(relUserOrg);
			
			//2.重新添加用户组织机构关系
			String st[]={};
			if(user.getOrg_id()!=null && user.getOrg_id().length()>2){
				st=user.getOrg_id().substring(1, user.getOrg_id().length()-1).split(",");
			}
			for(int i=0;i<st.length;i++){
				if(st[i]!=null && !"\"\"".equals(st[i].trim())){
					String org_code=st[i].substring(1,st[i].length()-1);
					RelUserOrg rel=new RelUserOrg();
					rel.setUser_code(user.getUser_code());
					rel.setOrg_code(org_code);
					rel.setGmt_date(Common.getTime());
					userDao.insertUserOrg(rel);
				}
			}
				
			//修改用户角色关系
			//1.首先删除用户之前的角色关系
			RelUserRole relUserRole=new RelUserRole();
			relUserRole.setGmt_date(Common.getTime());
			relUserRole.setUser_code(user.getUser_code());
			userDao.del_user_role(relUserRole);
			
			//2.重新添加用户角色关系
			String str[]={};
			if(user.getRole_id()!=null && user.getRole_id().length()>2){
				str=user.getRole_id().substring(1, user.getRole_id().length()-1).split(",");
			}
			for(int i=0;i<str.length;i++){
				if(str[i]!=null && !"\"\"".equals(str[i].trim())){
					String role_code=str[i].substring(1,str[i].length()-1);
					RelUserRole relRole=new RelUserRole();
					relRole.setUser_code(user.getUser_code());
					relRole.setRole_code(role_code);
					relRole.setGmt_date(Common.getTime());
					userDao.insertUserRole(relRole);
				}
			}
			
			if(flag==1){
				return state.addState(AppInfo.SUCCESS,null);       //修改成功
			}
			return state.addState(AppInfo.NOT_FIND,null);			//修改主键不存在
		}
		catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

	public String findAll(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("param"),"utf-8");     //分页参数
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");   //查询条件
			PageInfo pageInfo=JSON.parseObject(stk,PageInfo.class);
			if(pageInfo==null){
				return state.addState(AppInfo.NO_PARAM,null);        //传入数据格式不正确
			}
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("start",pageInfo.getStart());
			map.put("pageSize",pageInfo.getPageSize());
			map.put("end",pageInfo.getStart()+pageInfo.getPageSize());  //oracle分页需要
			if(st!=null && !"\"\"".equals(st.trim())){
				map.putAll(state.JsonToMap(st));
			}
			map.put("queryItem", getQueryItem(map));  //通用查询条件
			pageInfo.setTotalCount(userDao.findAllSize(map));
			
			//查询每个用户的 组织机构  拼接
			List<User> list=null;
			//判断当前数据库类型
			if(Common.dataBaseType()==1){
				list=userDao.findAllSql(map);
			}
			if(Common.dataBaseType()==2){
				list=userDao.findAllOrcl(map);
			}
			
			for(int i=0;i<list.size();i++){
				StringBuffer ogStr=new StringBuffer("");
				List<RelUserOrg> orgList=userDao.sel_user_org(list.get(i).getUser_code());
				if(orgList!=null){
					for(int j=0;j<orgList.size();j++){
						if(orgList.get(j).getOrg_zhname()!=null){
							ogStr.append(orgList.get(j).getOrg_zhname());
							ogStr.append(" ");
						}
					}
				}
				list.get(i).setOrg_zhname(ogStr.toString());
			}
			
			//返回 基础信息
			state.addState(AppInfo.SUCCESS,list);
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
		StringBuffer str=new StringBuffer(" and s.del_modified=1");
		if(Common.notEmpty(map.get("user_name"))){  //用户名称
			str.append(" and s.user_name like '%"+map.get("user_name")+"%'");
		}
		if(Common.notEmpty(map.get("user_code"))){   //用户代码
			str.append(" and s.user_code like '%"+map.get("user_code")+"%'");
		}
		if(Common.notEmpty(map.get("org_zhname"))){  //机构中文名称
			str.append(" and s.user_code in" +
					"	(select g.user_code from rel_user_org g " +
					"	left join sys_org y on g.org_code=y.org_code" +
					"	where  y.org_zhname like '%"+map.get("org_zhname")+"%' and g.del_modified=1 )");
		}
		if(Common.notEmpty(map.get("role_zhname"))){  //角色中文名称
			str.append("and s.user_code in" +
					"	(select user_code from rel_user_role e" +
					"	left join sys_role y on e.role_code=y.role_code " +
					"	where y.role_zhname like '%"+map.get("role_zhname")+"%' and e.del_modified=1 ) ");
		}
		if(Common.notEmpty(map.get("is_manager"))){	  //是否ROLE_ADMIN (最高管理员)
			if("0".equals(map.get("is_manager").toString())){  //是
				str.append(" and s.user_code in " +
						" (select user_code from REL_USER_ROLE where role_code='ROLE_ADMIN')");
			}
			if("1".equals(map.get("is_manager").toString())){	//不是
				str.append(" and s.user_code not in " +
						" (select user_code from REL_USER_ROLE where role_code='ROLE_ADMIN')");
			}
		}
		return str.toString();
	}			
	
	public String checkOnly(HttpServletRequest request){
		State state=new BaseState();
		try{
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");   //相关条件
			User user=JSON.parseObject(st,User.class);
			return state.addState(AppInfo.SUCCESS,userDao.checkOnly(user));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
}
