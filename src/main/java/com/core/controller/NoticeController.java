package com.core.controller;

import com.base.model.Notice;
import com.base.model.UserInfo;
import com.base.pojo.Page;
import com.core.service.NoticeService;
import com.core.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	NoticeService noticeService;

	@Autowired
	UserService userService;

	/**
	 * 前台notice
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView noticePage() {
		ModelAndView mav = new ModelAndView("notice");
		List<Notice> list = noticeService.getNoticeByPage();
        Page.setTotalCount(noticeService.getCount());
		for (Notice n : list) {
			UserInfo u = userService.getUserName(n.getDoUserId());
			if (u != null) {
				n.setUserName(u.getUserName());
			}
		}
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 前台留言
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping("/addNotice")
	public ModelAndView addNotice(String content) {
		ModelAndView mav = new ModelAndView("blank");
		noticeService.addNotice(content);
		mav.addObject("msg", "留言成功!");
		mav.addObject("gotoPage", "notice/index");
		return mav;
	}

	/**
	 * 后台notice
	 * 
	 * @return
	 */
    //是否具有admin角色
    @RequiresRoles("admin")
	@RequestMapping("/manage")
	public ModelAndView manage() {
		ModelAndView mav = new ModelAndView("admin/notice");
		List<Notice> list = noticeService.getNoticeByPage();
        Page.setTotalCount(noticeService.getCount());
		for (Notice n : list) {
			UserInfo u = userService.getUserName(n.getDoUserId());
			if (u != null) {
				n.setUserName(u.getUserName());
			}
		}
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 执行删除操作
	 * 
	 * @param id
	 * @return
	 */
    //是否具有admin角色
    @RequiresRoles("admin")
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("blank");
		noticeService.delNotice(id);
		mav.addObject("msg", "删除成功!");
		mav.addObject("gotoPage", "notice/manage");
		return mav;
	}
}