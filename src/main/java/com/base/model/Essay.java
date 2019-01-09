package com.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 使用redis存对象，一定要实现Serializable接口，否则会报错。
 */
@ApiModel("文章实体类")
public class Essay implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6329114421795606475L;

	private Integer essayId;

    @ApiModelProperty(notes = "文章标题")
	private String title;

    @ApiModelProperty(notes = "发布时间")
	private String doTime;

    @ApiModelProperty(notes = "文章图片id")
	private Integer pictureId;

	//aboutMe关于我  essay文章
    @ApiModelProperty(notes = "文章类型", allowableValues = "aboutMe,ssay")
	private String essayType;

	//带格式文章内容
    @ApiModelProperty(notes = "带格式文章内容")
	private String content;

	//点击次数
    @ApiModelProperty(notes = "点击次数")
	private Integer clickNum;

	//站长推荐
    @ApiModelProperty(notes = "站长推荐")
	private Integer showLevel;

	/* 图片路径 */
	private String pictureName;

	/* 查询起始条数 */
	private Integer start;

	/* 每页查询条数 */
	private Integer rows;

	/* 搜索出现的次数 */
	private Integer searchtimes;

	/* 用于首页文章展示分类 */
	private String showType;
	
	public Integer getEssayId() {
		return essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getEssayType() {
		return essayType;
	}

	public void setEssayType(String essayType) {
		this.essayType = essayType;
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

	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public Integer getShowLevel() {
		return showLevel;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getSearchtimes() {
		return searchtimes;
	}

	public void setSearchtimes(Integer searchtimes) {
		this.searchtimes = searchtimes;
	}

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
}