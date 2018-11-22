package com.core.dao;

import com.base.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
	Notice selectByPrimaryKey(Integer noticeId);

	int deleteByPrimaryKey(Integer noticeId);

	int insertSelective(Notice record);

	int updateByPrimaryKeySelective(Notice record);

	int countInt();

	List<Notice> getNoticeByPage();
}