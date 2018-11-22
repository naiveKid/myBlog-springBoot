package com.core.dao;

import com.base.model.Mood;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoodMapper {
	Mood selectByPrimaryKey(Integer moodId);
	 
	int deleteByPrimaryKey(Integer moodId);

    int insertSelective(Mood record);

    int updateByPrimaryKeySelective(Mood record);

    int countInt();
    
    List<Mood> getMoodByPage();
}