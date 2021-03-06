/*
 * 
 * 
 * 
 * 
 */

package com.yuntao.zhushou.model.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口主体
 * @author admin
 *
 * @2016-07-30 20
 */
public class IdocUrl implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**  ID * */
	private Long id;

	/**
	 * 企业id
	 */
	private Long companyId;

	private Long projectId;

	/**  URL * */
	private String url;
		
	/**  名称 * */
	private String name;
		
	/**  应用 * */
	private String appName;
		
	/**  模块 * */
	private String module;
		
	/**  版本 * */
	private String version;

	/**  返回数据 * */
	private String resultData;

	/**  返回注解数据 * */
	private String resultMockData;

	/**  mock状态(0 未开启，1 已开启) * */
	private Integer status;

	/**  状态 * */
	private Integer mockStatus;
		
	/**  创建时间 * */
	private Date gmtCreate;
		
	/**  修改时间 * */
	private Date gmtModify;
		
	/**  是否删除（1：没有；0；已删除） * */
	private Integer delStatus;
		
	/**  说明 * */
	private String memo;
		
	/**  创建人ID * */
	private Long createUserId;
		
	/**  创建用户名称 * */
	private String createUserName;


	/**
	 * 类型 0 文档; 1 枚举; 2 资源文件
	 */
	private Integer type;

	/**
	 * 资源id
	 */
	private Long showResId;

	/**
	 * mock 数据id
	 */
	private Long mockDataId;

	
	public IdocUrl(){
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setModule(String value) {
		this.module = value;
	}
	
	public String getModule() {
		return this.module;
	}
	public void setVersion(String value) {
		this.version = value;
	}
	
	public String getVersion() {
		return this.version;
	}
	public void setResultData(String value) {
		this.resultData = value;
	}
	
	public String getResultData() {
		return this.resultData;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setGmtCreate(Date value) {
		this.gmtCreate = value;
	}
	
	public Date getGmtCreate() {
		return this.gmtCreate;
	}
	public void setGmtModify(Date value) {
		this.gmtModify = value;
	}
	
	public Date getGmtModify() {
		return this.gmtModify;
	}
	public void setDelStatus(Integer value) {
		this.delStatus = value;
	}
	
	public Integer getDelStatus() {
		return this.delStatus;
	}
	public void setMemo(String value) {
		this.memo = value;
	}
	
	public String getMemo() {
		return this.memo;
	}
	public void setCreateUserId(Long value) {
		this.createUserId = value;
	}
	
	public Long getCreateUserId() {
		return this.createUserId;
	}
	public void setCreateUserName(String value) {
		this.createUserName = value;
	}
	
	public String getCreateUserName() {
		return this.createUserName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getResultMockData() {
		return resultMockData;
	}

	public void setResultMockData(String resultMockData) {
		this.resultMockData = resultMockData;
	}

	public Long getShowResId() {
		return showResId;
	}

	public void setShowResId(Long showResId) {
		this.showResId = showResId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getMockDataId() {
		return mockDataId;
	}

	public void setMockDataId(Long mockDataId) {
		this.mockDataId = mockDataId;
	}

	public Integer getMockStatus() {
		return mockStatus;
	}

	public void setMockStatus(Integer mockStatus) {
		this.mockStatus = mockStatus;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
