package com.base.model;

import java.io.Serializable;

/**
 * 使用redis存对象，一定要实现Serializable接口，否则会报错。
 */
public class AboutMe implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5098980254238808067L;

	private Integer aboutMeId;

	private String realName;

	private String address;

	private String nowAddress;

	private String likeBook;

	private String likeMusic;

	private Integer essayId;

	private Integer pictureId;

	/* 图片路径 */
	private String pictureName;

	public Integer getAboutMeId() {
		return aboutMeId;
	}

	public void setAboutMeId(Integer aboutMeId) {
		this.aboutMeId = aboutMeId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNowAddress() {
		return nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}

	public String getLikeBook() {
		return likeBook;
	}

	public void setLikeBook(String likeBook) {
		this.likeBook = likeBook;
	}

	public String getLikeMusic() {
		return likeMusic;
	}

	public void setLikeMusic(String likeMusic) {
		this.likeMusic = likeMusic;
	}

	public Integer getEssayId() {
		return essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}

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
}