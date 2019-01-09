package com.core.controller;

import com.base.model.UserInfo;
import com.base.util.CommonUtil;
import com.base.util.RequestContextHolderUtil;
import com.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@Controller
@RequestMapping("/user")
@Api("用户控制层")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 验证用户名是否可用
     *
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    @ApiOperation(value = "验证用户名是否可用", httpMethod = "POST", notes = "验证用户名是否可用", response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 500, message = "服务器内部异常")
    })
    public boolean checkUser(String userName) {
        try {
            userName = URLDecoder.decode(userName, "UTF-8");
            boolean flag = userService.checkUserName(userName);
            return flag;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 登录
     *
     * @param userinfo
     * @param checkcode
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public ModelAndView login(UserInfo userinfo, String checkcode) {
        ModelAndView mav = new ModelAndView("blank");
        boolean flag = false;
        if (!userService.checkYZM(checkcode)) {
            mav.addObject("alert", "验证码输入有误!");
        } else {
            // 获得主题对象
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userinfo.getUserName(), userinfo.getPassword());
            try {
                if (userinfo == null || userinfo.getRememberMe() == null) {
                    token.setRememberMe(false);//是否设置为自动登陆
                } else {
                    token.setRememberMe(true);
                }
                subject.login(token);
                flag = true;
            } catch (AuthenticationException e) {
                mav.addObject("alert", "账户不存在或者密码输入有误!");
            }
        }
        if (flag) {
            RequestContextHolderUtil.getSession().setAttribute("userName", userinfo.getUserName());
            int role = userService.role(userinfo.getUserName());
            if (role == 1) {
                mav.addObject("gotoPage", "web/manage");
            } else if (role == 0) {
                mav.addObject("gotoPage", "web/myBlog/index");
            }
        } else {
            mav.addObject("gotoPage", "login.jsp");
        }
        return mav;
    }

    /**
     * 注册
     *
     * @param userinfo
     * @param checkcode
     * @return
     */
    @RequestMapping("/register")
    public ModelAndView register(UserInfo userinfo, String checkcode) {
        ModelAndView mav = new ModelAndView("blank");
        boolean flag = false;
        if (!userService.checkYZM(checkcode)) {
            mav.addObject("alert", "验证码输入有误!");
        } else {
            UserInfo user = userService.getUser(userinfo.getUserName());
            if (user != null) {
                mav.addObject("alert", "账户已存在，请更换用户名!");
            } else {
                userService.addUser(userinfo);
                mav.addObject("alert", "注册成功！!");
                flag = true;
            }
        }
        if (flag) {
            mav.addObject("gotoPage", "login.jsp");
        } else {
            mav.addObject("gotoPage", "register.jsp");
        }
        return mav;
    }

    /**
     * 注销
     *
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ModelAndView mav = new ModelAndView("blank");
        mav.addObject("msg", "注销成功!");
        mav.addObject("gotoPage", "login.jsp");
        return mav;
    }

    /**
     * websocket断开连接(ajax)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/exitAjax")
    public String exitAjax() {
        //同一账号多处登录，服务器主动断开当前session的用户连接
        //注销登陆
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "{\"info\":\"alert\"}";
    }
}