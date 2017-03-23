package com.baosight.iwater.system.define;

import java.util.List;

/**
 * 分页JavaBean
 * @author liuwendong
 * @since  2017-1-12
 */
public class PageInfo {

	/**
	 * 当前页数
	 */
	int pageNo=1;
	
    /**
     * 总页数
     */
    int totalPage=-1;
    
    /**
     * 总记录数
     */
    int totalCount=-1;
    
    /**
     * 页记录数
     */
    int pageSize=10;
    
    /**
     * 开始数
     */
    int start=0;
    
    //装载记录
    private List pageList;
    /**
     * 结束数
     */
    int end;
    
    String roleId;
    String role_Id;
    String isOwer;
    String userId;
    String thread;

    
    
    public List getPageList() {
		return pageList;
	}
	public void setPageList(List pageList) {
		this.pageList = pageList;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
     * @return 当前页数
     */
    public int getPageNo() {
        return pageNo;
    }
    /**
     * @param pageNo 当前页数
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    /**
     * @return 页记录数
     */
    public int getPageSize() {
        return pageSize;
    }
    /**
     * @param pageSize 页记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * @return 总记录数
     */
    public int getTotalCount() {
        return totalCount;
    }
    /**
     * @param tatalCount 总记录数
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    /**
     * @return 总页数
     */
    public int getTotalPage() {
    	int tp = 0;
    	if(this.getTotalCount() % this.getPageSize() ==0)
    	{
    		tp = this.getTotalCount() / this.getPageSize();
    	}else{
    		tp = this.getTotalCount() / this.getPageSize() + 1;
    	}
        return tp;
    }
    /**
     * @param totalpage 总页数
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    /**
     * @return 开始数
     */
    public int getStart() {
    
      return (pageNo-1)*pageSize;
    }
	public String getIsOwer() {
		return isOwer;
	}
	public void setIsOwer(String isOwer) {
		this.isOwer = isOwer;
	}
	public String getRole_Id() {
		return role_Id;
	}
	public void setRole_Id(String role_Id) {
		this.role_Id = role_Id;
	}
}
