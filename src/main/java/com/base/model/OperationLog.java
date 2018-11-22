package com.base.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志(系统所有操作日志)
 */
//表明myBlog数据库的集合名为：operationLog
@Document(collection = "operationLog")
public class OperationLog implements Serializable {
    private static final long serialVersionUID = -2744897826542617815L;

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 操作用户
     */
    private String userName;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date operationTime;

    /**
     * 耗时（单位毫秒）
     */
    private Long timeConsuming;

    /**
     * URI
     */
    private String requestUri;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * IP地址
     */
    private String remoteAddr;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 代理
     */
    private String userAgent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Long getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(Long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
