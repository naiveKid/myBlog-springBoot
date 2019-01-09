package com.core.resolver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Description:
 * 自定义异常处理
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        /*******************不需要进行日志记录的异常**********************/
        if (e instanceof org.apache.shiro.authz.UnauthorizedException){//shiro 角色、权限验证错误
            ModelAndView mav = new ModelAndView("blank");
            mav.addObject("alert", "您没有足够的角色/权限进行操作!");
            mav.addObject("gotoPage", "login.jsp");
            return mav;
        }else if (e instanceof org.apache.shiro.authz.UnauthenticatedException){//shiro 登陆验证错误
            ModelAndView mav = new ModelAndView("blank");
            mav.addObject("alert", "您需要登陆才能继续操作!");
            mav.addObject("gotoPage", "login.jsp");
            return mav;
        }
        /*******************需要进行日志记录的异常**********************/
        Logger logger = LogManager.getLogger();
        logger.error(e.getMessage(), e);

        if(e instanceof IOException){//IO错误
            return new ModelAndView("error/ioException","ex",e);
        }else if(e instanceof SQLException){//SQL错误
            return new ModelAndView("error/sqlException","ex",e);
        }
        return null;
    }
}