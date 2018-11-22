package com.core.dao;

import com.base.model.Picture;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PictureMapper {
	Picture selectByPrimaryKey(Integer pictureId);

	int deleteByPrimaryKey(Integer pictureId);

	int insertSelective(Picture record);

	int updateByPrimaryKeySelective(Picture record);

	int getCount(String type);

	List<Picture> getAllPicture(String type);

	List<Picture> getPictureByPage(Picture picture);
}