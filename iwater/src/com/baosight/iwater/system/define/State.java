package com.baosight.iwater.system.define;

import java.util.Map;

/**
 * 处理返回数据 接口
 * @author liuwendong
 *
 */
public interface State {
	
	public void addCode(int code);
	
	public void addData(Object obj);
	
	public void addInfo(String key,Object obj);
	
	public String getJson();
	
	public Map<String,Object> JsonToMap(String json);
	
	public String addState(int code,Object obj);
	
	public Map<String, String> ObjToMap(Object obj);
}
