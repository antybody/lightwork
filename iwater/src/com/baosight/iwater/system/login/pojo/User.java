package com.baosight.iwater.system.login.pojo;

public class User {
	private String ui_id;
	private String add_date;
	private String up_date;
	private String user_info;
	private String user_code;
	private String user_pwd;
	private String user_name;
	private String user_mail;
	private String user_tel;
	private String user_phone;
	private String del_modified;
	
	public User(){
		
	}
	
	public User(String ui_id, String add_date, String up_date,
			String user_info, String user_code, String user_pwd,
			String user_name, String user_mail, String user_tel,
			String user_phone, String del_modified) {
		super();
		this.ui_id = ui_id;
		this.add_date = add_date;
		this.up_date = up_date;
		this.user_info = user_info;
		this.user_code = user_code;
		this.user_pwd = user_pwd;
		this.user_name = user_name;
		this.user_mail = user_mail;
		this.user_tel = user_tel;
		this.user_phone = user_phone;
		this.del_modified = del_modified;
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
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_mail() {
		return user_mail;
	}
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getDel_modified() {
		return del_modified;
	}
	public void setDel_modified(String del_modified) {
		this.del_modified = del_modified;
	}
	
	
}
