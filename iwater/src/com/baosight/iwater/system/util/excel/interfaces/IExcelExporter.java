/*
 * FileName: IExcelExporter.java
 * Copyright 1998-2013 宝信软件 公共服务事业部
 * All rights Reserved, Designed By BVS2.0
 * Author: gaoh
 * Version: 1.0
 * CreateDate: 2013-9-2
 * Modification History:
 * Date         Author    Version      Description
 * ------------------------------------------------------------
 * 2017-3-1		gaoh		1.0 		表格分析器
 */
package com.baosight.iwater.system.util.excel.interfaces;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface IExcelExporter {

	/**
	 * 导出excel-变长一维表
	 */
	XSSFWorkbook exportExcel(String fileName, String dataJsonString,String zhColArr,String enColArr);
	
	/**
	 * 导出excel-模板
	 */
	XSSFWorkbook exportExcelForTemplate(String filePath, String templateId, String filename, String dataJsonString);

	/**
	 * 导出excel-模板-单sheet
	 * 
	 * @param filePath
	 * @param tempId
	 * @param exprotFileName
	 * @param dataJsonString
	 * @return
	 */
	XSSFWorkbook exportTemplateForSingle(String filePath, String tempId, String exprotFileName, String dataJsonString);

	Map<String, Map<String, Map<String, String>>> listToMap(List<Map<String, String>> list);
}
