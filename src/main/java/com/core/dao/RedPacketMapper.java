package com.core.dao;

import com.base.model.RedPacket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RedPacketMapper {
    int insert(RedPacket record);

    List<RedPacket> selectAll();
}