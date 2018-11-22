package com.core.dao;

import com.base.model.Essay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EssayMapper {
	Essay selectByPrimaryKey(Integer essayId);
	
	int deleteByPrimaryKey(Integer essayId);

    int insertSelective(Essay record);

    int updateByPrimaryKeySelective(Essay record);
    
    int countInt();
    
    int getCount(String type);
    
    List<Essay> getEssayByPage(Essay essay);
    
    List<Essay> getEssayLimitRows(Essay essay);
}