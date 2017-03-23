package com.baosight.iwater.system.db.pojo;
/**
 *redis  数据库配置管理
 *可以用来指定Properties文件中的键
 * @author liuwendong
 *
 */
public class Redis {
	private String redis_addr;  			//访问地址
	private String redis_port;				//访问端口
	private String redis_auth;				//授权密码
	private String redis_max_total;			//连接池的最大数据库连接数
	private String redis_max_idle;			//最大空闲数
	private String redis_max_wait;			//最大建立连接等待时间
	private String redis_test_on_borrow;  	//borrow相关操作标志
	
	public Redis(){
		
	}
	
	public Redis(String redis_addr, String redis_port, String redis_auth,
			String redis_max_total, String redis_max_idle,
			String redis_max_wait, String redis_test_on_borrow) {
		super();
		this.redis_addr = redis_addr;
		this.redis_port = redis_port;
		this.redis_auth = redis_auth;
		this.redis_max_total = redis_max_total;
		this.redis_max_idle = redis_max_idle;
		this.redis_max_wait = redis_max_wait;
		this.redis_test_on_borrow = redis_test_on_borrow;
	}

	public String getRedis_addr() {
		return redis_addr;
	}
	public void setRedis_addr(String redis_addr) {
		this.redis_addr = redis_addr;
	}
	public String getRedis_port() {
		return redis_port;
	}
	public void setRedis_port(String redis_port) {
		this.redis_port = redis_port;
	}
	public String getRedis_auth() {
		return redis_auth;
	}
	public void setRedis_auth(String redis_auth) {
		this.redis_auth = redis_auth;
	}
	public String getRedis_max_total() {
		return redis_max_total;
	}
	public void setRedis_max_total(String redis_max_total) {
		this.redis_max_total = redis_max_total;
	}
	public String getRedis_max_idle() {
		return redis_max_idle;
	}
	public void setRedis_max_idle(String redis_max_idle) {
		this.redis_max_idle = redis_max_idle;
	}
	public String getRedis_max_wait() {
		return redis_max_wait;
	}
	public void setRedis_max_wait(String redis_max_wait) {
		this.redis_max_wait = redis_max_wait;
	}
	public String getRedis_test_on_borrow() {
		return redis_test_on_borrow;
	}
	public void setRedis_test_on_borrow(String redis_test_on_borrow) {
		this.redis_test_on_borrow = redis_test_on_borrow;
	}
	
}
