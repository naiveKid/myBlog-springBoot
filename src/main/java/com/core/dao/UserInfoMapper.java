package com.core.dao;

import com.base.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
	UserInfo selectByPrimaryKey(Integer userId);

	int deleteByPrimaryKey(Integer userId);

	int insertSelective(UserInfo record);

	int updateByPrimaryKeySelective(UserInfo record);

	List<UserInfo> checkUserName(UserInfo userInfo);
}