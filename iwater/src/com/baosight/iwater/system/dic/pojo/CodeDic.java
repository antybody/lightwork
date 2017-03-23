package com.baosight.iwater.system.dic.pojo;

public class CodeDic {

	private String dic_id;
	private String dic_code;
	private String dic_name;
	private String parent_code;
	private String dic_desc;
	private String create_date;
	private String update_date;
	private String is_usable;
	private String seq;
	private String create_name;
	private String update_name;
	
	public CodeDic(){
		
	}
	
	public CodeDic(String dic_id, String dic_code, String dic_name, String parent_code,
			String dic_desc, String create_date, String update_date, String is_usable,
			String seq, String create_name, String update_name){
		super();
		this.dic_id = dic_id;
		this.dic_code = dic_code;
		this.dic_name = dic_name;
		this.parent_code = parent_code;
		this.dic_desc = dic_desc;
		this.create_date = create_date;
		this.update_date = update_date;
		this.is_usable = is_usable;
		this.seq = seq;
		this.create_name = create_name;
		this.update_name = update_name;
	}

	public String getDic_id() {
		return dic_id;
	}

	public void setDic_id(String dic_id) {
		this.dic_id = dic_id;
	}

	public String getDic_code() {
		return dic_code;
	}

	public void setDic_code(String dic_code) {
		this.dic_code = dic_code;
	}

	public String getDic_name() {
		return dic_name;
	}

	public void setDic_name(String dic_name) {
		this.dic_name = dic_name;
	}

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	public String getDic_desc() {
		return dic_desc;
	}

	public void setDic_desc(String dic_desc) {
		this.dic_desc = dic_desc;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getIs_usable() {
		return is_usable;
	}

	public void setIs_usable(String is_usable) {
		this.is_usable = is_usable;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public String getUpdate_name() {
		return update_name;
	}

	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
	}
	
	
}
