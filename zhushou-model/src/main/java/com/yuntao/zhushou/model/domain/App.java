package com.yuntao.zhushou.model.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shengshan.tang on 2015/12/12 at 15:11
 */
public class App implements Serializable {

    private static final long serialVersionUID = 1L;


    /*/** ID */
    private Long id;

    /**
     * 企业id
     */
    private Long companyId;

    /**
     * 企业id
     */
    private Long projectId;

    /**
     * 名称
     */
    private String name;

    /**
     * 负责人Id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 是否删除
     */
    private Integer delStatus;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 日志
     */
    private String log;

    /**
     * 描述
     */
    private String desc;

    /**
     * git代码名称
     */
    private String codeName;

    /**
     * 域名；user.api.yuntaohongbao.com
     */
    private String domain;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 是否前端（1 是，0 否）
     */
    private Integer front;

    /**
     * 编译变量
     */
    private String compileProperty;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getFront() {
        return front;
    }

    public void setFront(Integer front) {
        this.front = front;
    }

    public String getCompileProperty() {
        return compileProperty;
    }

    public void setCompileProperty(String compileProperty) {
        this.compileProperty = compileProperty;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
