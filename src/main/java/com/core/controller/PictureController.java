package com.core.controller;

import com.base.model.Picture;
import com.base.pojo.Page;
import com.core.service.PictureService;
import com.core.service.WebService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    PictureService pictureService;
    @Autowired
    WebService webService;

    /**
     * 前台picture
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView picturePage() {
        ModelAndView mav = new ModelAndView("photo");
        List<Picture> list = pictureService.getAllPicture("photo");
        for (Picture picture : list) {
            picture.setPictureName(webService.getImgPrefix() + "/" + picture.getPictureName().replace("\\", "/"));
        }
        mav.addObject("list", list);
        return mav;
    }


    /**
     * 后台picture
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/manage")
    public ModelAndView manage() {
        ModelAndView mav = new ModelAndView("admin/photo");
        List<Picture> list = pictureService.getPictureByPage("photo");
        Page.setTotalCount(pictureService.getCount("photo"));
        for (Picture picture : list) {
            picture.setPictureName(webService.getImgPrefix() + "/" + picture.getPictureName().replace("\\", "/"));
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
        ModelAndView mav = new ModelAndView("admin/alterPhoto");
        Picture picture = pictureService.getPictureById(id);
        picture.setPictureName(webService.getImgPrefix() + "/" + picture.getPictureName().replace("\\", "/"));
        mav.addObject("picture", picture);
        return mav;
    }

    /**
     * 执行修改操作
     *
     * @param picture
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/alter")
    public ModelAndView alterPicture(Picture picture) {
        ModelAndView mav = new ModelAndView("blank");
        pictureService.updatePicture(picture);
        mav.addObject("msg", "修改成功!");
        mav.addObject("gotoPage", "picture/manage");
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
        ModelAndView mav = new ModelAndView("admin/addPhoto");
        return mav;
    }

    /**
     * 执行添加操作
     *
     * @param picture
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/add")
    public ModelAndView addPicture(Picture picture) {
        ModelAndView mav = new ModelAndView("blank");
        Picture pictureNew = pictureService.getPictureById(picture.getPictureId());
        picture.setPictureId(pictureNew.getPictureId());
        picture.setPictureName(pictureNew.getPictureName());
        pictureService.updatePicture(picture);
        mav.addObject("msg", "添加成功!");
        mav.addObject("gotoPage", "picture/manage");
        return mav;
    }

    /**
     * 删除相册
     *
     * @param id
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping(value = "/deletePicture/{id}", method = RequestMethod.GET)
    public ModelAndView deletePicture(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("blank");
        pictureService.delPicture(id);
        mav.addObject("msg", "删除成功!");
        mav.addObject("gotoPage", "picture/manage");
        return mav;
    }
}
