package com.base.model;

import java.io.Serializable;

/**
 * 使用redis存对象，一定要实现Serializable接口，否则会报错。
 */
public class Picture implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8824348070765131100L;

	private Integer pictureId;

	private String pictureName;

	private String pictureTitle;

	private String pictureContent;

	// aboutMe关于我 other文章 mood心情 index幻灯片 photo相片
	private String pictureType;

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getPictureTitle() {
		return pictureTitle;
	}

	public void setPictureTitle(String pictureTitle) {
		this.pictureTitle = pictureTitle;
	}

	public String getPictureContent() {
		return pictureContent;
	}

	public void setPictureContent(String pictureContent) {
		this.pictureContent = pictureContent;
	}

	public String getPictureType() {
		return pictureType;
	}

	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}
}