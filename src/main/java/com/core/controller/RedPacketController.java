package com.core.controller;

import com.base.model.PickRedPacket;
import com.base.util.DateTimeUtil;
import com.core.service.RedPacketService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/redPacket")
public class RedPacketController {
    @Autowired
    RedPacketService redPacketService;

    @RequestMapping("/manage")
    public ModelAndView listRedPacket() {
        ModelAndView mav = new ModelAndView("admin/pickRedPacket");
        List<PickRedPacket> list=redPacketService.getPickRedPacketByPage();
        mav.addObject("list", list);
        return mav;
    }

    /**
     * 后台添加红包活动页面
     *
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView addPage() {
        ModelAndView mav = new ModelAndView("admin/addPickRedPacket");
        return mav;
    }

    /**
     * 后台添加红包
     *
     * @return
     */
    //是否具有admin角色
    @RequiresRoles("admin")
    @RequestMapping("/add")
    public ModelAndView addEssay(PickRedPacket pickRedPacket) {
        ModelAndView mav = new ModelAndView("blank");
        Date dateTime = new Date();
        String date = DateTimeUtil.formatDate(dateTime, 6);
        pickRedPacket.setDoTime(date);
        pickRedPacket.setRestNumber(pickRedPacket.getNumber());
        pickRedPacket.setVersion(0);
        redPacketService.addPickRedPacket(pickRedPacket);
        //预先生成小红包缓存
        redPacketService.createRedPacketCache(pickRedPacket);
        mav.addObject("msg", "添加成功!");
        mav.addObject("gotoPage", "redPacket/manage");
        return mav;
    }

}
