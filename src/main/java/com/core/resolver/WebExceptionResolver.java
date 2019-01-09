package com.core.resolver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Description:
 * 重写默认异常处理类
 */
public class WebExceptionResolver extends SimpleMappingExceptionResolver {

    public WebExceptionResolver(){
        super.setExcludedExceptions(IOException.class,SQLException.class);
        super.setDefaultErrorView("error/error");//所有的异常定义默认的异常处理页面
        super.setExceptionAttribute("ex");//异常处理页面用来获取异常信息的变量名
        super.setDefaultStatusCode(500);//默认返回状态码
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        /*******************需要进行日志记录的异常**********************/
        Logger logger = LogManager.getLogger();
        logger.error(ex.getMessage(), ex);
        return super.doResolveException(request, response, handler, ex);
    }
}