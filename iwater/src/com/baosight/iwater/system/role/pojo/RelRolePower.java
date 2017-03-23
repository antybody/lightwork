package com.baosight.iwater.system.role.pojo;
/**
 * 角色与权限关系表
 * @author liuwendong
 *
 */
public class RelRolePower {
	private String role_code;	//角色代码
	private String power_code;	//权限代码
	private String power_owner; //所属菜单代码(类型是页面使用)
	private String power_follower; 	//权限资源跟从(类型是页面使用)
	private String power_type;	//权限资源类型 (方便区分 接口  菜单 页面)
	private String del_modified;//删除标志位
	private String gmt_date;	//编辑时间
	
	private String power_name;    //权限名称  方便查询显示
	
	public RelRolePower(){
		
	}
	
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	public String getPower_code() {
		return power_code;
	}
	public void setPower_code(String power_code) {
		this.power_code = power_code;
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
	public String getPower_name() {
		return power_name;
	}
	public void setPower_name(String power_name) {
		this.power_name = power_name;
	}

	public String getPower_owner() {
		return power_owner;
	}

	public void setPower_owner(String power_owner) {
		this.power_owner = power_owner;
	}

	public String getPower_follower() {
		return power_follower;
	}

	public void setPower_follower(String power_follower) {
		this.power_follower = power_follower;
	}

	public String getPower_type() {
		return power_type;
	}

	public void setPower_type(String power_type) {
		this.power_type = power_type;
	}
	
}
