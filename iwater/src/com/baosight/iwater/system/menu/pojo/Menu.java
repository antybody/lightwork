package com.baosight.iwater.system.menu.pojo;
/**
 * 菜单
 * @author liuwendong
 *
 */
public class Menu {
	private String ui_id;			//主键
	private String add_date;		//添加时间	
	private String up_date;			//修改时间
	private String user_info;		//操作者	
	private String menu_zhname;		//菜单中文名称
	private String menu_enname;		//菜单英文名称
	private String menu_code;		//菜单代码
	private String item_prefix;		//所属项目
	private String parent_menu;		//父级菜单
	private String menu_url;		//菜单路径
	private String menu_sort;		//菜单排序
	private String menu_class;		//菜单样式	
	private String pic_class;		//菜单图标样式
	private String is_del;			//删除标志位
	
	
	private String parent_menuName;  //父级菜单名称  方便显示
	public Menu(){
		
	}
	
	public Menu(String ui_id, String add_date, String up_date,
			String user_info, String menu_zhname, String menu_enname,
			String menu_code, String item_prefix, String parent_menu,
			String menu_url, String menu_sort, String menu_class,
			String pic_class, String is_del) {
		super();
		this.ui_id = ui_id;
		this.add_date = add_date;
		this.up_date = up_date;
		this.user_info = user_info;
		this.menu_zhname = menu_zhname;
		this.menu_enname = menu_enname;
		this.menu_code = menu_code;
		this.item_prefix = item_prefix;
		this.parent_menu = parent_menu;
		this.menu_url = menu_url;
		this.menu_sort = menu_sort;
		this.menu_class = menu_class;
		this.pic_class = pic_class;
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
	public String getMenu_zhname() {
		return menu_zhname;
	}
	public void setMenu_zhname(String menu_zhname) {
		this.menu_zhname = menu_zhname;
	}
	public String getMenu_enname() {
		return menu_enname;
	}
	public void setMenu_enname(String menu_enname) {
		this.menu_enname = menu_enname;
	}
	public String getMenu_code() {
		return menu_code;
	}
	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}
	public String getMenu_sort() {
		return menu_sort;
	}
	public void setMenu_sort(String menu_sort) {
		this.menu_sort = menu_sort;
	}
	public String getMenu_class() {
		return menu_class;
	}
	public void setMenu_class(String menu_class) {
		this.menu_class = menu_class;
	}
	public String getPic_class() {
		return pic_class;
	}
	public void setPic_class(String pic_class) {
		this.pic_class = pic_class;
	}
	public String getIs_del() {
		return is_del;
	}
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	public String getParent_menu() {
		return parent_menu;
	}
	public void setParent_menu(String parent_menu) {
		this.parent_menu = parent_menu;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public String getItem_prefix() {
		return item_prefix;
	}
	public void setItem_prefix(String item_prefix) {
		this.item_prefix = item_prefix;
	}
	public String getParent_menuName() {
		return parent_menuName;
	}
	public void setParent_menuName(String parent_menuName) {
		this.parent_menuName = parent_menuName;
	}
	
	
}
