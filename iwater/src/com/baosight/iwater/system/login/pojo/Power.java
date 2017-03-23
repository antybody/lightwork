package com.baosight.iwater.system.login.pojo;
/**
 * 权限资源
 * @author liuwendong
 *
 */
public class Power {
	private String ui_id;	//主键
	private String add_date;	//添加时间
	private String up_date;		//修改时间
	private String user_info; 	//操作者
	private String power_type;	//权限类型		数据字典
	private String power_owner;  //权限权属      页面才有
	private String power_prefix;  //权限所属项目
	private String power_name;	//权限名称
	private String power_code;	//权限代码
	private String power_url;	//对应地址
	private String power_token;  //权限token(公共接口使用)
	private String power_follower;	//权限跟从
	private String del_modified;	//删除标志位
	
	private String role_code;   //对应的角色代码       方便显示和数据采集
	public Power(){
		
	}
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
	public String getUser_info() {
		return user_info;
	}
	public void setUser_info(String user_info) {
		this.user_info = user_info;
	}
	public String getPower_type() {
		return power_type;
	}
	public void setPower_type(String power_type) {
		this.power_type = power_type;
	}
	public String getPower_owner() {
		return power_owner;
	}
	public void setPower_owner(String power_owner) {
		this.power_owner = power_owner;
	}
	public String getPower_prefix() {
		return power_prefix;
	}
	public void setPower_prefix(String power_prefix) {
		this.power_prefix = power_prefix;
	}
	public String getPower_name() {
		return power_name;
	}
	public void setPower_name(String power_name) {
		this.power_name = power_name;
	}
	public String getPower_code() {
		return power_code;
	}
	public void setPower_code(String power_code) {
		this.power_code = power_code;
	}
	public String getPower_url() {
		return power_url;
	}
	public void setPower_url(String power_url) {
		this.power_url = power_url;
	}
	public String getPower_token() {
		return power_token;
	}
	public void setPower_token(String power_token) {
		this.power_token = power_token;
	}
	public String getPower_follower() {
		return power_follower;
	}
	public void setPower_follower(String power_follower) {
		this.power_follower = power_follower;
	}
	public String getDel_modified() {
		return del_modified;
	}
	public void setDel_modified(String del_modified) {
		this.del_modified = del_modified;
	}
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	
	
}
