package com.baosight.iwater.system.define.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.State;

public class BaseState implements State {
	private Map<String,Object> map=new HashMap<String,Object>();
	
	/**
	 * 添加  返回码 (AppInfo)  
	 * 描述信息与之同步
	 * @param code
	 */
	public void addCode(int code){
		map.put("code",code);
		map.put("message",AppInfo.info.get(code));
	}
	
	/**
	 * 添加  元数据
	 * @param obj  
	 */
	public void addData(Object obj){
		map.put("data",JSON.toJSON(obj));
	}
	
	/**
	 * 添加数据并得到返回的json字符串
	 * @param code  状态码
	 * @param obj	元数据 (类的对象  类对象的集合)
	 * @return
	 */
	public String addState(int code,Object obj){
		addCode(code);
		addData(obj);
		return getJson();
	}
	
	/**
	 * 添加  额外数据
	 * @param key 键
	 * @param obj 值
	 */
	public void addInfo(String key,Object obj){
		map.put(key,obj);
	}
	
	/**
	 * (在设置数据后)
	 * 得到返回的信息字符串
	 * @return
	 */
	public String getJson(){
		return JSON.toJSONString(map);
	}

	//下面是一些帮助方法 
	
	
	/**
	 * 将传入的对象 转化为json格式的字符串
	 * @param obj
	 * @return
	 */
	public String toJSONString(Object obj){
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 将json 字符串 转换为 Map<String,Object>
	 * 数据格式错误 返回null
	 * @param json
	 * @return
	 */
	public Map<String,Object> JsonToMap(String json){
		try{
			JSONObject jsons=JSONObject.parseObject(json);
			if(jsons==null){
				return null;
			}
		    Map<String,Object> otherMap=new HashMap<String,Object>();
		    for(String str:jsons.keySet()){
		    	otherMap.put(str,jsons.get(str));
		    }
		    return otherMap;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将类的对象  转换为Map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> ObjToMap(Object obj) {  
		try{
			Map<String, String> mapValue = new HashMap<String, String>();  
	        Class<?> cls = obj.getClass();  
	        Field[] fields = cls.getDeclaredFields();  
	        for (Field field : fields) {  
	            String name = field.getName();  
	            String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());  
	            Method methodGet = cls.getDeclaredMethod(strGet);  
	            Object object = methodGet.invoke(obj);  
	            String value = object != null ? object.toString() : "";  
	            mapValue.put(name, value);  
	        }  
	        return mapValue;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}  
    } 
	/* utf-8
	 * 返回值定义：
		Json返回
		{
  			code:0,
  			message:’success’,
  			data:{key1:val1,key2:val2,key3:val3}
		}
		code: 返回码，0表示成功，非0表示各种不同的错误
		message: 描述信息，成功时为"success"，错误时则是错误信息
		data: 成功时返回的数据，类型为对象或数组

		1XX表示客户端的错误，2XX表示服务端的错误
	 * 
	 */
	
}
