package com.base.model;

import java.io.Serializable;

/**
 * 使用redis存对象，一定要实现Serializable接口，否则会报错。
 */
public class Mood implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7274978595941578937L;

	private Integer moodId;

	private String doTime;

	private Integer pictureId;

	private String content;

	/* 图片路径 */
	private String pictureName;

	public Integer getMoodId() {
		return moodId;
	}

	public void setMoodId(Integer moodId) {
		this.moodId = moodId;
	}

	public String getDoTime() {
		return doTime;
	}

	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
}