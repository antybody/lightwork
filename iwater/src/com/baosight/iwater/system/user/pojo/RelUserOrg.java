package com.baosight.iwater.system.user.pojo;
/**
 * 用户与组织机构关系表
 * @author liuwendong
 *
 */
public class RelUserOrg {
	private String user_code;
	private String org_code;
	private String del_modified;
	private String gmt_date;
	
	private String org_zhname;  //组织机构名称   方便查询显示
	public RelUserOrg(){
		
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
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
	public String getOrg_zhname() {
		return org_zhname;
	}
	public void setOrg_zhname(String org_zhname) {
		this.org_zhname = org_zhname;
	}
	
}
