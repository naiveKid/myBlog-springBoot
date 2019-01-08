package com.core.service;

import com.base.model.Mood;
import com.base.util.RequestContextHolderUtil;
import com.core.dao.MoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 缓存机制说明：把sql查询的结果放到了redis中去, 然后第二次发起该条查询时从redis中去读取查询的结果，从而达到优化的效果.
 * 
 * @Cacheable(value="a")先查询redis是否存在相关结果，有则直接凑够redis中读取，否则从数据库获取数据并存放到redis中，以便下次直接从redis中读取。
 * 若不指定key的值，则会按照默认的规则生成；此处采用了自定义key。
 * @CachePut(value="a")先执行方法，最后再将该方法的查询结果放到redis中去，该方法始终会被spring调用.
 * @CacheEvict(value={"a","b",allEntries=true)的意思就是执行该方法后要清除redis中key名称为a,b的数据.
 */
@Service
public class MoodService {
	@Autowired
	MoodMapper moodDao;

	/**
	 * 得到分页心情记录
	 * 
	 * @return
	 */
	public List<Mood> getMoodByPage() {
		return moodDao.getMoodByPage();
	}

	/**
	 * 得到总记录条数
	 * 
	 * @return
	 */
	public int getCount() {
		return moodDao.countInt();
	}

	/**
	 * 得到一条具体的记录
	 * 
	 * @param moodId
	 * @return
	 */
	@Cacheable(value = "mood",key="'mood_'+#moodId")
	public Mood getMoodById(int moodId) {
		return (Mood) moodDao.selectByPrimaryKey(moodId);
	}

	/**
	 * 执行更新操作
	 * 
	 * @param mood
	 */
	@CachePut(value = "mood",key="'mood_'+#mood.moodId")
	public Mood updateMood(Mood mood) {
		moodDao.updateByPrimaryKeySelective(mood);
		return mood;
	}

	/**
	 * 执行新增操作
	 * 
	 * @param mood
	 */
	@CachePut(value = "mood",key="'mood_'+#mood.moodId")
	public Mood addMood(Mood mood) {
		moodDao.insertSelective(mood);
		return mood;
	}
	
	/**
	 * 执行删除操作
	 * 
	 * @param id
	 */
	@CacheEvict(value = { "mood" },key="'mood_'+#id")
	public void delMood(int id) {
		moodDao.deleteByPrimaryKey(id);
	}
}
