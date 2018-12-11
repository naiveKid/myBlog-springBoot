<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>operatorLog</title>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="7"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <table width="80%" align="center">
                <tr>
                    <td height="40" align="center"><strong style="font-size: 20px;">系统日志</strong></td>
                </tr>
            </table>
            <table class="bordered">
                <thead>
                <tr>
                    <th width="50px">序号</th>
                    <th width="110px">操作用户</th>
                    <th width="120px">IP地址</th>
                    <th>URI</th>
                    <th width="90px">请求类型</th>
                    <th width="200px">操作时间</th>
                    <th width="100px">耗时</th>
                    <th width="110px">操作</th>
                </tr>
                </thead>
                <c:choose>
                    <c:when test="${not empty page}">
                        <c:forEach items="${page.list }" var="obj" varStatus="stat">
                            <tr>
                                <td id="sort${obj.id}">${stat.count}</td>
                                <td>${obj.userName}</td>
                                <td>${obj.remoteAddr}</td>
                                <td>${obj.requestUri}</td>
                                <td>${obj.method}</td>
                                <td>
                                    <fmt:formatDate value='${obj.operationTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
                                </td>
                                <td>${obj.timeConsuming}ms</td>
                                <td>
                                    <a href="/systemLog/showOperationLog/${obj.id}">查看</a>
                                    <a href="/systemLog/delOperationLog/${obj.id}" onclick="del();">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6">还没有发布操作日志!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
            <jsp:include page="/WEB-INF/jsp/include/page.jsp">
                <jsp:param name="link" value="/systemLog/listPagedOperationLog"/>
            </jsp:include>
        </section>
    </aside>
</div>
</body>
</html>