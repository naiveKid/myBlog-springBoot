package com.base.model;

import java.io.Serializable;

/**
 * 使用redis存对象，一定要实现Serializable接口，否则会报错。
 */
public class Notice implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7044314854449032602L;

	private Integer noticeId;

	private Integer doUserId;

	private String content;
	/* 留言姓名 */
	private String userName;

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public Integer getDoUserId() {
		return doUserId;
	}

	public void setDoUserId(Integer doUserId) {
		this.doUserId = doUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}