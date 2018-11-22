package com.base.model;

import java.io.Serializable;

/**
 * 使用redis存对象，一定要实现Serializable接口，否则会报错。
 */
public class UserInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4864434459129617760L;

	private Integer userId;

	private String userName;

	private String password;

	private Integer isAdmin;//是否为管理员 1:是 0:否

    private Boolean rememberMe;//是否记住密码

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}