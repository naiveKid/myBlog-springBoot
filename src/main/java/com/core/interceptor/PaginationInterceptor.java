package com.core.interceptor;

import com.base.pojo.Page;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于在每次请求添加对应的分页参数
 * 
 * @author hxz
 * @date 2017年10月5日
 */
public class PaginationInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 设置本次请求的分页参数
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String offset = request.getParameter("offset");
		if (offset != null) {
			Page.setOffset(Integer.parseInt(offset));
		}else{
			Page.setOffset(0);
		}
		return true;
	}

	/**
	 * 在页面渲染完成之后,销毁本次请求的分页参数
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		Page.removeOffset();
		Page.removePageSize();
		Page.removeTotalCount();
		super.afterCompletion(request, response, handler, ex);
	}
}
