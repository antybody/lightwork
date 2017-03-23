/*
 * FileName: ExcelExporter.java
 * Copyright 1998-2013 宝信软件 公共服务事业部
 * All rights Reserved, Designed By BVS2.0
 * Author: gaoh
 * Version: 1.0
 * CreateDate: 2013-9-2
 * Modification History:
 * Date         Author    Version      Description
 * ------------------------------------------------------------
 * 2017-3-1		gaoh		1.0			表格分析器
 */

package com.baosight.iwater.system.util.excel.arch;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.baosight.iwater.system.util.excel.interfaces.IExcelExporter;

public class ExcelExporter implements IExcelExporter{

	/**
	 * 无模板简单导出-适用于一维表导出（grid列表）-单sheet
	 * 
	 * @author gaoh
	 * @version	1.0
	 */
	@Override
	public XSSFWorkbook exportExcel(String fileName, String dataJsonString,String zhColArr,String enColArr) {
		//设置导出文件名
//		String exprotFileName = fileName+".xlsx";
		//创建工作簿
		XSSFWorkbook workBook = new XSSFWorkbook();
		//创建sheet
		XSSFSheet sheet=workBook.createSheet();
		workBook.setSheetName(0,fileName);
		//数据字符串转数组
		JSONArray dataJson = new JSONArray(dataJsonString);
		//调用sheet分析方法
		analysisSheet(fileName,workBook,sheet,dataJson,zhColArr,enColArr);
		return workBook;
	}
	
	
	
	/**
	 * 无模板导出内核-单sheet
	 * 
	 */
	public void analysisSheet(String fileName, XSSFWorkbook workBook, Sheet sheet,
			JSONArray dataJson, String zhColArr, String enColArr){
		//设置样式1
		XSSFFont font1=workBook.createFont();
		font1.setColor(XSSFFont.COLOR_NORMAL);
		//设置样式2
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
		//拼接标题内容
		StringBuffer otherTitle=new StringBuffer("lightWork");
		otherTitle.append(fileName);
		//1.首先向excel中填入标题
		XSSFRow headRow=(XSSFRow) sheet.createRow((short)0);//第一行主标题
		XSSFCell headCell = headRow.createCell(0);
		headCell.setCellStyle(titleStyle);
		headCell.setCellType(XSSFCell.CELL_TYPE_STRING);
		headCell.setCellValue(otherTitle.toString());
		//获取中文列数组
		JSONArray title = new JSONArray(zhColArr);
		//单元格,createCell(i),这里的i代表单元格是第几列，
		//CellRangeAddress(firstRow,lastRow,firstCol,lastCol)里的参数分别表示需要合并的单元格起始行，起始列 
		sheet.addMergedRegion((new CellRangeAddress(0, 0, 0, title.length()-1)));
		//创建第一行标题
		XSSFRow titleRow=(XSSFRow) sheet.createRow((short)1);//第二行的标题
		//在第一行的基础上 创建列
		for(int i=0;i<title.length();i++){
			XSSFCell cell = titleRow.createCell(i);
			cell.setCellStyle(titleStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title.getString(i));
			String colValue = title.getString(i);
			//调整列宽
			int valuelen = (colValue.getBytes().length)*256;
			int curlen = sheet.getColumnWidth(i);
			if(valuelen > curlen){
				sheet.setColumnWidth(i,valuelen);
			}
		}
		//设置数据单元格
		XSSFRow contentRow=null;
		XSSFCell cell=null;
		JSONArray entitle = new JSONArray(enColArr);
		for(int i=0;i<dataJson.length();i++){
			contentRow = (XSSFRow) sheet.createRow((short) i+2);
			JSONObject menuMap = (JSONObject) dataJson.get(i);
			for(int j=0;j<entitle.length();j++){
				cell = contentRow.createCell(j);
				cell.setCellStyle(titleStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				String enCol = (String) entitle.get(j);
				String dataValue = (String)menuMap.get(enCol);
				cell.setCellValue(dataValue);
				//调整列宽
				int valuelen = (dataValue.getBytes().length)*256;
				int curlen = sheet.getColumnWidth(j);
				if(valuelen > curlen){
					sheet.setColumnWidth(j,valuelen);
				}
			}
		}
	}
	
	
	
	/**
	 * 模板导出-不推荐变长一维表使用-单sheet
	 * 
	 * @author gaoh
	 * @version 1.0
	 */
	public XSSFWorkbook exportTemplateForSingle(String filePath, String tempId,
			String fileName, String dataJsonStr){
		//创建工作簿
		XSSFWorkbook workBook = null;
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(filePath);
			workBook = new XSSFWorkbook(fileIn);
		} catch (FileNotFoundException e) {
			workBook = null;
			System.out.println("exportExcelForTemplate $$$$$$ 导出缓存文件不存在！");
			System.out.println(e);
		} catch (IOException e) {
			workBook = null;
			System.out.println("exportExcelForTemplate $$$$$$ 导出缓存文件IO错误！");
			System.out.println(e);
		}
		if(workBook != null){
			//单sheet导出，因此直接取第0个
			Sheet exportSheet = workBook.getSheetAt(0);
			JSONObject dataJson = new JSONObject(dataJsonStr);
			//调用单sheet分析的方法
			analysisDataToSheet(workBook,exportSheet,dataJson);
		}
		return workBook;
	}



	/**
	 * 模板导出内核-单个sheet解析
	 * 
	 * 约定数据格式为：
	 * {
	 * 	 "rowNum1":{"colNum1":{"val":""},"colNum2":{"val":""}},
	 * 	 "rowNum2":{"colNum1":{"val":""},"colNum2":{"val":""}}
	 * }
	 * （可类似：最里层的对象只要具有val属性即可，其他属性可以随意）
	 */
	public void analysisDataToSheet(XSSFWorkbook currentWorkBook, Sheet currentSheet,
			JSONObject dataJson){
		// 获取第一行
		int firstRow = currentSheet.getFirstRowNum();
		// 最后一行
		int lastRow = currentSheet.getLastRowNum();
		for (int i = firstRow; i <= lastRow; ++i) {
			// 遍历每一行
			Row currentRow = currentSheet.getRow(i);
			if (currentRow != null) {
				try{
					//当前行号：约定是每个对象的key
					String currRowNum = i + "";
					JSONObject rowDataObj = (JSONObject) dataJson.get(currRowNum);
					//获取模板当前行的总列数
					int colNum = currentRow.getPhysicalNumberOfCells();
					for(int j = 0; j < colNum; j++){
						String currColNum = j + "";
						try{
							//获取当前单元格数据
							JSONObject cellDataObj = (JSONObject) rowDataObj.get(currColNum);
							String cellData = cellDataObj.getString("val");
							//获取当前模板单元格cell对象
							Cell currCell = currentRow.getCell(j);
							currCell.setCellValue(cellData);
							//设置单元格样式
							XSSFCellStyle  cs = (XSSFCellStyle )currentWorkBook.createCellStyle();
							cs.setBorderBottom(CellStyle.BORDER_THIN);
							cs.setBottomBorderColor(HSSFColor.BLACK.index);
//							cs.setTopBorderColor(HSSFColor.BLACK.index);
//							cs.setLeftBorderColor(HSSFColor.BLACK.index);
							cs.setBorderRight(CellStyle.BORDER_THIN);
							cs.setRightBorderColor(HSSFColor.BLACK.index);
							XSSFFont font = (XSSFFont)currentWorkBook.createFont();
							font.setColor(HSSFColor.BLACK.index);
							font.setFontName("宋体");
							font.setFontHeightInPoints((short) 9);
							cs.setFont(font);
							currCell.setCellStyle(cs);
							currCell.setAsActiveCell();
						}catch (Exception e) {
							// TODO: handle exception
							System.out.println("模板解析$$$$$$$$$第"+i+"行、第"+j+"列的格子无数据");
						}
					}
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("模板解析$$$$$$$$$第"+i+"行无数据");
				}
			}
		}
	}
	
	
	
	/**
	 * 模板导出-多sheet
	 * 
	 * @author gaoh
	 * @version	1.0
	 */
	public XSSFWorkbook exportExcelForTemplate(String filePath, String templateId,
			String filename, String dataJsonString){
		//创建工作簿
		XSSFWorkbook workBook = null;
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(filePath);
			workBook = new XSSFWorkbook(fileIn);
		} catch (FileNotFoundException e) {
			workBook = null;
			System.out.println("exportExcelForTemplate $$$$$$ 导出缓存文件不存在！");
			System.out.println(e);
		} catch (IOException e) {
			workBook = null;
			System.out.println("exportExcelForTemplate $$$$$$ 导出缓存文件IO错误！");
			System.out.println(e);
		}
		if(workBook != null){
			JSONObject dataJson = new JSONObject(dataJsonString);
			int sheetNum = workBook.getNumberOfSheets();
			for(int i = 0; i < sheetNum; i++){
				Sheet exportSheet = workBook.getSheetAt(i);
				String sheetName = exportSheet.getSheetName();
				String sheetId = sheetName.substring(sheetName.lastIndexOf("(")+1,sheetName.lastIndexOf(")"));
				if(sheetName.contains("一维")){
					String zhColArr = new JSONArray("['主键','字典项编码','字典项名称','父节点','是否启用']").toString();
					String enColArr = new JSONArray("['dic_id','dic_code','dic_name','parent_code','is_usable']").toString();
					JSONArray sheetData = (JSONArray) dataJson.get(sheetId);
					analysisSheet(sheetName,workBook,exportSheet,sheetData,zhColArr,enColArr);
				}else{
					JSONObject sheetData = (JSONObject) dataJson.get(sheetId);
					analysisDataToSheet(workBook,exportSheet,sheetData);
				}
			}
		}
		return workBook;
	}
	
	
	
	
	/**
	 * list转map：把list转成如下结构
	 * {
	 * 	 "rowNum1":{"colNum1":{"val":""},"colNum2":{"val":""}},
	 * 	 "rowNum2":{"colNum1":{"val":""},"colNum2":{"val":""}}
	 * }
	 * 便于模板分析时使用
	 */
	public Map<String,Map<String,Map<String,String>>> listToMap(List<Map<String,String>> list){
		Map<String,Map<String,Map<String,String>>> lmap = new HashMap<String, Map<String,Map<String,String>>>();
		Map<String,Map<String,String>> mMap = new HashMap<String, Map<String,String>>();
		for(int i = 0; i < list.size(); i++){
			Map<String,String> smap = list.get(i);
			String rowNum = smap.get("row_num");
			String colNum = smap.get("col_num");
			if(lmap.get(rowNum)==null){
				mMap = new HashMap<String, Map<String,String>>();
			}else{
				mMap = lmap.get(rowNum);
			}
			mMap.put(colNum, smap);
			lmap.put(rowNum, mMap);
		}
		return lmap;
	}
}