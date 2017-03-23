package com.baosight.iwater.system.role.pojo;
/**
 * 平台角色
 * @author liuwendong 
 *
 */
public class Role {
	private String ui_id;
	private String add_date;
	private String up_date;
	private String user_info;
	private String role_type;    //角色类型  数据字典
	private String role_zhname;
	private String role_enname;
	private String role_code;
	private String is_del;
	
	private String rel_role_power;  //角色权限 关系 方便显示
	public Role(){
		
	}
	
	public Role(String ui_id, String add_date, String up_date,
			String user_info, String role_zhname, String role_enname,
			String role_code, String is_del) {
		super();
		this.ui_id = ui_id;
		this.add_date = add_date;
		this.up_date = up_date;
		this.user_info = user_info;
		this.role_zhname = role_zhname;
		this.role_enname = role_enname;
		this.role_code = role_code;
		this.is_del = is_del;
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

	public String getRole_zhname() {
		return role_zhname;
	}

	public void setRole_zhname(String role_zhname) {
		this.role_zhname = role_zhname;
	}

	public String getRole_enname() {
		return role_enname;
	}

	public void setRole_enname(String role_enname) {
		this.role_enname = role_enname;
	}

	public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public String getRel_role_power() {
		return rel_role_power;
	}

	public void setRel_role_power(String rel_role_power) {
		this.rel_role_power = rel_role_power;
	}
	
	
	
}	
