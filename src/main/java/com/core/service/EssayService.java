package com.core.service;

import com.base.model.Essay;
import com.base.util.CommonUtil;
import com.core.dao.EssayMapper;
import com.core.redis.RedisTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EssayService {
	@Autowired
	EssayMapper essayDao;

	@Autowired
	WebService webService;

	@Autowired
    RedisTemplateUtils redisTemplateUtils;

	/**
	 * 得到从start开始的rows条记录
	 * 
	 * @param essayType
	 * @param start(从0开始)
	 * @param rows
	 * @return
	 */
	public List<Essay> getEssayLimitRows(String essayType, Integer start, Integer rows) {
		Essay essay = new Essay();
		essay.setEssayType(essayType);
		essay.setStart(start);
		essay.setRows(rows);
		return essayDao.getEssayLimitRows(essay);
	}

    /**
     * 得到分页文章记录
     * @param essayType
     * @param showType
     * @return
     */
	public List<Essay> getEssayByPage(String essayType,String showType) {
		Essay essay = new Essay();
		essay.setEssayType(essayType);
		essay.setShowType(showType);
		return essayDao.getEssayByPage(essay);
	}

	/**
	 * 得到总记录条数
	 * 
	 * @return
	 */
	public int getCount(String type) {
		return essayDao.getCount(type);
	}

	/**
	 * 得到对应的一条文章记录
	 * 
	 * @param essayId
	 * @return
	 */
	public Essay getEssayById(int essayId) {
		return essayDao.selectByPrimaryKey(essayId);
	}

	/**
	 * 执行更新操作
	 * 
	 * @param essay
	 * @return
	 */
	public Essay updateEssay(Essay essay) {
		essayDao.updateByPrimaryKeySelective(essay);
		if("essay".equals(essay.getEssayType())){
			webService.createIndex(essay);
		}
		return essay;
	}

	/**
	 * 执行新增操作
	 * 
	 * @param essay
	 */
	public Essay addEssay(Essay essay) {
		essayDao.insertSelective(essay);
		if("essay".equals(essay.getEssayType())){
			webService.createIndex(essay);
		}
		return essay;
	}

	/**
	 * 执行删除操作
	 * 
	 * @param id
	 */
	public void delEssay(int id){
		Essay essay = essayDao.selectByPrimaryKey(id);
		// 删除索引
		if("essay".equals(essay.getEssayType())){
			webService.deleteIndex(essay);
		}
		// 删除文章记录
		essayDao.deleteByPrimaryKey(id);
	}
	
	/**
	 * 更新文章的阅读数
	 * @param essay
	 */
	public Integer updateEssayClickNum(Essay essay){
		Integer clickNum=0;
		
		if(redisTemplateUtils.hasKey("essayId_"+essay.getEssayId()+"_click")){//存在，则从redis中取
			clickNum=(Integer) redisTemplateUtils.get("essayId_"+essay.getEssayId()+"_click");
		}else{//不存在，则从数据库中取
			clickNum=essay.getClickNum();
			if(CommonUtil.isNull(clickNum)||clickNum<0){
				clickNum=0;
			}
		}
		++clickNum;
        redisTemplateUtils.set("essayId_"+essay.getEssayId()+"_click", clickNum,7200);
		return clickNum;
	}
}