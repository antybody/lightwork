package com.baosight.iwater.system.db.pojo;
/**
 * jdbc  数据库配置管理
 * 可以用来指定Properties文件中的键
 * @author liuwendong 
 *
 */
public class JDBC {
	private String driver;  		//连接驱动
	private String url;				//连接路径
	private String username;		//用户名
	private String password;		//密码
	private String initialSize;   	//初始连接数
	private String maxActive;		//最大连接数
	private String maxIdle;			//最大空闲连接数
	private String minIdle;			//最小空闲连接数
	private String maxWait;			//最长等待时间
	
	public JDBC(){
		
	}
	
	public JDBC(String driver, String url, String username, String password,
			String initialSize, String maxActive, String maxIdle,
			String minIdle, String maxWait) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.initialSize = initialSize;
		this.maxActive = maxActive;
		this.maxIdle = maxIdle;
		this.minIdle = minIdle;
		this.maxWait = maxWait;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(String initialSize) {
		this.initialSize = initialSize;
	}
	public String getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}
	public String getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}
	public String getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}
	public String getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}
	
}
