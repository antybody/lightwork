package com.baosight.iwater.system.define;

import java.util.HashMap;
import java.util.Map;

public final class AppInfo {
	
	public static final int SUCCESS = 0;
	public static final int NOT_FIND = 101;
	public static final int LOGIN_FAIL=102;
	public static final int NO_USER=103;
	public static final int PAS_ERROR=104;
	public static final int DEL_LINK_DATA=105;
	
	public static final int READ_PROPERTIES_ERROR=203;
	
	public static final int SESSTION_LOST=302;
	public static final int NO_PARAM=301;
	/*
	 * 1XX表示客户端的错误，  2XX表示服务端的错误
	 * unicode编码      可以自行添加所需要的错误    
	 */

	public static Map<Integer, String> info = new HashMap<Integer, String>(){{
		
		put( AppInfo.SUCCESS, "SUCCESS" );
		//操作的数据不存在 (对应的主键不存在)
		put( AppInfo.NOT_FIND, "\u64cd\u4f5c\u7684\u6570\u636e\u4e0d\u5b58\u5728");
		//用户名或密码不正确  (登录失败)
		put( AppInfo.LOGIN_FAIL, "\u7528\u6237\u540d\u6216\u5bc6\u7801\u4e0d\u6b63\u786e");
		//登录账号不存在
		put( AppInfo.NO_USER,"\u767b\u5f55\u8d26\u53f7\u4e0d\u5b58\u5728");
		//登录密码错误
		put( AppInfo.PAS_ERROR, "\u767b\u5f55\u5bc6\u7801\u9519\u8bef");
		
		//读取配置文件失败(找不到指定的properties文件 或者其他错误)
		put( AppInfo.READ_PROPERTIES_ERROR,"\u8bfb\u53d6\u914d\u7f6e\u6587\u4ef6\u5931\u8d25");
		
		//未获得所需要的参数(传入参数格式不正确 或者没有传入参数)
		put( AppInfo.NO_PARAM, "\u672a\u83b7\u5f97\u6240\u9700\u8981\u7684\u53c2\u6570");
		
		//用户登录失效，请刷新后重新登录
		put( AppInfo.SESSTION_LOST,"\u7528\u6237\u767b\u5f55\u5931\u6548\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u65b0\u767b\u5f55");
		
		//存在关联数据，拒绝删除 
		put( AppInfo.DEL_LINK_DATA, "\u5b58\u5728\u5173\u8054\u6570\u636e\uff0c\u62d2\u7edd\u5220\u9664");
	}};
	
	public static String getStateInfo ( int key ) {
		return AppInfo.info.get( key );
	}
	
}
