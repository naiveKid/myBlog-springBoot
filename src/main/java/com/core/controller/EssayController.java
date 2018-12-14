package com.core.controller;

import com.base.model.Essay;
import com.base.model.Picture;
import com.base.pojo.Page;
import com.base.util.CommonUtil;
import com.base.util.DateTimeUtil;
import com.core.redis.RedisTemplateUtils;
import com.core.service.EssayService;
import com.core.service.PictureService;
import com.core.service.WebService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("/essay")
public class EssayController {
    @Autowired
    EssayService essayService;
    @Autowired
    PictureService pictureService;
    @Autowired
    RedisTemplateUtils redisTemplateUtils;
    @Autowired
    WebService webService;

    /**
     * 前台essay
     *
     * @param show
     * @return
     */
    @RequestMapping("/listType")
    public ModelAndView essayPage(String show) {
        ModelAndView mav = new ModelAndView("essay");
        if (CommonUtil.isNull(show)) {
            show = "doTime";//默认按照时间排序
        }
        List<Essay> list = essayService.getEssayByPage("essay", show);
        Page.setTotalCount(essayService.getCount("essay"));
        for (Essay essay : list) {
            essay.setPictureName(webService.getImgPrefix() + "/" + essay.getPictureName().replace("\\", "/"));
        }
        mav.addObject("list", list);
        mav.addObject("type", show);
        return mav;
    }

    /**
     * 前台文章详细
     *
     * @param essayId
     * @return
     */
    @RequestMapping(value = "/detail/{essayId}", method = RequestMethod.GET)
    public ModelAndView detailPage(@PathVariable int essayId) {
        ModelAndView mav = new ModelAndView("detail");
        Essay essay = essayService.getEssayById(essayId);
        Integer clickNum = essayService.updateEssayClickNum(essay);
        if (essay != null) {
            essay.setClickNum(clickNum);
            int pictureId = essay.getPictureId();
            Picture picture = pictureService.getPictureById(pictureId);
            picture.setPictureName(webService.getImgPrefix() + "/" + picture.getPictureName().replace("\\", "/"));
            mav.addObject("picture", picture);
        }
        mav.addObject("essay", essay);
        return mav;
    }

    /**
     * 后台essay
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequiresPermissions({"essay:update", "essay:publish"})
    @RequestMapping("/manage")
    public ModelAndView manage() {
        ModelAndView mav = new ModelAndView("admin/essay");
        List<Essay> list = essayService.getEssayByPage("essay", "doTime");
        Page.setTotalCount(essayService.getCount("essay"));
        for (Essay essay : list) {
            essay.setPictureName(webService.getImgPrefix() + "/" + essay.getPictureName().replace("\\", "/"));
        }
        mav.addObject("list", list);
        return mav;
    }

    /**
     * 后台修改文章信息页面
     *
     * @param id
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequiresPermissions({"essay:update"})
    @RequestMapping(value = "/alterPage/{id}", method = RequestMethod.GET)
    public ModelAndView alterPage(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("admin/alterEssay");
        Essay essay = essayService.getEssayById(id);
        essay.setPictureName(webService.getImgPrefix() + "/" + essay.getPictureName().replace("\\", "/"));
        mav.addObject("essay", essay);
        return mav;
    }

    /**
     * 后台修改文章信息
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequiresPermissions({"essay:update"})
    @RequestMapping(value = "/alter")
    public ModelAndView alterEssay(Essay essay) {
        ModelAndView mav = new ModelAndView("blank");
        Picture pictureNew = pictureService.getPictureById(essay.getPictureId());
        pictureNew.setPictureType("other");
        pictureService.updatePicture(pictureNew);

        Date dateTime = new Date();
        String date = DateTimeUtil.formatDate(dateTime, 6);
        essay.setDoTime(date);
        essay.setPictureId(pictureNew.getPictureId());
        essayService.updateEssay(essay);
        mav.addObject("msg", "修改成功!");
        mav.addObject("gotoPage", "essay/manage");
        return mav;
    }

    /**
     * 后台添加文章信息页面
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/addPage")
    public ModelAndView addPage() {
        ModelAndView mav = new ModelAndView("admin/addEssay");
        return mav;
    }

    /**
     * 后台添加文章信息
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/add")
    public ModelAndView addEssay(Essay essay) {
        ModelAndView mav = new ModelAndView("blank");
        Picture pictureNew = pictureService.getPictureById(essay.getPictureId());
        pictureNew.setPictureType("other");
        pictureService.updatePicture(pictureNew);

        Date dateTime = new Date();
        String date = DateTimeUtil.formatDate(dateTime, 6);
        essay.setDoTime(date);
        essay.setPictureId(pictureNew.getPictureId());
        essayService.addEssay(essay);
        mav.addObject("msg", "添加成功!");
        mav.addObject("gotoPage", "essay/manage");
        return mav;
    }

    /**
     * 后台删除文章信息
     *
     * @param id
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping(value = "/deleteEssay/{id}")
    public ModelAndView deleteEssay(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("blank");
        essayService.delEssay(id);
        mav.addObject("msg", "删除成功!");
        mav.addObject("gotoPage", "essay/manage");
        return mav;
    }

    /**
     * 后台发布订阅信息页面
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    //是否具有文章发布权限
    @RequiresPermissions({"essay:publish"})
    @RequestMapping("/publishMessagePage")
    public ModelAndView publishMessagePage() {
        ModelAndView mav = new ModelAndView("admin/addEssayMessage");
        return mav;
    }

    /**
     * 后台发布订阅信息
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    //是否具有文章发布权限
    @RequiresPermissions({"essay:publish"})
    @RequestMapping(value = "/publishMessage")
    public ModelAndView publishMessage(String message) {
        redisTemplateUtils.sendMessage("essayTalk", message);
        ModelAndView mav = new ModelAndView("blank");
        mav.addObject("msg", "发布成功!");
        mav.addObject("gotoPage", "essay/manage");
        return mav;
    }
}