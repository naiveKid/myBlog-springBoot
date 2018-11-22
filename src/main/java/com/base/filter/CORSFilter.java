package com.base.filter;

import com.base.util.CommonUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截所有请求并在请求头部加上允许跨域代码
 * 
 * @WebFilter注解自动注册Filter
 *
 **/
@WebFilter(urlPatterns = "/*", description = "跨域请求")
public class CORSFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        String origin=httpServletRequest.getHeader("Origin");
        if(!CommonUtil.isNull(origin)){//动态获取跨域访问的来源
            httpResponse.addHeader("Access-Control-Allow-Origin", origin);//解决cookies跨域的全匹配问题
        }else{
            httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        }
        String requestHeader=httpServletRequest.getHeader("Access-Control-Request-Headers");
        if(!CommonUtil.isNull(origin)){//动态获取跨域访问的请求头
            httpResponse.addHeader("Access-Control-Allow-Headers",requestHeader);//允许请求头跨域
        }
        httpResponse.addHeader("Access-Control-Max-Age","3600");//允许预检缓存3600s
        httpResponse.addHeader("Access-Control-Allow-Credentials","true");//启用cookie跨域
        httpResponse.addHeader("Access-Control-Allow-Methods","*");//允许所有的方法:get,post,put...
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) {
	}
}
