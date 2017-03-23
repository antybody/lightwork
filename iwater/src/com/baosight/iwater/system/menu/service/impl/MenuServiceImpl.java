package com.baosight.iwater.system.menu.service.impl;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.baosight.iwater.system.db.manager.PropertiesManager;
import com.baosight.iwater.system.define.AppInfo;
import com.baosight.iwater.system.define.Common;
import com.baosight.iwater.system.define.PageInfo;
import com.baosight.iwater.system.define.State;
import com.baosight.iwater.system.define.impl.BaseState;
import com.baosight.iwater.system.menu.dao.MenuMapper;
import com.baosight.iwater.system.menu.pojo.Menu;
import com.baosight.iwater.system.menu.pojo.Power;
import com.baosight.iwater.system.menu.service.IMenuService;
import com.baosight.iwater.system.user.pojo.User;
//下面注解里面的参数  是给实例化后的对象起名
@Service("menuService")
public class MenuServiceImpl implements IMenuService {
	
	@Resource
	private MenuMapper menuDao;
	
	//删除菜单
	@Transactional
	public String deleteByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			//获得传入数据  转换为map
			String stk=URLDecoder.decode(request.getParameter("params"),"UTF-8");
			Menu menu=JSON.parseObject(stk,Menu.class);
			if(menu==null || menu.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);  //传入数据格式不正确
			}
			
			//查询 1.权限资源是否有对应的页面  2.子菜单
			if(menuDao.delQueryMenu(menu.getUi_id())!=0
			|| menuDao.delQueryPower(menu.getUi_id())!=0){
				return state.addState(AppInfo.DEL_LINK_DATA,null);  //存在关联数据,拒绝删除
			}
			
			menu.setUp_date(Common.getTime());
			Power power=new Power();
			power.setUi_id(menu.getUi_id());
			power.setUp_date(Common.getTime());
			
			//获得操作者的信息
			HttpSession session=request.getSession();
			if(session.getAttribute("USER_INFO")!=null){
				power.setUser_info(session.getAttribute("USER_INFO").toString());
				menu.setUser_info(session.getAttribute("USER_INFO").toString());
			}
			
			int flag=menuDao.deleteByPrimaryKey(menu);
			flag+=menuDao.deletePower(power);
			if(flag==2){
				return state.addState(AppInfo.SUCCESS,null);   //删除成功 
			}
			return state.addState(AppInfo.NOT_FIND,null);	    //删除主键不存在	
		}
		catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
	//添加
	@Transactional
	public String insert(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Menu menu=JSON.parseObject(stk,Menu.class);
			if(menu==null){
				return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
			}
			String ui_id=Common.getUUID();
			menu.setUi_id(ui_id);
			menu.setAdd_date(Common.getTime());
			//menu数据 -> power数据
			Power power=new Power();
			power.setUi_id(ui_id);
			power.setAdd_date(Common.getTime());
			power.setUser_info(menu.getUser_info());
			power.setPower_type("002"); //菜单
			power.setPower_parent(menu.getParent_menu());  //父菜单
			power.setPower_prefix(menu.getItem_prefix());
			power.setPower_name(menu.getMenu_zhname());
			power.setPower_code(menu.getMenu_code());
			power.setPower_url(menu.getMenu_url());
			
			//获得操作者的信息
			HttpSession session=request.getSession();
			if(session.getAttribute("USER_INFO")!=null){
				power.setUser_info(session.getAttribute("USER_INFO").toString());
				menu.setUser_info(session.getAttribute("USER_INFO").toString());
			}
			
			//进行排序
			if(!"0".equals(menu.getParent_menu())){
				Integer num=menuDao.SonMenuNum();
				menu.setMenu_sort(num.toString());
			}
			else{
				menu.setMenu_sort("1");
			}
			
			menuDao.insert(menu);
			menuDao.insertPower(power);   //同步添加权限资源
			return state.addState(AppInfo.SUCCESS,null);
		}
		catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
	//根据主键查询
	public String selectByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Map<String,Object> map=state.JsonToMap(stk);
			if(map==null || map.get("ui_id")==null){
				return state.addState(AppInfo.NO_PARAM,null);     //传入数据格式不正确
			}
			return state.addState(AppInfo.SUCCESS, menuDao.selectByPrimaryKey(map.get("ui_id").toString()));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}

	//根据主键修改
	@Transactional
	public String updateByPrimaryKey(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Menu menu=JSON.parseObject(stk,Menu.class);
			if(menu==null || menu.getUi_id()==null){
				return state.addState(AppInfo.NO_PARAM,null);      //传入数据格式不正确
			}
			menu.setUp_date(Common.getTime());
			
			//menu数据 -> power数据
			Power power=new Power();
			power.setUi_id(menu.getUi_id());  //主键
			power.setUp_date(Common.getTime());
			power.setUser_info(menu.getUser_info());
			power.setPower_type("002"); //菜单
			power.setPower_parent(menu.getParent_menu());  //父菜单
			power.setPower_prefix(menu.getItem_prefix());
			power.setPower_name(menu.getMenu_zhname());
			power.setPower_code(menu.getMenu_code());
			power.setPower_url(menu.getMenu_url());
			
			//获得操作者的信息
			HttpSession session=request.getSession();
			if(session.getAttribute("USER_INFO")!=null){
				power.setUser_info(session.getAttribute("USER_INFO").toString());
				menu.setUser_info(session.getAttribute("USER_INFO").toString());
			}
			
			//进行修改
			int flag=menuDao.updateByPrimaryKey(menu);
			flag+=menuDao.updatePower(power);  //同步修改权限资源
			if(flag==2){
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
		List<Menu> list=menuDao.findAllSql(map);
		String exprotFileName="菜单信息"+".xlsx";
		XSSFWorkbook workBook=null;
		//创建工作薄
		workBook=new XSSFWorkbook();  
		//创建sheet  生成一个表格
		XSSFSheet sheet=workBook.createSheet();  
		//工作薄名称
		workBook.setSheetName(0,"菜单信息");
		
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
		StringBuffer otherTitle=new StringBuffer("iwater菜单信息  ");
		
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
		String title[]={"添加时间","修改时间","操作者","菜单中文名称","菜单英文名称",
					"菜单编号","所属项目","父级菜单","菜单访问路径","菜单排序 ","菜单样式","菜单图标样式"};
		for(int i=0;i<title.length;i++){
			 XSSFCell cell = titleRow.createCell(i);        
			 cell.setCellStyle(titleStyle);
			 cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			 cell.setCellValue(title[i]);
		}
		 
		//2.向excel中写入具体内容
		String content[]={"add_date","up_date","user_info","menu_zhname",
					"menu_enname","menu_code","item_prefix","parent_menu",
					"menu_url","menu_sort","menu_class","pic_class"};
		XSSFRow contentRow=null;
		XSSFCell cell=null;
		for(int i=0;i<list.size();i++){
			contentRow = sheet.createRow((short) i+2);  
			Map<String,String> menuMap=state.ObjToMap(list.get(i));
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
	
	//查询所有  分页
	public String findAll(HttpServletRequest request) {
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("param"),"UTF-8");  //分页参数
			String st=URLDecoder.decode(request.getParameter("params"),"UTF-8");	//查询条件
			PageInfo pageInfo=JSON.parseObject(stk,PageInfo.class);
			if(pageInfo==null){
				return state.addState(AppInfo.NO_PARAM,null);        //传入数据格式不正确
			}
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("start",pageInfo.getStart());
			map.put("pageSize",pageInfo.getPageSize());
			map.put("end",pageInfo.getStart()+pageInfo.getPageSize());  //oracle 分页参数
			if(st!=null && !"\"\"".equals(st.trim())){
				Menu menu=JSON.parseObject(st,Menu.class);
				map.putAll(state.ObjToMap(menu));
			}
			map.put("queryItem", getQueryItem(map));  //通用查询条件
			pageInfo.setTotalCount(menuDao.findAllSize(map));
			
			//判断当前数据库类型
			if(Common.dataBaseType()==1){
				state.addState(AppInfo.SUCCESS,menuDao.findAllSql(map));
			}
			if(Common.dataBaseType()==2){
				state.addState(AppInfo.SUCCESS,menuDao.findAllOrcl(map));
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
		StringBuffer str=new StringBuffer(" and u.is_del=1");
		if(Common.notEmpty(map.get("menu_zhname"))){  //菜单中文名称
			str.append(" and u.menu_zhname like '%"+map.get("menu_zhname")+"%'");
		}
		if(Common.notEmpty(map.get("menu_enname"))){  //菜单英文名称
			str.append(" and u.menu_enname like '%"+map.get("menu_enname")+"%'");
		}
		if(Common.notEmpty(map.get("menu_url"))){  //菜单路径
			str.append(" and u.menu_url like '%"+map.get("menu_url")+"%'");
		}
		if(Common.notEmpty(map.get("item_prefix"))){  //项目名称
			str.append(" and u.item_prefix like '%"+map.get("item_prefix")+"%'");
		}
		if(Common.notEmpty(map.get("menu_code"))){  //菜单代码
			str.append(" and u.menu_code like '%"+map.get("menu_code")+"%'");
		}
		if(Common.notEmpty(map.get("parent_menu"))){  //父子菜单判断
			if("0".equals(map.get("parent_menu"))){	//父菜单
				str.append(" and u.parent_menu ='0'");
			}
			if("1".equals(map.get("parent_menu"))){	//子菜单
				str.append(" and u.parent_menu !='0'");
			}	
		}
		return str.toString();
	}
	
	//得到所有的父菜单
	public String findParentMenu(HttpServletRequest request){
		State state=new BaseState();
		try{
			String stk=URLDecoder.decode(request.getParameter("params"),"utf-8");
			Menu menu=JSON.parseObject(stk,Menu.class);
			return state.addState(AppInfo.SUCCESS,menuDao.findParentMenu(menu));
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
	
	//properties文件中项目的前缀
	public String menuItem(HttpServletRequest request){
		State state=new BaseState();
		PropertiesManager manager=new PropertiesManager();
		Map<String,String> map=manager.readProperties("project.properties");
		if(map==null){
			return state.addState(AppInfo.READ_PROPERTIES_ERROR,null);
		}
		return state.addState(AppInfo.SUCCESS,map);
	}
	
	public String checkOnly(HttpServletRequest request){
		State state=new BaseState();
		try{
			String st=URLDecoder.decode(request.getParameter("params"),"utf-8");   //相关条件
			Menu menu=JSON.parseObject(st,Menu.class);
			Power power=new Power();
			//同时 校验在权限资源中代码是否重复
			power.setPower_code(menu.getMenu_code());
			int flag=menuDao.checkOnly(menu);
			flag+=menuDao.checkOnlyPower(power);
			return state.addState(AppInfo.SUCCESS,flag);
		}
		catch(Exception e){
			e.printStackTrace();
			return state.addState(AppInfo.NO_PARAM,null);   //传入数据格式不正确
		}
	}
}
