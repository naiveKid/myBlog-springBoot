package com.core.interceptor;

import com.base.model.OperationLog;
import com.base.model.UserInfo;
import com.base.util.CommonUtil;
import com.base.util.ExceptionUtil;
import com.core.service.SystemLogService;
import com.core.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * mongodb日志拦截器
 *
 * @ClassName: LogInterceptor
 * @Description: 日志拦截器
 */
@Component
@ConfigurationProperties(prefix="mongdb")
public class LogInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    UserService userService;

    @Autowired
    private SystemLogService systemLogService;

    /**
     * 线程池
     */
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 记录请求开始时间
     */
    private static final ThreadLocal<Long> StartTimeThreadLocal =new ThreadLocal<Long>();

    // 需要拦截的URI资源
    private List<String> excludedUris;

    public void setExcludedUris(List<String> excludedUris) {
        this.excludedUris = excludedUris;
    }

    //执行controller方法之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        StartTimeThreadLocal.set(0L);//初始化
        for (final String uri : excludedUris) { // 需要拦截的URI资源
            if (requestURI.indexOf(uri) != -1) {
                long beginTime = System.currentTimeMillis();
                StartTimeThreadLocal.set(beginTime);// 存放开始时间
            }
        }
        return true;
    }

    //渲染页面之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long beginTime = StartTimeThreadLocal.get();
        if (beginTime!=0L) {//不为0L,才需要保存日志
            // 记录系统日志
            final OperationLog log = new OperationLog();
            // 获取当前登录用户
            String userName = (String) request.getSession().getAttribute("userName");
            UserInfo user = userService.getUser(userName);
            if (user != null) {
                log.setUserName(user.getUserName());
                log.setUserId(String.valueOf(user.getUserId()));
            }
            log.setOperationTime(new Date());
            log.setRequestUri(request.getRequestURI());
            log.setMethod(request.getMethod());
            log.setParams(getParamString(request.getParameterMap()));
            log.setRemoteAddr(CommonUtil.getClintIP(request));
            log.setUserAgent(request.getHeader("user-agent"));
            // 耗时
            long endTime = System.currentTimeMillis();
            log.setTimeConsuming(endTime - beginTime);
            if (ex != null) {
                log.setException(ExceptionUtil.getStackTraceAsString(ex));
            }
            // 开启线程保存日志
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    systemLogService.insertOperationLog(log);
                }
            });
        }
    }

    /**
     * 获取参数
     *
     * @param map
     * @return String
     */
    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String[]> e : map.entrySet()) {
            if (StringUtils.isNotBlank(e.getKey()) && StringUtils.contains(e.getKey().toLowerCase(), "password")) {
                continue;
            }
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(HtmlUtils.htmlEscape(value[0])).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }
}