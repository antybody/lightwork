package com.baosight.iwater.system.log.service.impl;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.Common;
import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.log.dao.logMapper;
import com.baosight.iwater.system.log.pojo.Log;
import com.baosight.iwater.system.log.service.ILogService;

@Service("logService")
public class LogServiceImpl implements ILogService{
   
	@Resource
	private logMapper logDao;
	
	/**
	 * 查询所有日志
	 */
	public String findAll(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=request.getParameter("param");     //分页参数
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
			pageInfo.setTotalCount(logDao.findAllSize(map));
			//添加基本信息
			if(Common.dataBaseType()==1){ //判断当前数据库类型
				state.addState(AppInfo.SUCCESS,logDao.findAllSql(map));
			}
			else{
				state.addState(AppInfo.SUCCESS,logDao.findAllOrcl(map));
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
		if(Common.notEmpty(map.get("add_date"))){  //添加时间
			str.append(" and s.add_date like '%"+map.get("add_date")+"%'");
		}
		if(Common.notEmpty(map.get("log_desc"))){  //用途
			str.append(" and s.log_desc like '%"+map.get("log_desc")+"%'");
		}
		if(Common.notEmpty(map.get("log_user"))){  //操作者
			str.append(" and s.log_user like '%"+map.get("log_user")+"%'");
		}
		if(Common.notEmpty(map.get("log_IP"))){  //ip地址
			str.append(" and s.log_IP like '%"+map.get("log_IP")+"%'");
		}
		return str.toString();
	}		
	
	/**
	 * 新增日志
	 */
	public String add(Log log){
		State state=new BaseState();
		try{
			logDao.insert(log);
			return state.addState(AppInfo.SUCCESS,null);         //添加成功
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

	/**
	 * 删除日志
	 */
	public String del(HttpServletRequest request) {
		State state=new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Log log=JSON.parseObject(stk,Log.class);
			if(log==null || log.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);  //传入数据格式不正确
			}
			log.setUp_date(Common.getTime());
			//进行删除
			int flag=logDao.del(log);
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

	/**
	 * 导出日志
	 */
	public String download(HttpServletRequest request,
			HttpServletResponse response) {
		State state=new BaseState();
		String stk = request.getParameter("param");
		//String st=request.getParameter("params");
		//PageInfo pageInfo=JSON.parseObject(st,PageInfo.class);
		Map<String,Object> map = state.JsonToMap(stk);//查询条件
		map.put("queryItem", getQueryItem(map));  //通用查询条件
		map.put("excel_flag", "yes");		//不分页
		//默认导出当前页开始的500条数据  防止溢出
		/*map.put("start", pageInfo.getStart());
		map.put("pageSize", 500);
		map.put("end",pageInfo.getStart()+500);*/
		List<Map<String,String>> list=null;
		list=logDao.download(map);
		/*if(Common.dataBaseType()==1){ //判断当前数据库类型
			list=logDao.downloadSql(map);
		}
		else{
			list=logDao.downloadOrcl(map);
		}*/
		String exprotFileName = "系统日志"+".xlsx";
		XSSFWorkbook workBook = null;
		//创建工作薄
		workBook = new XSSFWorkbook();
		//创建sheet  生成一个表格
		XSSFSheet sheet=workBook.createSheet();
		
		sheet.autoSizeColumn((short)0); //调整第一列宽度
        sheet.autoSizeColumn((short)1); //调整第二列宽度
        sheet.autoSizeColumn((short)2); //调整第三列宽度
        sheet.autoSizeColumn((short)3); //调整第四列宽度
        
		//工作簿名称
		workBook.setSheetName(0,"日志信息");
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
		StringBuffer otherTitle=new StringBuffer("iwater日志信息  ");
		
		//1.首先向excel中填入标题
		XSSFRow headRow=sheet.createRow((short)0);  //第一行主标题
		 XSSFCell headCell = headRow.createCell(0);        
		 headCell.setCellStyle(titleStyle);
		 headCell.setCellType(XSSFCell.CELL_TYPE_STRING);
		 headCell.setCellValue(otherTitle.toString());
		 sheet.addMergedRegion((new CellRangeAddress(0, 0, 0, 9)));   
		 
		//创建第一行标题
		XSSFRow titleRow=sheet.createRow((short)1);  //第二行的标题
		
		//在第一行的基础上 创建列
		String title[]={"日志用途","添加时间","修改时间","日至方法","操作者",
					"请求IP","异常代码","异常参数","操作时间"};
		for(int i=0;i<title.length;i++){
			 XSSFCell cell = titleRow.createCell(i); 
			 cell.setCellStyle(titleStyle);
			 cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			 cell.setCellValue(title[i]);
		}
		
		String content[]={"log_desc","add_date","up_date","log_method",
				"log_user","log_IP","log_excode","log_params","log_date"};
		
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

	/**
	 * 根据id查询日志
	 */
	public String selectByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Map<String,Object> map=state.JsonToMap(stk);
			if(map==null || map.get("ui_id")==null){
				return state.addState(AppInfo.NO_PARAM,null);     //传入数据格式不正确
			}
			return state.addState(AppInfo.SUCCESS, logDao.selectByPrimaryKey(map.get("ui_id").toString()));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

}
