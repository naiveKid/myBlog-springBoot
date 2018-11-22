package com.core.service;

import com.base.model.Picture;
import com.base.util.RequestContextHolderUtil;
import com.core.dao.PictureMapper;
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
public class PictureService {
	@Autowired
	PictureMapper pictureDao;

	/**
	 * 得到分页图片记录
	 * 
	 * @return
	 */
	public List<Picture> getPictureByPage(String pictureType) {
		Picture picture=new Picture();
		picture.setPictureType(pictureType);
		return pictureDao.getPictureByPage(picture);
	}

	/**
	 * 得到所有的记录
	 *
	 * @return
	 */
	public List<Picture> getAllPicture(String type) {
		return pictureDao.getAllPicture(type);
	}
	
	/**
	 * 得到对应的一条图片记录
	 * 
	 * @param pictureId
	 * @return
	 */
	@Cacheable(value = "picture",key="'picture_'+#pictureId")
	public Picture getPictureById(int pictureId) {
		return (Picture) pictureDao.selectByPrimaryKey(pictureId);
	}

	/**
	 * 得到总记录条数
	 * 
	 * @return
	 */
	public int getCount(String type) {
		return pictureDao.getCount(type);
	}

	/**
	 * 执行更新操作
	 * 
	 * @param picture
	 */
	@CachePut(value = "picture",key="'picture_'+#picture.pictureId")
	public Picture updatePicture(Picture picture) {
		pictureDao.updateByPrimaryKeySelective(picture);
		return picture;
	}

	/**
	 * 执行新增操作
	 * 
	 * @param picture
	 */
	@CachePut(value = "picture",key="'picture_'+#picture.pictureId")
	public Picture addPicture(Picture picture) {
		pictureDao.insertSelective(picture);
		return picture;
	}

	/**
	 * 执行删除操作
	 * 
	 * @param id
	 */
	@CacheEvict(value = { "picture" },key="'picture_'+#id")
	public void delPicture(int id){
		//删除文件
		Picture picture= pictureDao.selectByPrimaryKey(id);
		String filePath =RequestContextHolderUtil.getSession().getServletContext().getRealPath("/")+picture.getPictureName();
		filePath=filePath.replace("/", "\\");
		filePath=filePath.replace("\\\\", "\\");
		File file=new File(filePath);
		if(file.exists()){
			file.delete();
		}
		pictureDao.deleteByPrimaryKey(id);
	}
}