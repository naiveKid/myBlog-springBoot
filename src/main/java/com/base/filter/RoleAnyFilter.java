package com.base.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author 11075
 * @Description:
 * 自定义filter,用于shiro角色控制时,仅满足其中一个角色即可的情形
 */
public class RoleAnyFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] roles = (String[]) o;

        if (roles == null || roles.length == 0) {
            return true;
        }
        for (String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }

        return false;
    }
}