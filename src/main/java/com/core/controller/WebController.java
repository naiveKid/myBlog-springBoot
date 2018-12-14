package com.core.controller;

import com.base.model.AboutMe;
import com.base.model.Essay;
import com.base.model.PickRedPacket;
import com.base.model.Picture;
import com.base.pojo.LucenePage;
import com.base.pojo.Page;
import com.base.util.BeanUtilEx;
import com.base.util.CommonUtil;
import com.base.util.JsonUtil;
import com.base.util.MobileCodeUtil;
import com.base.util.QRCodeUtil;
import com.base.util.RequestContextHolderUtil;
import com.core.service.AboutMeService;
import com.core.service.EssayService;
import com.core.service.PictureService;
import com.core.service.RedPacketService;
import com.core.service.WebService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/web")
public class WebController {
    @Autowired
    WebService webService;
    @Autowired
    AboutMeService aboutMeService;
    @Autowired
    EssayService essayService;
    @Autowired
    PictureService pictureService;
    @Autowired
    RedPacketService redPacketService;

    /**
     * 前台首页
     *
     * @return
     */
    @RequestMapping("/myBlog/index")
    public ModelAndView gotoPage() {
        ModelAndView mav = new ModelAndView("index");
        List<Picture> pictureList = webService.indexPicture();
        //点击排序
        List<Essay> clickNumList = webService.indexEssay("clickNum");
        //时间排序
        List<Essay> doTimeList = webService.indexEssay("doTime");
        //展示层次排序
        List<Essay> showLevelList = webService.indexEssay("showLevel");

        mav.addObject("pictureList", pictureList);
        mav.addObject("clickNumList", clickNumList);
        mav.addObject("doTimeList", doTimeList);
        mav.addObject("showLevelList", showLevelList);
        mav.addObject("role", webService.role());
        return mav;
    }

    /**
     * 后台首页
     *
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping("/manage")
    public ModelAndView manage() {
        ModelAndView mav = new ModelAndView("admin/index");
        AboutMe aboutMe = aboutMeService.getAboutMe();
        if (aboutMe != null) {
            aboutMe.setPictureName(webService.getImgPrefix() + "/" + aboutMe.getPictureName().replace("\\", "/"));
            Essay essay = essayService.getEssayById(aboutMe.getEssayId());
            if (essay != null) {
                mav.addObject("essay", essay);
            }
            mav.addObject("aboutMe", aboutMe);
        }
        return mav;
    }

    /**
     * 上传文件
     *
     * @return
     */
    @RequestMapping("/Upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> files = Murequest.getFileMap();// 得到文件map对象
        String filePathPrex = pictureService.createFilePath(webService.getFilePath());

        for (MultipartFile file : files.values()) {
            String absolutePath = filePathPrex + File.separator + file.getOriginalFilename();
            File tagetFile = new File(absolutePath);// 创建文件对象

            while (true) {
                if (tagetFile.exists()) {
                    String one = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + "1";
                    String two = absolutePath.substring(absolutePath.lastIndexOf("."));
                    absolutePath = one + two;
                    tagetFile = new File(absolutePath);
                } else {
                    break;
                }
            }

            // 文件名不存在,新建文件并将文件复制到新建文件中
            try {
                tagetFile.createNewFile();
                file.transferTo(tagetFile);

                Picture picture = new Picture();
                picture.setPictureName(StringUtils.substringAfterLast(tagetFile.getAbsolutePath().replace("\\", "/"), webService.getFilePath()));
                picture = pictureService.addPicture(picture);

                Map<String, String> map = new HashMap<String, String>();
                map.put("pictureId", picture.getPictureId().toString());
                map.put("pictureName", picture.getPictureName());
                response.getWriter().print(JsonUtil.toJson(map));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 下载文件
     *
     * @param response
     * @param fileName
     * @param filePathSuffix
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/downLoad")
    public ModelAndView downLoad(HttpServletResponse response, String fileName, String filePathSuffix) throws UnsupportedEncodingException {
        // 清空response
        response.reset();
        // 设置response的Header
        response.setHeader("connection", "close");
        response.setContentType("application/octet-stream");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        try {
            // 以流的形式下载文件
            InputStream fis = new BufferedInputStream(new FileInputStream(webService.getFilePath() + filePathSuffix));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            /* 点击下载之后，又取消下载 异常不处理 */
            if (!e.getClass().getSimpleName().equals("ClientAbortException")) {
                System.out.println("附件停止下载...");
                // e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 幻灯片管理
     *
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping("/other")
    public ModelAndView other() {
        ModelAndView mav = new ModelAndView("admin/other");
        List<Picture> pictureList = webService.indexPicture();
        Page.setTotalCount(pictureService.getCount("index"));
        if (pictureList != null) {
            mav.addObject("list", pictureList);
        }
        return mav;
    }

    /**
     * 幻灯片记录增加页面
     *
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping("/other/addPage")
    public ModelAndView addOtherPage() {
        ModelAndView mav = new ModelAndView("admin/addOther");
        return mav;
    }

    /**
     * 执行幻灯片添加操作
     *
     * @param picture
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping("/other/add")
    public ModelAndView addOther(Picture picture) {
        ModelAndView mav = new ModelAndView("blank");
        Picture pictureNew = pictureService.getPictureById(picture.getPictureId());
        pictureNew.setPictureType("index");
        pictureService.updatePicture(pictureNew);
        mav.addObject("msg", "添加成功!");
        mav.addObject("gotoPage", "web/other");
        return mav;
    }

    /**
     * 幻灯片记录删除
     *
     * @param id
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/other/del/{id}", method = RequestMethod.GET)
    public ModelAndView delOther(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("blank");
        pictureService.delPicture(id);
        mav.addObject("msg", "删除成功!");
        mav.addObject("gotoPage", "web/other");
        return mav;
    }

    /**
     * 跳转至二维码信息输入页面
     *
     * @return
     */
    @RequestMapping("/QRCodePage")
    public ModelAndView QRCodePage() {
        ModelAndView mav = new ModelAndView("/QRCode");
        return mav;
    }

    /**
     * 返回二维码生成图片名
     *
     * @param request
     * @param info
     * @return
     */
    @ResponseBody
    @RequestMapping("/QRCode")
    public String qrCode(HttpServletRequest request, String info) {
        Random rand = new Random();
        String filePathPrex = pictureService.createFilePath(webService.getFilePath());//创建文件目录
        String fileName = rand.nextLong() + "";
        if (QRCodeUtil.encode(filePathPrex, fileName, info)) {
            return webService.getImgPrefix() + "/" + StringUtils.substringAfterLast(filePathPrex, webService.getFilePath()).replace("\\", "/") + "/" + fileName + ".png";
        } else {
            return "fail";
        }
    }

    /**
     * 通过关键字查找对应的文章内容
     *
     * @param keyWord
     * @param pageIndex
     * @return
     */
    @RequestMapping("/search")
    public ModelAndView search(String keyWord, Integer pageIndex) {
        ModelAndView mav = new ModelAndView("/search");
        try {
            if (CommonUtil.isNull(keyWord)) {
                mav.setViewName("blank");
                mav.addObject("msg", "关键字不能为空!");
                mav.addObject("gotoPage", "web/myBlog/index");
                return mav;
            }
            if (CommonUtil.isNull(pageIndex) || pageIndex < 1) {// 为NULL和小于1则置为1
                pageIndex = 1;
            }
            LucenePage.pageSize = 10;
            LucenePage lucenePage = webService.listEssayBySearch(pageIndex, keyWord);
            if (!CommonUtil.isNull(lucenePage)) {
                List<Essay> list = lucenePage.getList();
                if (!CommonUtil.isNull(list)) {
                    List<Essay> essaylist = new ArrayList<Essay>();
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println("索引遍历:" + list.get(i).getEssayId());
                        Essay essay = essayService.getEssayById(list.get(i).getEssayId());
                        if (!CommonUtil.isNull(essay)) {
                            BeanUtilEx.copyIgnoreNulls(list.get(i), essay);
                        }
                        essaylist.add(essay);
                    }
                    mav.addObject("list", essaylist);
                }
            }
            RequestContextHolderUtil.getSession().setAttribute("totalCount", lucenePage.getTotalCount());
            RequestContextHolderUtil.getSession().setAttribute("pageSize", LucenePage.pageSize);
            mav.addObject("pageIndex", pageIndex);//当前页数
            mav.addObject("keyWord", keyWord);//关键词
        } catch (Exception e) {//查询出错
            RequestContextHolderUtil.getSession().setAttribute("totalCount", 0);
            RequestContextHolderUtil.getSession().setAttribute("pageSize", 10);
            mav.addObject("pageIndex", pageIndex);//当前页数
            mav.addObject("keyWord", keyWord);//关键词
        }
        return mav;
    }

    /**
     * 查看当前所有的红包活动
     *
     * @return
     */
    @RequestMapping(value = "/redPacket")
    public ModelAndView redPacketPage() {
        ModelAndView mav = new ModelAndView("/redPacket");
        List<PickRedPacket> list = redPacketService.getPickRedPacketRestNumberByPage();
        Page.setTotalCount(redPacketService.countRestNumber());
        mav.addObject("list", list);
        return mav;
    }

    /**
     * 抢红包
     *
     * @param packetId
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/doRedPacket")
    public String doRedPacket(Integer packetId) {
        String result;
        double flag = redPacketService.doRedPacket(packetId);
        if (flag == -1.0f) {
            result = "请勿重复领取.";
        } else if (flag == -2.0f) {
            result = "活动已结束.";
        } else if (flag == 0.0f) {
            result = "您的手速慢了一点,红包已派发完毕.";
        } else {
            result = "您抢到" + flag + "元";
        }
        return result;
    }

    /**
     * 查询号码归属地页面
     *
     * @return
     */
    @RequestMapping(value = "/mobileInfoPage")
    public ModelAndView mobileInfoPage() {
        ModelAndView mav = new ModelAndView("/mobileInfo");
        return mav;
    }

    /**
     * 返回号码归属地信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMobileInfo")
    public String getMobileInfo(String telPhoneNumber) {
        return MobileCodeUtil.getTelPhoneNumberInfo(telPhoneNumber);
    }

    /**
     * 笑话角色姓名填写
     *
     * @return
     */
    @RequestMapping(value = "/jokerPage")
    public ModelAndView jokerPage() {
        ModelAndView mav = new ModelAndView("/jokerPage");
        return mav;
    }

    /**
     * 笑话内容
     *
     * @return
     */
    @RequestMapping(value = "/joker")
    public ModelAndView joker(String boyName, String girlName) {
        ModelAndView mav = new ModelAndView("/joker");
        mav.addObject("boyName", boyName);
        mav.addObject("girlName", girlName);
        return mav;
    }

    /**
     * 在线对话设置
     *
     * @return
     */
    @RequestMapping(value = "/talkPage")
    public ModelAndView talkPage() {
        ModelAndView mav = new ModelAndView("/talkPage");
        return mav;
    }

    /**
     * 在线对话
     *
     * @return
     */
    @RequestMapping(value = "/talk")
    public ModelAndView talk(String boyName, String girlName, String role) {
        ModelAndView mav = new ModelAndView("/talk");
        mav.addObject("boyName", boyName);
        mav.addObject("girlName", girlName);
        mav.addObject("role", role);
        return mav;
    }
}