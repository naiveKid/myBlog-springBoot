package com.core.dao;

import com.base.model.PickRedPacket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PickRedPacketMapper {
    int insert(PickRedPacket record);

    List<PickRedPacket> selectAll();

    PickRedPacket getPickRedPacketById(Integer id);

    List<PickRedPacket> getPickRedPacketByPage();

    List<PickRedPacket> getPickRedPacketRestNumberByPage();

    Integer update(PickRedPacket pickRedPacket);
}