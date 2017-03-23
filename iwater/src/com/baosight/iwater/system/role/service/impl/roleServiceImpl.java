package com.baosight.iwater.system.role.service.impl;

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
import com.baosight.iwater.system.role.dao.RoleMapper;
import com.baosight.iwater.system.role.pojo.RelRolePower;
import com.baosight.iwater.system.role.pojo.Role;
import com.baosight.iwater.system.role.service.IRoleService;
import com.baosight.iwater.system.user.pojo.User;

@Service("roleService")
public class roleServiceImpl implements IRoleService {
	
	@Resource
	private RoleMapper roleDao;
	
	public String deleteByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Role role=JSON.parseObject(stk,Role.class);
			if(role==null || role.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);  //传入数据格式不正确
			}
			role.setUp_date(Common.getTime());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				role.setUser_info(obj.toString());
			}
			//进行删除
			int flag=roleDao.deleteByPrimaryKey(role);
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
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Role role=JSON.parseObject(stk,Role.class);
			if(role==null){
				return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
			}
			
			role.setAdd_date(Common.getTime());
			role.setUi_id(Common.getUUID());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				role.setUser_info(obj.toString());
			}
			roleDao.insert(role);
			
			//添加角色权限关系
			JSONObject json=JSON.parseObject(stk);
			JSONObject jsonObj=JSON.parseObject(json.get("rel_role_power").toString());
			JSONArray array=JSON.parseArray(jsonObj.get("inter").toString());
			
			//1.添加接口权限
			RelRolePower rrp=null;
			for(Object inter: array){
				if(inter!=null){
					rrp=new RelRolePower();
					rrp.setGmt_date(Common.getTime());
					rrp.setPower_code(inter.toString());
					rrp.setPower_type("001");
					rrp.setRole_code(role.getRole_code());
					roleDao.insertRolePower(rrp);
				}
			}
			
			//2.添加菜单权限  以及对应的页面 按钮
			JSONObject menuObj=JSON.parseObject(jsonObj.get("menu").toString());
			for(String menu:menuObj.keySet()){
				if(menu!=null){
					rrp=new RelRolePower();
					rrp.setGmt_date(Common.getTime());
					rrp.setPower_code(menu);
					rrp.setPower_type("002");
					rrp.setRole_code(role.getRole_code());
					roleDao.insertRolePower(rrp);   //2.1  添加菜单
					if(menuObj.get(menu)!=null 
							&& !"".equals(menuObj.get(menu))){
						//2.2  添加菜单对应的页面 和按钮
						JSONObject pageObj=JSON.parseObject(menuObj.get(menu).toString());
						for(String page:pageObj.keySet()){
							rrp=new RelRolePower();
							rrp.setGmt_date(Common.getTime());
							rrp.setPower_code(page);
							rrp.setPower_type("004");
							rrp.setRole_code(role.getRole_code());
							rrp.setPower_owner(menu);     //页面所属菜单
							rrp.setPower_follower(pageObj.get(page).toString()); //页面的按钮
							roleDao.insertRolePower(rrp);   
						}
					}
				}
			}
			return state.addState(AppInfo.SUCCESS,null);         //添加成功
		}
		catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
			
			//查询角色的权限关系
			List<RelRolePower> list=roleDao.selectRolePower(map.get("ui_id").toString());
			JSONObject mainJson=new JSONObject();  //主体数据
			JSONArray interJson=new JSONArray();  //接口数据
			JSONObject menuJson=new JSONObject();  //菜单  页面  按钮数据
			for(int i=0;i<list.size();i++){
				if("001".equals(list.get(i).getPower_type())){  //接口
					interJson.add(list.get(i).getPower_code());
				}
				if("002".equals(list.get(i).getPower_type())){  //菜单
					JSONObject pageJson=new JSONObject();  //页面数据
					//查找菜单下的页面
					for(int j=0;j<list.size();j++){
						if(list.get(i).getPower_code().equals(list.get(j).getPower_owner())){
							pageJson.put(list.get(j).getPower_code(),list.get(j).getPower_follower());
						}
					}
					menuJson.put(list.get(i).getPower_code(),pageJson);
				}
			}
			//合成主体数据
			mainJson.put("inter",interJson);
			mainJson.put("menu",menuJson);
			Role role= roleDao.selectByPrimaryKey(map.get("ui_id").toString());
			role.setRel_role_power(mainJson.toJSONString());
			return state.addState(AppInfo.SUCCESS,role);
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
			Role role=JSON.parseObject(stk,Role.class);
			if(role==null || role.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);      //传入数据格式不正确
			}
			role.setUp_date(Common.getTime());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				role.setUser_info(obj.toString());
			}
			//进行修改
			int flag=roleDao.updateByPrimaryKey(role);
			
			//1.首先删除角色所有的权限关系
			RelRolePower rrp=null;
			rrp=new RelRolePower();
			rrp.setRole_code(role.getRole_code());
			rrp.setGmt_date(Common.getTime());
			roleDao.deleteRolePower(rrp);
			
			//2.重新添加角色权限关系
			JSONObject json=JSON.parseObject(stk);
			JSONObject jsonObj=JSON.parseObject(json.get("rel_role_power").toString());
			JSONArray array=JSON.parseArray(jsonObj.get("inter").toString());
			
			//2.1.添加接口权限
			for(Object inter: array){
				if(inter!=null){
					rrp=new RelRolePower();
					rrp.setGmt_date(Common.getTime());
					rrp.setPower_code(inter.toString());
					rrp.setPower_type("001");
					rrp.setRole_code(role.getRole_code());
					roleDao.insertRolePower(rrp);
				}
			}
			
			//2.2.添加菜单权限  以及对应的页面 按钮
			JSONObject menuObj=JSON.parseObject(jsonObj.get("menu").toString());
			for(String menu:menuObj.keySet()){
				if(menu!=null){
					rrp=new RelRolePower();
					rrp.setGmt_date(Common.getTime());
					rrp.setPower_code(menu);
					rrp.setPower_type("002");
					rrp.setRole_code(role.getRole_code());
					roleDao.insertRolePower(rrp);   //2.1  添加菜单
					if(menuObj.get(menu)!=null 
							&& !"".equals(menuObj.get(menu))){
						//2.2  添加菜单对应的页面 和按钮
						JSONObject pageObj=JSON.parseObject(menuObj.get(menu).toString());
						for(String page:pageObj.keySet()){
							rrp=new RelRolePower();
							rrp.setGmt_date(Common.getTime());
							rrp.setPower_code(page);
							rrp.setPower_type("004");
							rrp.setRole_code(role.getRole_code());
							rrp.setPower_owner(menu);     //页面所属菜单
							rrp.setPower_follower(pageObj.get(page).toString()); //页面的按钮
							roleDao.insertRolePower(rrp);   
						}
					}
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
	
	public String getAll(HttpServletRequest request){
		State state=new BaseState();
		return state.addState(AppInfo.SUCCESS,roleDao.findAll());
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
			map.put("end",pageInfo.getStart()+pageInfo.getPageSize()); //oracle  分页参数
			if(st!=null && !"\"\"".equals(st.trim())){
				map.putAll(state.JsonToMap(st));
			}
			
			map.put("queryItem", getQueryItem(map));  //通用查询条件
			pageInfo.setTotalCount(roleDao.findAllSize(map));
			//返回 基础信息
			if(Common.dataBaseType()==1){  //判断当前数据库类型
				state.addState(AppInfo.SUCCESS,roleDao.findAllSql(map));
			}
			if(Common.dataBaseType()==2){
				state.addState(AppInfo.SUCCESS,roleDao.findAllOrcl(map));
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
		StringBuffer str=new StringBuffer(" and s.is_del=1  ");
		if(Common.notEmpty(map.get("role_zhname"))){  //角色名称
			str.append(" and s.role_zhname like '%"+map.get("role_zhname")+"%'");
		}
		if(Common.notEmpty(map.get("role_code"))){  //角色代码
			str.append(" and s.role_code like '%"+map.get("role_code")+"%'");
		}
		if(Common.notEmpty(map.get("role_type"))){  //角色类型
			String type_str[]=map.get("role_type").toString().split("-");
			if(type_str.length!=0){
				str.append(" and (");
			}
			for(int i=0;i<type_str.length;i++){
				if(i==0){
					str.append(" s.role_type ='"+type_str[i]+"'");
				}
				else{
					str.append(" or s.role_type ='"+type_str[i]+"'");
				}
			}
			if(type_str.length!=0){
				str.append(")");
			}
		}
		return str.toString();
	}		
	
	public String getRolePower(HttpServletRequest request){
		State state=new BaseState();
		try{
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");	//查询条件
			RelRolePower rrp=JSON.parseObject(st,RelRolePower.class);
			if(rrp==null){
				return state.addState(AppInfo.NO_PARAM,null);    //传入数据格式不正确
			}
			return state.addState(AppInfo.SUCCESS,roleDao.sel_role_power(rrp.getRole_code()));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);    //传入数据格式不正确
		}
	}
	
	public String checkOnly(HttpServletRequest request){
		State state=new BaseState();
		try{
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");   //相关条件
			Role role=JSON.parseObject(st,Role.class);
			return state.addState(AppInfo.SUCCESS,roleDao.checkOnly(role));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
	public String selRoleUser(HttpServletRequest request){
		State state=new BaseState();
		try{
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");   //相关条件
			Map<String,Object> map=state.JsonToMap(st);
			return state.addState(AppInfo.SUCCESS,roleDao.sel_role_user(map.get("role_code").toString()));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
}
