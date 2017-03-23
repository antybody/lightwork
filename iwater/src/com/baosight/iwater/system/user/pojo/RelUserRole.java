package com.baosight.iwater.system.user.pojo;
/**
 * 用户与角色关系表
 * @author liuwendong
 *
 */
public class RelUserRole {
	private String user_code;  //账号
	private String role_code;	//角色代号
	private String del_modified;//删除标志位
	private String gmt_date;	//编辑日期
	
	private String role_zhname;   //角色名称  方便查询显示
	public RelUserRole(){
		
	}
	
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	public String getDel_modified() {
		return del_modified;
	}
	public void setDel_modified(String del_modified) {
		this.del_modified = del_modified;
	}
	public String getGmt_date() {
		return gmt_date;
	}
	public void setGmt_date(String gmt_date) {
		this.gmt_date = gmt_date;
	}
	public String getRole_zhname() {
		return role_zhname;
	}
	public void setRole_zhname(String role_zhname) {
		this.role_zhname = role_zhname;
	}
	
}
