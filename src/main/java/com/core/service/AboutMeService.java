package com.core.service;

import com.base.model.AboutMe;
import com.core.dao.AboutMeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutMeService {

	@Autowired
	AboutMeMapper aboutMeDao;

	/**
	 * 获取到一条记录
	 * 
	 * @return
	 */
	public AboutMe getAboutMe() {
		if (aboutMeDao.countInt() == 0) {// 若记录为空
			AboutMe aboutMe = new AboutMe();// 新建一条关于我记录
			aboutMe.setEssayId(1);
			aboutMe.setPictureId(0);
			aboutMeDao.insertSelective(aboutMe);
		}
		return aboutMeDao.getAboutMe();
	}

	/**
	 * 修改关于我
	 * 
	 * @param aboutMe
	 */
	public void updateAboutMe(AboutMe aboutMe) {
		if (aboutMe.getEssayId() != 1) {
			aboutMe.setEssayId(1);
		}
		aboutMeDao.updateByPrimaryKeySelective(aboutMe);
	}
}
