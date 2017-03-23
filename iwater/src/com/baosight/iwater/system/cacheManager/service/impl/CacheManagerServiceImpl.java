package com.baosight.iwater.system.cacheManager.service.impl;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.baosight.iwater.define.ApplicationContextUtil;
import com.baosight.iwater.define.QuartzManager;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.define.CacheManager;
import com.baosight.iwater.system.cacheManager.dao.CacheManagerMapper;
import com.baosight.iwater.system.cacheManager.service.ICacheManagerService;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.Common;
import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.menu.pojo.Menu;
@Service("cacheManagerService")
public class CacheManagerServiceImpl implements ICacheManagerService {
	@Resource
	private CacheManagerMapper mapper;
	
	private  ApplicationContextUtil applicationUtil;
	
	private ApplicationContext ctx = applicationUtil.getContext();
	private SqlSessionFactory sqlSessionFactory= (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
	public String findAllTest() {
		State state=new BaseState();
		CacheManager cacheManager=new CacheManager();
		try{  
			Map<String,Object> map=new HashMap<String,Object>();
			List<Map<String,String>> findAllList = mapper.findAll();
			for(int i=0;i<findAllList.size();i++){
				Map<String,String> map2=findAllList.get(i);
				String key=map2.get("cache_package")+"."+map2.get("cache_idname")+"-"+map2.get("cache_role");
					if(map2.get("cache_state").equals("0")&&cacheManager.jedis.get(key.getBytes("utf-8"))!=null){
						map2.put("cache_state","1");
						//缺少更新数据库为“1”操作
						updateById(map2);
					}
			}	
			for(int i=0;i<findAllList.size();i++){
				Map<String,String> map3=findAllList.get(i);
				if(map3.get("cache_type").equals("1")){
					map3.put("cache_type","刷新");
				}else if(map3.get("cache_type").equals("2")){
					map3.put("cache_type","同步");
				}
				if(map3.get("cache_state").equals("0")){
					map3.put("cache_state","不存在");
				}else if(map3.get("cache_state").equals("1")){
					map3.put("cache_state","未启动");
				}else if(map3.get("cache_state").equals("2")){
					map3.put("cache_state","正常运行");
				}
				String[] cachePackage=map3.get("cache_package").split("\\.");
				String cachePackageStr="";
				if(cachePackage.length>2){
					cachePackageStr+=cachePackage[0]+".**."+cachePackage[cachePackage.length-1];
					map3.put("cache_package",cachePackageStr);
				}
			}
			state.addState(AppInfo.SUCCESS,findAllList);
			//返回  分页信息
			//将信息返回
			String str=state.getJson(); 
			return str;
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	
		
	}
	
	public String findAll(HttpServletRequest request) {
		CacheManager cacheManager=new CacheManager();
//		cacheManager.set("key1", "value1");//设置key,value
//		String value1=cacheManager.get("key1");//获取key,value
//		Boolean isExist=cacheManager.exists("key1");//是否存在
//		String[] keyvalues=new String[]{"key2","value2","key3","value3"};
//		cacheManager.mset(keyvalues);//批量设置
//		String[] keys=new String[]{"key2","key2","key3"};
//		List<String> values=cacheManager.mget(keys);//批量获取
		
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
			map.put("end", pageInfo.getStart()+pageInfo.getPageSize());  //oracle 分页参数
			if(st!=null && !"\"\"".equals(st.trim())){
				JSONObject  ja =  JSONObject.fromObject(st);
				Map<String,Object> findAllList = ja;
				map.putAll(findAllList);
			}
			
			map.put("queryItem", getQueryItem(map));  //通用查询条件
			pageInfo.setTotalCount(mapper.findAllSize(map));
			//System.out.println(map);
			
			//返回 基础信息
			List<Map<String,String>> findAllList =null;
			//判断当前数据库类型
			if(Common.dataBaseType()==1){   //mysql
				findAllList=mapper.findAllFlushCacheTest(map);
			}
			if(Common.dataBaseType()==2){	//oracle
				findAllList=mapper.orclFindAllFlushCacheTest(map);
			}
			//List<Map<String,String>> findAllList = mapper.findAllFlushCacheTest(map);
			for(int i=0;i<findAllList.size();i++){
				Map<String,String> map2=findAllList.get(i);
				String key=map2.get("cache_package")+"."+map2.get("cache_idname")+"-"+map2.get("cache_role");
					if(cacheManager.jedis.get(key.getBytes("utf-8"))!=null){
						int stat=QuartzManager.isexist(key);
						if(stat==0){
							if(!map2.get("cache_state").equals("1")){
								map2.put("cache_state","1");
								//缺少更新数据库为“1”操作
								updateById(map2);
							}   
							
						}else if(stat==1){
							if(!map2.get("cache_state").equals("2")){
								map2.put("cache_state","2");
								//缺少更新数据库为“1”操作
								updateById(map2);
							}
						}
					}else{
						if(!map2.get("cache_state").equals("0")){
							map2.put("cache_state","0");
							//缺少更新数据库为“1”操作
							updateById(map2);
						}
					}
			}	
			for(int i=0;i<findAllList.size();i++){
				Map<String,String> map3=findAllList.get(i);
				if(Common.notEmpty(map3.get("cache_type"))){
					if(map3.get("cache_type").equals("1")){
						map3.put("cache_type","刷新");
					}else if(map3.get("cache_type").equals("2")){
						map3.put("cache_type","同步");
					}
				}
				
				if(Common.notEmpty(map3.get("cache_state"))){
					if(map3.get("cache_state").equals("0")){
						map3.put("cache_state","不存在");
					}else if(map3.get("cache_state").equals("1")){
						map3.put("cache_state","未启动");
					}else if(map3.get("cache_state").equals("2")){
						map3.put("cache_state","正常运行");
					}
				}
				
				String[] cachePackage=map3.get("cache_package").split("\\.");
				String cachePackageStr="";
				if(cachePackage.length>2){
					cachePackageStr+=cachePackage[0]+".**."+cachePackage[cachePackage.length-1];
					map3.put("cache_package",cachePackageStr);
				}
			}
			state.addState(AppInfo.SUCCESS,findAllList);
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
		StringBuffer str=new StringBuffer(" and cache_modified='1'");
		if(Common.notEmpty(map.get("cache_menu"))){  //菜单名称
			str.append(" and cache_menu like '%"+map.get("cache_menu")+"%'");
		}
		if(Common.notEmpty(map.get("cache_package"))){  //包名称
			str.append(" and cache_package like '%"+map.get("cache_package")+"%'");
		}
		if(Common.notEmpty(map.get("cache_role"))){  //角色名
			str.append(" and cache_role like '%"+map.get("cache_role")+"%'");
		}
		if(Common.notEmpty(map.get("cache_idname"))){  //<select>id名称
			str.append(" and cache_idname like '%"+map.get("cache_idname")+"%'");
		}
		if(Common.notEmpty(map.get("cache_type"))){  //缓存类型
			String type[]=map.get("cache_type").toString().split("-");
			str.append(" and (");
			for(int i=0;i<type.length;i++){
				if(i!=0){
					str.append(" or ");
				}
				str.append(" cache_type like '%"+type[i]+"%'");
			}
			str.append(" )");
		}
		if(Common.notEmpty(map.get("cache_state"))){ //缓存状态
			String state[]=map.get("cache_state").toString().split("-");
			str.append(" and (");
			for(int i=0;i<state.length;i++){
				if(i!=0){
					str.append(" or ");
				}
				str.append(" cache_state like '%"+state[i]+"%'");
			}
			str.append(" )");
		}
		return str.toString();
	}	
	
	//刷新缓存，调的是xml中方法名+FlushCache
	public List<Map<String,String>> findAllFlushCache(String sql) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		List<Map<String,String>> findAllList = null;
		  try {
			  findAllList = sqlSession.selectList(sql);//找到对应的刷新缓存xml中方法并执行
	        } finally {
	            sqlSession.close();
	        }
		return findAllList;
	}
	public int save(Map<String,String> map,String sql) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		int findAllList ;
		  try {
			  findAllList = sqlSession.insert(sql, map);//找到对应的刷新缓存xml中方法并执行
	        } finally {
	            sqlSession.close();
	        }
		return findAllList;
	}
	public int updateRefreshTime(Map<String,String> map,String sql) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		int findAllList ;
		  try {
			  findAllList = sqlSession.update(sql,map);//找到对应的刷新缓存xml中方法并执行
	        } finally {
	            sqlSession.close();
	        }
		return findAllList;
	}  
	public int updateById(Map<String,String> map,String sql) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		int findAllList ;
		  try {
			  findAllList = sqlSession.update(sql,map);//找到对应的刷新缓存xml中方法并执行
	        } finally {
	            sqlSession.close();
	        }
		return findAllList;
	}  
	public int deleteById(Map<String,String> map,String sql) {
		SqlSession sqlSession = sqlSessionFactory.openSession(); 
		int findAllList ;
		  try {
			  findAllList = sqlSession.delete(sql,map);//找到对应的刷新缓存xml中方法并执行
	        } finally {
	            sqlSession.close();
	        }
		return findAllList;
	}
	public int deleteById(Map<String, String> map) {
		// TODO Auto-generated method stub
		return mapper.deleteById(map);
	}
	public int updateById(Map<String, String> map) {
		// TODO Auto-generated method stub
		return mapper.updateById(map);
	}
	public int save(Map<String, String> map) {
		// TODO Auto-generated method stub
		return mapper.save(map);
	}
	//导出菜单
	public String download(HttpServletRequest request,HttpServletResponse response){
		State state=new BaseState();
		String stk="";
		try {
			stk = URLDecoder.decode(request.getParameter("param"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Map<String,Object> map=state.JsonToMap(stk);   //查询条件
		map.put("excel_flag","hello");   //不进行分页
		List<Map<String,String>> list=mapper.findAllFlushCache(map);
		String exprotFileName="菜单信息"+".xlsx";
		XSSFWorkbook workBook=null;
		//创建工作薄
		workBook=new XSSFWorkbook();  
		//创建sheet  生成一个表格
		XSSFSheet sheet=workBook.createSheet();  
		//工作薄名称
		workBook.setSheetName(0,"缓存信息");
		
		//字体样式1
		XSSFFont font1=workBook.createFont();
		font1.setColor(XSSFFont.COLOR_NORMAL);
		//font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		
		//字体样式2
		XSSFFont font2=workBook.createFont();
		font2.setColor(XSSFFont.COLOR_NORMAL);
		
		//生成一个样式1  使用标题的字体样式
		XSSFCellStyle titleStyle = workBook.createCellStyle();
		titleStyle.setFont(font1);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		//生成样式2   使用内容的样式
		XSSFCellStyle style = workBook.createCellStyle();
		style.setFont(font2);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		StringBuffer otherTitle=new StringBuffer("iwater缓存信息  ");
		
		//1.首先向excel中填入标题
		XSSFRow headRow=sheet.createRow((short)0);  //第一行主标题
		 XSSFCell headCell = headRow.createCell(0);        
		 headCell.setCellStyle(titleStyle);
		 headCell.setCellType(XSSFCell.CELL_TYPE_STRING);
		 headCell.setCellValue(otherTitle.toString());
		 //单元格,createCell(i),这里的i代表单元格是第几列，
		 //CellRangeAddress(firstRow,lastRow,firstCol,lastCol)里的参数分别表示需要合并的单元格起始行，起始列 
		sheet.addMergedRegion((new CellRangeAddress(0, 0, 0, 10)));   
		
		//创建第一行标题
		XSSFRow titleRow=sheet.createRow((short)1);  //第二行的标题
		//在第一行的基础上 创建列
		String title[]={"添加时间","修改时间","操作者","菜单中文名称","包名称",
					"角色名称","方法名称","缓存类型","缓存状态","刷新频率","最后刷新时间"};
		for(int i=0;i<title.length;i++){
			 XSSFCell cell = titleRow.createCell(i);        
			 cell.setCellStyle(titleStyle);
			 cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			 cell.setCellValue(title[i]);
		}
		 
		//2.向excel中写入具体内容
		String content[]={"add_date","up_date","user_info","cache_menu",
					"cache_package","cache_role","cache_idname","cache_type","cache_state",
					"cache_rate","cache_refreshtime"};
		XSSFRow contentRow=null;
		XSSFCell cell=null;
		for(int i=0;i<list.size();i++){
			contentRow = sheet.createRow((short) i+2);  
			Map<String,String> menuMap=list.get(i);
			for(int j=0;j<content.length;j++){
				cell = contentRow.createCell(j);        
				cell.setCellStyle(titleStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(menuMap.get(content[j]));
			}
		}
		
	    try {
			downloadExcel(workBook, exprotFileName, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state.addState(AppInfo.SUCCESS,null);
	}
	
	/**
	 * 下载excel
	 * @param workBook
	 * @param exprotFileName
	 * @param response
	 * @throws Exception
	 */
	public void downloadExcel(XSSFWorkbook workBook,String exprotFileName,HttpServletResponse response)throws Exception{
		//向请求输出  形成下载
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((exprotFileName).getBytes(), "ISO-8859-1"));//设定输出文件头
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型 
		// 通过response的输出流把工作薄的流发送浏览器形成文件
		OutputStream outStream = response.getOutputStream();
		workBook.write(outStream);
		outStream.flush(); 
		outStream.close();		
	}
	public String selectByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Map<String,Object> map=state.JsonToMap(stk);
			if(map==null || map.get("cache_id")==null){
				return state.addState(AppInfo.NO_PARAM,null);     //传入数据格式不正确
			} 
			List<Map<String,String>> findAllList = mapper.selectByPrimaryKey(map.get("cache_id").toString());
			return state.addState(AppInfo.SUCCESS, findAllList);
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	public List<Map<String,String>> selectListByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
			String stk="";
			try {
				stk = URLDecoder.decode(request.getParameter("params"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String,Object> map=state.JsonToMap(stk);
			if(map==null || map.get("cache_id")==null){
				return null;     //传入数据格式不正确
			} 
			List<Map<String,String>> findAllList = mapper.selectByPrimaryKey(map.get("cache_id").toString());
			return findAllList;
	}
}
