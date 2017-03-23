package com.baosight.iwater.system.dic.service.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.Common;
import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.dic.dao.DicMapper;
import com.baosight.iwater.system.dic.pojo.CodeDic;
import com.baosight.iwater.system.dic.service.IDicService;
import com.baosight.iwater.system.util.excel.arch.ExcelExporter;
import com.baosight.iwater.system.util.excel.interfaces.IExcelExporter;


@Service("dicService")
public class DicServiceImpl implements IDicService{

	@Resource
	private DicMapper dicDao;

	public String initLoad(HttpServletRequest request){
		State state=new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			CodeDic dic=JSON.parseObject(stk,CodeDic.class);
			String dic_id = dic.getDic_id();
			if(dic==null || dic_id==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			Map<String,String> map = new HashMap<String, String>();
			map.put("dic_id", dic_id);
//			List<Map<String,String>> list=dicDao.queryForTree(map);
//			JSONArray jsonArr = new JSONArray(list);
//			return state.addState(AppInfo.NOT_FIND,jsonArr);//返回查询结果
			return state.addState(AppInfo.SUCCESS, dicDao.queryForTree(map));
		}catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
		}
	}
	
	
	/**
	 * 初始化时查询树节点下一级子节点：有分页
	 */
	public String nextLevelNode(HttpServletRequest request){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			//获取分页相关参数
			PageInfo pageInfo=JSON.parseObject(stk,PageInfo.class);
			if(pageInfo==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			//获取节点id
			JSONObject params = new JSONObject(stk);
			String dic_code = params.getString("dic_code");
			if(params==null || dic_code==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("parent_code", dic_code);
			//设置分页参数：总记录数
			pageInfo.setTotalCount(dicDao.countChildnode(paramMap));
			//根据条件查询：分页
			paramMap.put("start", pageInfo.getStart());
			paramMap.put("pageSize", pageInfo.getPageSize());
			paramMap.put("end",pageInfo.getStart()+pageInfo.getPageSize());  //oracle 分页参数
			paramMap.put("paging", "enable");
			
			List<CodeDic> list=null;
			//判断当前数据库类型
			if(Common.dataBaseType()==1){
				list=dicDao.queryForChildnode(paramMap);
			}
			if(Common.dataBaseType()==2){
				list=dicDao.orclQueryForChildnode(paramMap);
			}
			
			//List<CodeDic> list = dicDao.queryForChildnode(paramMap);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("data", list);
			//总页数
			map.put("pageCount", pageInfo.getTotalPage());
			//当前页数
			map.put("curPageNo", pageInfo.getPageNo());
			//返回值
			return state.addState(AppInfo.SUCCESS, new JSONObject(map).toString());
		}catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
		}
	}
	
	
	/**
	 * 获取所有下级节点-不分页
	 */
	public String nextLevelNodeForAll(HttpServletRequest request){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			String parent_code = params.getString("parent_code");
			if(params==null || parent_code==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("parent_code", parent_code);
			//返回值
			return state.addState(AppInfo.SUCCESS, dicDao.queryForChildnode(map));
		}catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
		}
	}
	
	
	
	/**
	 * 查询节点是否存在
	 */
	public String queryForExist(HttpServletRequest request){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			String dic_code = params.getString("dic_code");
			if(params==null || dic_code==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			//状态字-新增时/修改时
			String checkType = params.getString("checkType");
			String dic_id = params.getString("dic_id");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("dic_code", dic_code);
			if("upd".equals(checkType)){
				//判断是否节点修改时
				map.put("dic_id", dic_id);
			}
			//返回值
			return state.addState(AppInfo.SUCCESS, dicDao.queryCountForCode(map));
		}catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
		}
	}
	
	
	/**
	 * 插入新节点
	 */
	public String insertForNew(HttpServletRequest request){
		State state = new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			Map<String, String> map = new HashMap<String, String>();
			map.put("dic_id", Common.getUUID());
			map.put("dic_code", (String)params.get("dic_code"));
			map.put("dic_name", (String)params.get("dic_name"));
			map.put("parent_code", (String)params.get("parent_code"));
			if(!params.isNull("dic_desc")){
				map.put("dic_desc", (String)params.get("dic_desc"));
			}
			map.put("is_usable", (String)params.get("is_usable"));
			if(!params.isNull("dic_seq")){
				map.put("seq", (String)params.get("dic_seq"));
			}
			//
			HttpSession httpsession = request.getSession(true);
			String userinfo = httpsession.getAttribute("USER_INFO").toString();
			map.put("create_name", userinfo);
			int i = dicDao.insertForNew(map);
			//返回值
			return state.addState(AppInfo.SUCCESS, i);
		}catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
		}
	}
	
	
	/**
	 * 更新节点-dic_id
	 */
	public String update(HttpServletRequest request){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			//获取dic_id（主键）并判断是否有效
			String dic_id = params.getString("dic_id");
			if(params==null || dic_id==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			//获取更新的其他字段
			String dic_code = params.getString("dic_code");
			String dic_name = params.getString("dic_name");
			String parent_code = params.getString("parent_code");
			String dic_desc = params.getString("dic_desc");
			String is_usable = params.getString("is_usable");
			String seq = params.getString("seq");
			//
			HttpSession httpsession = request.getSession(true);
			String userinfo = httpsession.getAttribute("USER_INFO").toString();
			//拼sql所需的参数
			Map<String,String> map = new HashMap<String, String>();
			map.put("dic_id", dic_id);
			map.put("dic_code", dic_code);
			map.put("dic_name", dic_name);
			map.put("parent_code", parent_code);
			map.put("dic_desc", dic_desc);
			map.put("is_usable", is_usable);
			map.put("seq", seq);
			map.put("update_name", userinfo);
			int res = dicDao.update(map);
			return state.addState(AppInfo.SUCCESS, res);
		}catch (Exception e) {
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM, null);
		}
	}
	
	
	/**
	 * 删除节点-dic_id
	 */
	public String deleteNode(HttpServletRequest request){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			//获取dic_id（主键）并判断是否有效
			String dic_id = params.getString("dic_id");
			//获取树节点，不做任何操作，只作为返回值一同传回前台
			String treeNode = params.getString("treeNode");
			if(params==null || dic_id==null || treeNode==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			//参数map
			Map<String,String> map = new HashMap<String, String>();
			//删除节点
			map.put("dic_id", dic_id);
			int res = dicDao.deleteForId(map);
			//所有返回结果全部放入resMap
			Map<String,Object> resMap = new HashMap<String, Object>();
			resMap.put("res", res+"");
			resMap.put("treeNode", treeNode);
			//重新查询字典数据，用于刷新字典树
			List<Map<String,String>> list = dicDao.queryForTree(new HashMap<String, String>());
			resMap.put("treeData", new JSONArray(list));
			return state.addState(AppInfo.SUCCESS, new JSONObject(resMap).toString());
		}catch (Exception e) {
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM, null);
		}
	}
	
	
	/**
	 * 节点启用/禁用-is_usable
	 */
	public String updateIsable(HttpServletRequest request){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			//获取dic_id（主键）并判断是否有效
			String dic_id = params.getString("dic_id");
			String is_usable = params.getString("is_usable");
			String treeNode = params.getString("treeNode");
			if(params==null || dic_id==null || is_usable==null || treeNode==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			//拼sql所需的参数
			Map<String,String> map = new HashMap<String, String>();
			map.put("dic_id", dic_id);
			map.put("is_usable", is_usable);
			int res = dicDao.updateIsable(map);
			Map<String,String> resMap = new HashMap<String, String>();
			resMap.put("res", res+"");
			resMap.put("treeNode", treeNode);
			return state.addState(AppInfo.SUCCESS, new JSONObject(resMap).toString());
		}catch (Exception e) {
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM, null);
		}
	}
	
	
	/**
	 * 节点编辑前先读取节点原信息
	 */
	public String loadBeforeUpdate(HttpServletRequest request){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			//获取dic_id（主键）并判断是否有效
			String dic_id = params.getString("dic_id");
			if(params==null || dic_id==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			return state.addState(AppInfo.SUCCESS, dicDao.queryBeforeUpdate(dic_id));
		}catch (Exception e) {
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM, null);
		}
	}
	
	
	
	/**
	 * 按节点编码查询节点是否存在-用于父节点校验
	 */
	public String queryForDicCode(HttpServletRequest request){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			//获取dic_id（主键）并判断是否有效
			String dic_code = params.getString("dic_code");
			if(params==null || dic_code==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dic_code", dic_code);
			return state.addState(AppInfo.SUCCESS, dicDao.queryCountForCode(map));
		}catch (Exception e) {
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM, null);
		}
	}
	
	
	
	/**
	 * 无模板导出-单sheet
	 */
	public String download(HttpServletRequest request,HttpServletResponse response){
		State state = new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			JSONObject params = new JSONObject(stk);
			//获取dic_id（主键）并判断是否有效
			String dic_code = params.getString("dic_code");
			String zhColArr = params.getString("zhColumns");
			String enColArr = params.getString("enColumns");
			if(params==null || dic_code==null || zhColArr==null || enColArr==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			//获取分页相关参数
			PageInfo pageInfo=JSON.parseObject(stk,PageInfo.class);
			if(pageInfo==null){
				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
			}
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("parent_code", dic_code);
			//设置分页参数：总记录数
			pageInfo.setTotalCount(dicDao.countChildnode(paramMap));
			//根据条件查询：分页
			paramMap.put("start", pageInfo.getStart());
			paramMap.put("pageSize", pageInfo.getPageSize());
			paramMap.put("end",pageInfo.getStart()+pageInfo.getPageSize());  //oracle 分页参数
			paramMap.put("paging", "enable");
			String exprotFileName = "字典信息";
			List<Map<String,String>> list= new ArrayList<Map<String,String>>();
			//判断当前数据库类型
			if(Common.dataBaseType()==1){
				list=dicDao.queryForChildnodes(paramMap);
			}
			if(Common.dataBaseType()==2){
				list=dicDao.orclQueryForChildnodes(paramMap);
			}
			String dataJsonString = new JSONArray(list).toString();
			//生成导出对象
			IExcelExporter exporter = new ExcelExporter();
			XSSFWorkbook workBook = exporter.exportExcel(exprotFileName, dataJsonString, zhColArr, enColArr);
			dataReturnForm(exprotFileName+".xlsx",workBook,response);
			return state.addState(AppInfo.SUCCESS, null);
		}catch (Exception e) {
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM, null);
		}
	}
	
	/**
	 * 模板导出-单sheet
	 */
//	public String download(HttpServletRequest request,HttpServletResponse response){
//		State state = new BaseState();
//		try{
//			//获得传入数据  转换为map
//			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
//			JSONObject params = new JSONObject(stk);
//			//获取dic_id（主键）并判断是否有效
//			String dic_code = params.getString("dic_code");
//			String zhColArr = params.getString("zhColumns");
//			String enColArr = params.getString("enColumns");
//			if(params==null || dic_code==null || zhColArr==null || enColArr==null){
//				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
//			}
//			//获取分页相关参数
//			PageInfo pageInfo=JSON.parseObject(stk,PageInfo.class);
//			if(pageInfo==null){
//				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
//			}
//			Map<String,Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("parent_code", dic_code);
//			//设置分页参数：总记录数
//			pageInfo.setTotalCount(dicDao.countChildnode(paramMap));
//			//根据条件查询：分页
//			paramMap.put("start", pageInfo.getStart());
//			paramMap.put("pageSize", pageInfo.getPageSize());
//			paramMap.put("end",pageInfo.getStart()+pageInfo.getPageSize());  //oracle 分页参数
//			paramMap.put("paging", "enable");
//			//模板导出-需要先把数据转成模板可以识别的数据结构
//			List<Map<String,String>> list2 = dicDao.queryForExportTest(null);
//			//模板id、导出文件名称
//			String tempId = "test1";
//			String exprotFileName = "模板导出测试1.xlsx";
//			//模板所在路径
//			String basePath = request.getSession().getServletContext().getRealPath("");
//			String filePath = basePath + "\\template\\excel\\" + tempId + ".xlsx";
//			//生成导出对象，并按模板组织数据结构
//			IExcelExporter exporter = new ExcelExporter();
//			Map<String,Map<String,Map<String,String>>> lMap = exporter.listToMap(list2);
//			String dataJsonString = new JSONObject(lMap).toString();
//			//调用模板分析workBook-单sheet
//			XSSFWorkbook workBook = exporter.exportTemplateForSingle(filePath, tempId, exprotFileName, dataJsonString);
//			dataReturnForm(exprotFileName,workBook,response);
//			return state.addState(AppInfo.SUCCESS, null);
//		}catch (Exception e) {
//			e.printStackTrace();
//			return state.addState(AppInfo.NO_PARAM, null);
//		}
//	}
	
	/**
	 * 模板导出-多sheet
	 */
//	public String download(HttpServletRequest request,HttpServletResponse response){
//		State state = new BaseState();
//		try{
//			//获得传入数据  转换为map
//			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
//			JSONObject params = new JSONObject(stk);
//			//获取dic_id（主键）并判断是否有效
//			String dic_code = params.getString("dic_code");
//			String zhColArr = params.getString("zhColumns");
//			String enColArr = params.getString("enColumns");
//			if(params==null || dic_code==null || zhColArr==null || enColArr==null){
//				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
//			}
//			//获取分页相关参数
//			PageInfo pageInfo=JSON.parseObject(stk,PageInfo.class);
//			if(pageInfo==null){
//				return state.addState(AppInfo.NO_PARAM,null);//传入数据格式不正确
//			}
//			Map<String,Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("parent_code", dic_code);
//			//设置分页参数：总记录数
//			pageInfo.setTotalCount(dicDao.countChildnode(paramMap));
//			//根据条件查询：分页
//			paramMap.put("start", pageInfo.getStart());
//			paramMap.put("pageSize", pageInfo.getPageSize());
//			paramMap.put("end",pageInfo.getStart()+pageInfo.getPageSize());  //oracle 分页参数
//			paramMap.put("paging", "enable");
//			//模板id、导出文件名称
//			String tempId = "test2";
//			String exprotFileName = "模板导出测试2.xlsx";
//			//模板所在路径
//			String basePath = request.getSession().getServletContext().getRealPath("");
//			String filePath = basePath + "\\template\\excel\\" + tempId + ".xlsx";
//			//生成导出对象
//			IExcelExporter exporter = new ExcelExporter();
//			//模板导出-需要先把数据转成模板可以识别的数据结构
//			List<Map<String,String>> list=null;
//			//判断当前数据库类型
//			if(Common.dataBaseType()==1){
//				list=dicDao.queryForChildnodes(paramMap);
//			}
//			if(Common.dataBaseType()==2){
//				list=dicDao.orclQueryForChildnodes(paramMap);
//			}
//			List<Map<String,String>> list2 = dicDao.queryForExportTest(null);
//			Map<String,Map<String,Map<String,String>>> lMap = exporter.listToMap(list2);
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("dic", list);
//			map.put("test1", lMap);
//			String dataJsonString = new JSONObject(map).toString();
//			//多sheet分析workBook
//			XSSFWorkbook workBook = exporter.exportExcelForTemplate(filePath,tempId,exprotFileName,dataJsonString);
//			//
//			dataReturnForm(exprotFileName,workBook,response);
//			return state.addState(AppInfo.SUCCESS, null);
//		}catch (Exception e) {
//			e.printStackTrace();
//			return state.addState(AppInfo.NO_PARAM, null);
//		}
//	}
	
	/**
	 * 前台form
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void dataReturnForm(String filename,XSSFWorkbook workBook,
			HttpServletResponse response){
		try{
			/********前台为form提交时使用********/
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes(), "ISO-8859-1"));//设定输出文件头
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型 
			// 通过response的输出流把工作薄的流发送浏览器形成文件
			OutputStream outStream = response.getOutputStream();
			workBook.write(outStream);
			outStream.flush(); 
			outStream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 前台ajax
	 * 
	 * @param filename
	 * @param workBook
	 * @return
	 */
	public void dataReturnAjax(String filename, XSSFWorkbook workBook){
		try{
			/********前台为ajax提交时使用********/
			String fileId = Common.getUUID();
			String fileName = fileId+".xlsx";
			String path = "D:/44444444444444444444444444/test";
			String filePath = path+"/"+fileName;
			FileOutputStream fileOut = new FileOutputStream(filePath);
			workBook.write(fileOut);
			fileOut.close();
			Map<String,String> map = new HashMap<String, String>();
			map.put("fileId", fileId);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
