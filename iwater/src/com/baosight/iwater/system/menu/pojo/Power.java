package com.baosight.iwater.system.menu.pojo;
/**
 * 权限资源表
 * @author liuwendong
 *
 */
public class Power {
	private String ui_id;	//主键
	private String add_date;	//添加时间
	private String up_date;		//修改时间
	private String user_info; 	//操作者
	private String power_type;	//权限类型		数据字典
	private String power_owner;  //权限权属      仅页面使用
	public String getPower_parent() {
		return power_parent;
	}

	public void setPower_parent(String power_parent) {
		this.power_parent = power_parent;
	}

	private String power_parent;  //父菜单ui_id  仅菜单使用
	private String power_prefix;  //权限所属项目
	private String power_name;	//权限名称
	private String power_code;	//权限代码
	private String power_url;	//对应地址
	private String power_follower;	//权限跟从
	private String del_modified;	//删除标志位
	
	private String power_ownerName;   //页面代码对应的名称   方便显示
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

	public String getPower_prefix() {
		return power_prefix;
	}

	public void setPower_prefix(String power_prefix) {
		this.power_prefix = power_prefix;
	}

	public String getPower_url() {
		return power_url;
	}

	public void setPower_url(String power_url) {
		this.power_url = power_url;
	}

	public String getPower_ownerName() {
		return power_ownerName;
	}

	public void setPower_ownerName(String power_ownerName) {
		this.power_ownerName = power_ownerName;
	}
	
	
}
