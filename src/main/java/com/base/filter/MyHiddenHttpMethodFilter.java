package com.base.filter;

import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @Description:将post转换为_method对应的请求类型.
 * SpringMVC 需要在web.xml中显式注册：
 * <filter>
 * <filter-name>HiddenHttpMethodFilter</filter-name>
 * <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
 * </filter>
 * <filter-mapping>
 * <filter-name>HiddenHttpMethodFilter</filter-name>
 * <url-pattern>/*</url-pattern>
 * </filter-mapping>
 * SpringBoot 默认注册了HiddenHttpMethodFilter.
 */
@WebFilter(urlPatterns = "/*", description = "请求类型转换")
public class MyHiddenHttpMethodFilter extends OncePerRequestFilter {
    private static final List<String> ALLOWED_METHODS;
    private String methodParam = "_method";

    static {
        ALLOWED_METHODS = Collections.unmodifiableList(Arrays.asList(HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest requestToUse = request;
        if (request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null) {//没有错误信息
            String paramValue = request.getParameter(this.methodParam);//从_method参数取出对应的值
            if (StringUtils.hasLength(paramValue)) {//值不为空
                String method = paramValue.toUpperCase(Locale.ENGLISH);//转为全大写
                if (ALLOWED_METHODS.contains(method)) {//判断值是否正确
                    requestToUse = new HttpMethodRequestWrapper(request, method);//修改为_method对应的请求类型
                }
            }
        }
        filterChain.doFilter(requestToUse, response);
    }

    private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {
        private final String method;

        public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }

        @Override
        public String getMethod() {
            return this.method;
        }
    }
}