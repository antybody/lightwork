package com.baosight.iwater.system.dic.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDicService {

	
	public String initLoad(HttpServletRequest request);
	
	public String nextLevelNode(HttpServletRequest request);
	
	public String nextLevelNodeForAll(HttpServletRequest request);
	
	public String queryForExist(HttpServletRequest request);
	
	public String insertForNew(HttpServletRequest request);
	
	public String update(HttpServletRequest request);
	
	public String deleteNode(HttpServletRequest request);
	
	public String updateIsable(HttpServletRequest request);
	
	public String loadBeforeUpdate(HttpServletRequest request);
	
	public String queryForDicCode(HttpServletRequest request);
	
	public String download(HttpServletRequest request,HttpServletResponse response);
}
