package com.baosight.iwater.system.power.service.impl;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.Common;
import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.organization.pojo.Organization;
import com.baosight.iwater.system.power.dao.PowerMapper;
import com.baosight.iwater.system.power.pojo.Power;
import com.baosight.iwater.system.power.service.IPowerService;
import com.baosight.iwater.system.user.pojo.User;
//下面注解里面的参数  是给实例化后的对象起名
@Service("powerService")
public class powerServiceImpl implements IPowerService {
	
	@Resource
	private PowerMapper powerDao;
	
	public String deleteByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Power power=JSON.parseObject(stk,Power.class);
			if(power==null || power.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);  //传入数据格式不正确
			}
			power.setUp_date(Common.getTime());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				power.setUser_info(obj.toString());
			}
			//进行删除
			int flag=powerDao.deleteByPrimaryKey(power);
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
			Power power=JSON.parseObject(stk,Power.class);
			if(power==null){
				return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
			}
			power.setAdd_date(Common.getTime());
			power.setUi_id(Common.getUUID());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				power.setUser_info(obj.toString());
			}
			powerDao.insert(power);
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
			return state.addState(AppInfo.SUCCESS, powerDao.selectByPrimaryKey(map.get("ui_id").toString()));
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
			Power power=JSON.parseObject(stk,Power.class);
			if(power==null || power.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);      //传入数据格式不正确
			}
			power.setUp_date(Common.getTime());
			Object obj=request.getSession().getAttribute("USER_INFO");  //添加操作者信息
			if(obj!=null){
				power.setUser_info(obj.toString());
			}
			//进行修改
			int flag=powerDao.updateByPrimaryKey(power);
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
			String stk=URLDecoder.decode(request.getParameter("param"),"utf-8");     //分页参数
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");   //查询条件
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
			pageInfo.setTotalCount(powerDao.findAllSize(map));
			//添加基本信息
			if(Common.dataBaseType()==1){ //判断当前数据库类型
				state.addState(AppInfo.SUCCESS,powerDao.findAllSql(map));
			}
			if(Common.dataBaseType()==2){
				state.addState(AppInfo.SUCCESS,powerDao.findAllOrcl(map));
			}
			//添加分页信息
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
		StringBuffer str=new StringBuffer(" and s.del_modified=1 ");
		if(Common.notEmpty(map.get("power_name"))){  //权限资源名称
			str.append(" and s.power_name like '%"+map.get("power_name")+"%'");
		}
		if(Common.notEmpty(map.get("power_code"))){  //权限资源代码
			str.append(" and s.power_code like '%"+map.get("power_code")+"%'");
		}
		if(Common.notEmpty(map.get("power_owner"))){  //权限资源  父级
			str.append(" and s.power_owner like '%"+map.get("power_owner")+"%'");
		}
		if(Common.notEmpty(map.get("power_follower"))){  //权限资源权属
			str.append(" and s.power_follower like '%"+map.get("power_follower")+"%'");
		}
		if(Common.notEmpty(map.get("power_type"))){  //权限资源 类型
			String type[]=map.get("power_type").toString().split("-");
			if(type.length!=0){
				str.append(" and (");
			}
			for(int i=0;i<type.length;i++){
				if(i!=0){
					str.append(" or ");
				}
				str.append("s.power_type="+type[i]);
			}
			if(type.length!=0){
				str.append(" )");
			}
		}
		return str.toString();
	}	
	
	public String getAll(HttpServletRequest request){
		State state=new BaseState();
		return state.addState(AppInfo.SUCCESS,powerDao.findAll());
	}
	
	public String checkOnly(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");   //相关条件
			Power power=JSON.parseObject(st,Power.class);
			return state.addState(AppInfo.SUCCESS,powerDao.checkOnly(power));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

}
