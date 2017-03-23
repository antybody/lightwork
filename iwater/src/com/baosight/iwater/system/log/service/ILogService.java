package com.baosight.iwater.system.log.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baosight.iwater.system.log.pojo.Log;

public interface ILogService {
	
	/**
	 * 查询全部权限资源   分页
	 * @param request
	 * @return
	 */
	String findAll(HttpServletRequest request);
	
	/**
	 * 保存日志
	 * @param log
	 * @return
	 */
    String add(Log log);

    /**
     * 删除日志
     * @param request
     * @return
     */
    String del(HttpServletRequest request);

    /**
     * 导出日志
     * @param request
     * @param response
     * @return
     */
	String download(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 根据主键查询日志
	 * @param request
	 * @return
	 */
	String selectByPrimaryKey(HttpServletRequest request);
	
}
