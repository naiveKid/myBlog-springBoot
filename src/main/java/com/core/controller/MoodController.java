package com.core.controller;

import com.base.model.Mood;
import com.base.model.Picture;
import com.base.pojo.Page;
import com.base.util.DateTimeUtil;
import com.core.service.MoodService;
import com.core.service.PictureService;
import com.core.service.WebService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/mood")
public class MoodController {
    @Autowired
    MoodService moodService;
    @Autowired
    PictureService pictureService;
    @Autowired
    WebService webService;

    /**
     * 前台mood
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView moodPage() {
        ModelAndView mav = new ModelAndView("mood");
        List<Mood> list = moodService.getMoodByPage();
        Page.setTotalCount(moodService.getCount());
        for (Mood mood : list) {
            mood.setPictureName(webService.getImgPrefix() + "/" + mood.getPictureName().replace("\\", "/"));
        }
        mav.addObject("list", list);
        return mav;
    }

    /**
     * 后台mood
     *
     * @return
     */
    @RequestMapping("/manage")
    public ModelAndView manage() {
        ModelAndView mav = new ModelAndView("admin/mood");
        List<Mood> list = moodService.getMoodByPage();
        Page.setTotalCount(moodService.getCount());
        for (Mood mood : list) {
            mood.setPictureName(webService.getImgPrefix() + "/" + mood.getPictureName().replace("\\", "/"));
        }
        mav.addObject("list", list);
        return mav;
    }

    /**
     * 修改页面
     *
     * @param id
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping(value = "/alterPage/{id}", method = RequestMethod.GET)
    public ModelAndView alterPage(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("admin/alterMood");
        Mood mood = moodService.getMoodById(id);
        mood.setPictureName(webService.getImgPrefix() + "/" + mood.getPictureName().replace("\\", "/"));
        mav.addObject("mood", mood);
        return mav;
    }

    /**
     * 执行修改操作
     *
     * @param mood
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping(value = "/alter")
    public ModelAndView alterMood(Mood mood) {
        ModelAndView mav = new ModelAndView("blank");
        Picture pictureNew = pictureService.getPictureById(mood.getPictureId());
        pictureNew.setPictureType("mood");
        pictureService.updatePicture(pictureNew);

        Date dateTime = new Date();
        String date = DateTimeUtil.formatDate(dateTime, 6);
        mood.setDoTime(date);
        mood.setPictureId(pictureNew.getPictureId());
        moodService.updateMood(mood);
        mav.addObject("msg", "修改成功!");
        mav.addObject("gotoPage", "mood/manage");
        return mav;
    }

    /**
     * 添加页面
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/addPage")
    public ModelAndView addPage() {
        ModelAndView mav = new ModelAndView("admin/addMood");
        return mav;
    }

    /**
     * 执行添加操作
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/add")
    public ModelAndView addMood(Mood mood) {
        ModelAndView mav = new ModelAndView("blank");
        Picture pictureNew = pictureService.getPictureById(mood.getPictureId());
        pictureNew.setPictureType("mood");
        pictureService.updatePicture(pictureNew);

        Date dateTime = new Date();
        String date = DateTimeUtil.formatDate(dateTime, 6);
        mood.setDoTime(date);
        mood.setPictureId(pictureNew.getPictureId());
        moodService.addMood(mood);
        mav.addObject("msg", "添加成功!");
        mav.addObject("gotoPage", "mood/manage");
        return mav;
    }

    /**
     * 删除心情日志记录
     *
     * @param id
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping(value = "/deleteMood/{id}", method = RequestMethod.GET)
    public ModelAndView deleteMood(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("blank");
        moodService.delMood(id);
        mav.addObject("msg", "删除成功!");
        mav.addObject("gotoPage", "mood/manage");
        return mav;
    }
}
