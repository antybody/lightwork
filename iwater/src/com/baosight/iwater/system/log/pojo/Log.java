package com.baosight.iwater.system.log.pojo;

import com.baosight.iwater.system.login.pojo.User;

public class Log {
	private String ui_id;			    //主键
	private String add_date;			//添加时间
	private String up_date;				//修改时间
	private String log_desc;			//用途
	private String log_method;			//方法
	private String log_user;			//操作者
	private String log_type;			//类型
	private String log_IP;			    //请求IP
	private String log_excode;			//异常代码
	private String log_exdesc;			//异常描述
	private String log_params;			//请求参数
	private String log_date;			//操作时间
	private String del_modified;		//删除标识位
	
	
	public String getUi_id() {			
		return ui_id;
	}
	public void setUi_id(String ui_id) {
		this.ui_id = ui_id;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}
	public String getUp_date() {
		return up_date;
	}
	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}
	public String getLog_desc() {
		return log_desc;
	}
	public void setLog_desc(String log_desc) {
		this.log_desc = log_desc;
	}
	public String getLog_method() {
		return log_method;
	}
	public void setLog_method(String log_method) {
		this.log_method = log_method;
	}
	public String getLog_user() {
		return log_user;
	}
	public void setLog_user(String log_user) {
		this.log_user = log_user;
	}
	public String getLog_type() {
		return log_type;
	}
	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}
	public String getLog_IP() {
		return log_IP;
	}
	public void setLog_IP(String log_IP) {
		this.log_IP = log_IP;
	}
	public String getLog_excode() {
		return log_excode;
	}
	public void setLog_excode(String log_excode) {
		this.log_excode = log_excode;
	}
	public String getLog_exdesc() {
		return log_exdesc;
	}
	public void setLog_exdesc(String log_exdesc) {
		this.log_exdesc = log_exdesc;
	}
	public String getLog_params() {
		return log_params;
	}
	public void setLog_params(String log_params) {
		this.log_params = log_params;
	}
	public String getLog_date() {
		return log_date;
	}
	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}
	public String getDel_modified() {
		return del_modified;
	}
	public void setDel_modified(String del_modified) {
		this.del_modified = del_modified;
	}
	
	
	

}
