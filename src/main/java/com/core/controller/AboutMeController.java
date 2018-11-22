package com.core.controller;

import com.base.model.AboutMe;
import com.base.model.Essay;
import com.base.model.Picture;
import com.core.service.AboutMeService;
import com.core.service.EssayService;
import com.core.service.PictureService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/about")
public class AboutMeController {
	@Autowired
	AboutMeService aboutMeService;
	@Autowired
	EssayService essayService;
	@Autowired
	PictureService pictureService;

	/**
	 * 前台aboutMe
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView aboutMePage()  {
		ModelAndView mav = new ModelAndView("aboutMe");
		AboutMe aboutMe = aboutMeService.getAboutMe();
		if (aboutMe != null) {
			Essay essay = essayService.getEssayById(aboutMe.getEssayId());
			if (essay != null) {
				mav.addObject("essay", essay);
			}
			Picture picture = pictureService.getPictureById(aboutMe.getPictureId());
			if (picture != null) {
				aboutMe.setPictureName(picture.getPictureName());
			}
			mav.addObject("aboutMe", aboutMe);
		}
		return mav;
	}

	/**
	 * 后台修改关于我
	 * 
	 * @param aboutMe
	 * @return
	 */
    //是否具有admin角色
    @RequiresRoles("admin")
	@RequestMapping(value="/alter")
	public ModelAndView alterAboutMe(AboutMe aboutMe, @RequestParam(name="title",required = false,defaultValue="") String title, String content) {
		ModelAndView mav = new ModelAndView("blank");// 请求转发
		Integer pictureId = aboutMe.getPictureId();
		Picture picture = pictureService.getPictureById(pictureId);
		picture.setPictureType("aboutMe");
		pictureService.updatePicture(picture);
		// 若为空，则增加一条记录；否则修改即可
		List<Essay> list = essayService.getEssayByPage("aboutMe","doTime");
		Essay essay = new Essay();
		if (list != null) {
			essay = list.get(0);
		}
		essay.setTitle(title);
		essay.setContent(content);
		essay.setEssayType("aboutMe");
		Essay essay1 = essayService.updateEssay(essay);
		aboutMe.setEssayId(essay1.getEssayId());
		aboutMe.setPictureId(picture.getPictureId());
		aboutMeService.updateAboutMe(aboutMe);
		mav.addObject("msg", "修改成功!");
		mav.addObject("gotoPage", "web/manage");
		return mav;
	}
}