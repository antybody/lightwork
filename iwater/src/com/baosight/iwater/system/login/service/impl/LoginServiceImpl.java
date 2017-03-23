package com.baosight.iwater.system.login.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.login.dao.LoginMapper;
import com.baosight.iwater.system.login.pojo.Power;
import com.baosight.iwater.system.login.pojo.User;
import com.baosight.iwater.system.login.service.ILoginService;
//下面注解里面的参数  是给实例化后的对象起名
@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	@Resource
	private LoginMapper loginDao;
	
	/**
	 *传入用户名  密码
	 *登录成功  用户对象
	 *登录失败  null
	 */
	public String selectByUserCode(HttpServletRequest request) {
		State state=new BaseState();
		try{
			User user=null;
			//获得传入数据  转换为map
			String params=request.getParameter("params");
			Map<String,Object> map=state.JsonToMap(params);
			if(map==null || map.get("user_code")==null || map.get("user_pwd")==null){
				return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
			}
			String user_code=map.get("user_code").toString();
			String user_pwd=map.get("user_pwd").toString();
			//返回信息
			user=loginDao.selectByUserCode(user_code);
			if(user==null){       				
				return state.addState(AppInfo.NO_USER,null);   //登录账号不存在
			}
			
			PasswordEncoder encoder=new BCryptPasswordEncoder();
			boolean flag=encoder.matches(user_pwd, user.getUser_pwd());
			if(flag){   	
				return state.addState(AppInfo.SUCCESS,user);  //登录成功
			}
			else{							
				return state.addState(AppInfo.PAS_ERROR,null); //密码不正确
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
	public String getMenu(HttpServletRequest request){
		State state=new BaseState();
		try{
			if(request.getSession().getAttribute("USER_INFO")==null){
				return state.addState(AppInfo.NO_PARAM,null);        //传入数据格式不正确
			}
			
			String user_name=request.getSession().getAttribute("USER_INFO").toString();
			
			List<Map<String,String>> list=null;  //菜单信息
			if(loginDao.isAdmin(user_name)==0){	//非最高管理员
				list=loginDao.getUserMenu(user_name);
			}
			else{		//最高管理员
				list=loginDao.getAllMenu();
			}
			
			/*
			 *这个算法是基于查询的数据结果而言的  局限性：只能用于二级菜单  不能用于三级菜单
			 *参考数据:
			 *ui_id  menu_zhname menu_url menu_class pic_class parent_menu
			 */
			
			//uiMap   键 :父级菜单的ui_id    值 :menu
			List<Map<String,String>> uiList=new ArrayList<Map<String,String>>(); 
			for(int i=0;i<list.size();i++){
				if(list.get(i).get("parent_menu")!=null
						&& "0".equals(list.get(i).get("parent_menu").toString())){
					uiList.add(list.get(i)); 
				}
			}
			
			//menuList 主要返回的菜单数据
			List<Map<String,Object>> menuList=new ArrayList<Map<String,Object>>();
			
			//循环父级菜单   向其加入子级菜单的数据 sonMenu
			for(int z=0;z<uiList.size();z++){
				Map<String,Object> menuMap=new HashMap<String,Object>();
				//子层数据
				List<Map<String,String>> sonList=new ArrayList<Map<String,String>>();
				for(int i=0;i<list.size();i++){
					if(list.get(i).get("parent_menu")!=null &&
							uiList.get(z).get("ui_id").equals(list.get(i).get("parent_menu").toString())){
						Map<String,String> sonMenu=new HashMap<String,String>(); 
						sonMenu.put("menu_url", list.get(i).get("menu_url"));
						sonMenu.put("cmenu_name", list.get(i).get("menu_zhname"));
						sonMenu.put("c_class", list.get(i).get("pic_class"));
						sonList.add(sonMenu);
					}
				}
				menuMap.put("menu_class",uiList.get(z).get("menu_class"));
				menuMap.put("pic_class",uiList.get(z).get("pic_class"));
				menuMap.put("pmenu_name",uiList.get(z).get("menu_zhname"));
				menuMap.put("child_menu",sonList);
				menuList.add(menuMap);
			}
			
			Map<String,Object> infoMap=new HashMap<String,Object>();
			infoMap.put("username",user_name);
			infoMap.put("menu_arr", menuList);
			return state.addState(AppInfo.SUCCESS,infoMap);
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
	/**
	 * 查询访问路径 对应的角色
	 * 内层数据格式:
	 * 0: 具体路径
	 * 1-n:  可访问该路径的角色
	 */
	public List<List<String>> selRolePower(){
		//返回的数据集合
		List<List<String>> rolePowerList=new ArrayList<List<String>>();
		List<Power> list=loginDao.selectRolePower();
		List<String> powerList=null;
		for(int i=0;i<list.size();i++){
			if("001".equals(list.get(i).getPower_type())
					|| "002".equals(list.get(i).getPower_type())){  //接口 或者 菜单
				int index=listLocation(rolePowerList, list.get(i).getPower_url());
				if(index==-1){	//数据集合中没有此路径
					powerList=new ArrayList<String>();
					powerList.add(list.get(i).getPower_url());
					powerList.add(list.get(i).getRole_code());
					rolePowerList.add(powerList);
				}
				else{	//数据集合中已经存在此路径
					int reFlag=0;
					for(int j=0;j<rolePowerList.get(index).size();j++){
						if(list.get(i).getRole_code().equals(rolePowerList.get(index).get(j))){
							reFlag=1;
						}
					}
					if(reFlag==0){  //在路径的访问角色中不重复
						rolePowerList.get(index).add(list.get(i).getRole_code());
					}
				}
			}
			
			if("004".equals(list.get(i).getPower_type())){	//页面
				if(!"".equals(list.get(i).getPower_url().trim())){
					String stk[]=list.get(i).getPower_url().split(",");
					for(String skz:stk){
						int index=listLocation(rolePowerList,skz);
						if(index==-1){  //数据集合中并不存在此路径
							powerList=new ArrayList<String>();
							powerList.add(skz);
							powerList.add(list.get(i).getRole_code());
							rolePowerList.add(powerList);
						}
						else{		//数据集合中已经存在此路径
							int reFlag=0;
							for(int j=0;j<rolePowerList.get(index).size();j++){
								if(list.get(i).getRole_code().equals(rolePowerList.get(index).get(j))){
									reFlag=1;
								}
							}
							if(reFlag==0){
								rolePowerList.get(index).add(list.get(i).getRole_code());
							}
						}
					}
					
					/*JSONObject obj=null;
					try{
						obj=JSON.parseObject(list.get(i).getPower_url());
					}
					catch(Exception e){
						//数据格式错误   不是json格式
						e.printStackTrace();
					}
					if(obj!=null){   //正确的json格式
						for(String key:obj.keySet()){
							int index=listLocation(rolePowerList,obj.get(key).toString());
							if(index==-1){  //数据集合中并不存在此路径
								powerList=new ArrayList<String>();
								powerList.add(obj.get(key).toString());
								powerList.add(list.get(i).getRole_code());
								rolePowerList.add(powerList);
							}
							else{		//数据集合中已经存在此路径
								int reFlag=0;
								for(int j=0;j<rolePowerList.get(index).size();j++){
									if(list.get(i).getRole_code().equals(rolePowerList.get(index).get(j))){
										reFlag=1;
									}
								}
								if(reFlag==0){
									rolePowerList.get(index).add(list.get(i).getRole_code());
								}
							}
							
						}
						
					}*/
				}
				
			}
			
		}
		return rolePowerList;
	}
	
	/**
	 * 返回路径在集合中的位置
	 * 集合中没有该路径时 返回-1
	 * @param list
	 * @param str
	 * @return
	 */
	public int listLocation(List<List<String>> list,String str){
		for(int i=0;i<list.size();i++){
			if(list.get(i).size()!=0 
					&& str.equals(list.get(i).get(0))){
				return i;
			}
		}
		return -1;
	}
}
