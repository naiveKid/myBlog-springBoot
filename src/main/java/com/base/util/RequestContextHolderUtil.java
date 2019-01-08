package com.base.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 在代码任何地方都能获取当前Request
 */
public class RequestContextHolderUtil {

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().getServletContext();
        return request;
    }

    /**
     * 获取session
     *
     * @return
     */
    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }

    /**
     * 获取ServletContext
     *
     * @return
     */
    public static ServletContext getServletContext() {
        ServletContext servletContext = getSession().getServletContext();
        return servletContext;
    }

}
