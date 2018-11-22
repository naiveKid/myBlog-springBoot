package com.core.dao;

import com.base.model.AboutMe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AboutMeMapper {
	AboutMe getAboutMe();

	int deleteByPrimaryKey(Integer aboutmeId);

	int insertSelective(AboutMe record);

	int updateByPrimaryKeySelective(AboutMe record);

	int countInt();
}