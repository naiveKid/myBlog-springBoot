package com.core.service;

import com.base.model.Picture;
import com.base.util.DateTimeUtil;
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
 * @Cacheable 先查询redis是否存在相关结果，有则直接凑够redis中读取，否则从数据库获取数据并存放到redis中，以便下次直接从redis中读取。
 * 若不指定key的值，则会按照默认的规则生成。 key 代表存放的键, value 为存放的命名空间
 * @CachePut 先执行方法，最后再将该方法的查询结果放到redis中去，该方法始终会被spring调用. key 代表存放的键, value 为存放的命名空间
 * @CacheEvict 的意思就是执行该方法后要清除redis中key名称为a,b的数据. key 代表存放的键, value 为存放的命名空间
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
        Picture picture = new Picture();
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
    @Cacheable(value = "picture", key = "'picture_'+#pictureId")
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
    @CachePut(value = "picture", key = "'picture_'+#picture.pictureId")
    public Picture updatePicture(Picture picture) {
        pictureDao.updateByPrimaryKeySelective(picture);
        return picture;
    }

    /**
     * 执行新增操作
     *
     * @param picture
     */
    @CachePut(value = "picture", key = "'picture_'+#picture.pictureId")
    public Picture addPicture(Picture picture) {
        pictureDao.insertSelective(picture);
        return picture;
    }

    /**
     * 执行删除操作
     *
     * @param id
     */
    @CacheEvict(value = {"picture"}, key = "'picture_'+#id")
    public void delPicture(int id) {
        pictureDao.deleteByPrimaryKey(id);
    }

    /**
     * 创建文件的目录结构
     *
     * @param filePath
     * @return
     */
    public String createFilePath(String filePath) {
        String filePathPrex = filePath;

        File dir = new File(filePathPrex);
        if (!dir.exists()) {// 目录不存在则创建
            dir.mkdirs();
        }
        //每年一个文件夹
        filePathPrex = filePathPrex + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy);
        dir = new File(filePathPrex);
        if (!dir.exists()) {// 目录不存在则创建
            dir.mkdir();
        }
        //每月一个文件夹
        filePathPrex = filePathPrex + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM);
        dir = new File(filePathPrex);
        if (!dir.exists()) {// 目录不存在则创建
            dir.mkdir();
        }
        //每日一个文件夹
        filePathPrex = filePathPrex + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd);
        dir = new File(filePathPrex);
        if (!dir.exists()) {// 目录不存在则创建
            dir.mkdir();
        }
        return filePathPrex;
    }
}