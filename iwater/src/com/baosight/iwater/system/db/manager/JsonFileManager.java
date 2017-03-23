package com.baosight.iwater.system.db.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

public class JsonFileManager {
//	public static void main(String[] args) throws Exception {
//		JSONObject appObject = new JSONObject();
//		appObject.put("type", "type");
//		appObject.put("username", "username");
//		appObject.put("password", "password");
//		appObject.put("uniqueMark", "uniqueMark");
//		appObject.put("userId", "userId");
//		appObject.put("sendTime", "sendTime");
//		// 文件存储路径
//		String path = System.getProperty("catalina.home") + "\\jsondata.json";
//		// 将数据存储到json文件
//		writeFile(path, appObject);
//	}

	/**
	 * 把Json格式的字符串写到文件
	 * 
	 * @param filePath
	 * @param sets
	 * @throws IOException
	 */
	public static void writeFile(String filePath, JSONObject appObject)
			throws IOException {
		File file = new File(filePath);
		// 如果文件不存在、则创建该文件
		if (!file.exists()) {
			file.createNewFile();
		}
		JSONObject jsonObj;
		// 获取JSON数据字符串
		String str = ReadFile(filePath);
		if (str.length() > 0) {
			// 获取JSON对象
			jsonObj = JSONObject.fromObject(str);
			// 获取JSON集合对象
			JSONArray arr = jsonObj.getJSONArray("appdata");
			arr.add(appObject);
		} else {
			JSONArray appArray = new JSONArray();
			appArray.add(appObject);
			jsonObj = new JSONObject();
			jsonObj.element("appdata", appArray);
		}
		FileWriter fileWriter = new FileWriter(filePath);
		PrintWriter out = new PrintWriter(fileWriter);
		out.write(jsonObj.toString());
		out.println();
		fileWriter.close();
		out.close();
	}

//	/**
//	 * 获取全部数据
//	 * 
//	 * @param filePath
//	 * @param sets
//	 * @throws IOException
//	 */
//	public List<appDataEntity> getAllDataByHttp() throws Exception {
//		// 文件存储路径
//		String path = System.getProperty("catalina.home") + "\\jsondata.json";
//		File dataFile = new File(path);
//		// 如果不存在，创建新文件
//		if (!dataFile.exists()) {
//			dataFile.createNewFile();
//		}
//		List<appDataEntity> appDataList = new ArrayList<appDataEntity>();
//		// 获取JSON数据的字符串
//		String JsonContext = ReadFile(path);
//		JSONArray jsonArray;
//		// 判断文件内是否有数据
//		if (JsonContext.length() > 0) {
//			// 获取文件JSON对象
//			JSONObject json = JSONObject.fromObject(JsonContext);
//			// 获取文件JSON数组对象
//			jsonArray = json.getJSONArray("appdata");
//			int size = jsonArray.size();
//			for (int i = 0; i < size; i++) {
//				appDataEntity appEntity = new appDataEntity();
//				JSONObject jsonObject = jsonArray.getJSONObject(i);
//				appEntity.setType(jsonObject.get("type").toString());
//				appEntity.setUserId(jsonObject.getString("userId"));
//				appEntity.setUsername(jsonObject.getString("username"));
//				appEntity.setPassword(jsonObject.getString("password"));
//				appEntity.setUniqueMark(jsonObject.getString("uniqueMark"));
//				appEntity.setSendTime(jsonObject.getString("sendTime"));
//				appDataList.add(0, appEntity);
//			}
//		}
//		return appDataList;
//	}

	/**
	 * 读JSON文件，返回字符串
	 * 
	 * @param filePath
	 * @param sets
	 * @throws IOException
	 */
	public static String ReadFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		// 构造最后返回的json串
		String laststr = "";
		try {
			// 以行为单位读取文件内容，一次读一整行：
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 拼接数据信息
				laststr = laststr + tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;
	}
}
