<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>Notice</title>
    <script>
    </script>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="5"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <table width="80%" align="center">
                <tr>
                    <td height="40" align="center"><strong style="font-size: 20px;">留言列表</strong></td>
                </tr>
            </table>
            <table class="bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>留言内容</th>
                    <th>留言人</th>
                    <th>操作</th>
                </tr>
                </thead>
                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach items="${list}" var="notice" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>
                                    <c:if test="${fn:length(notice.content)>30}">
                                        ${fn:substring(notice.content,0,30)}...
                                    </c:if>
                                    <c:if test="${fn:length(notice.content)<=30}">
                                        ${notice.content}
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${fn:length(notice.userName)>30}">
                                        ${fn:substring(notice.userName,0,30)}...
                                    </c:if>
                                    <c:if test="${fn:length(notice.userName)<=30}">
                                        ${notice.userName}
                                    </c:if>
                                </td>
                                <td><a href="/notice/delete/${notice.noticeId}" onclick="del();">删除</a></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="4">还没有留言哦!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
            <jsp:include page="/WEB-INF/jsp/include/page.jsp">
                <jsp:param name="link" value="/notice/manage"/>
            </jsp:include>
        </section>
    </aside>
</div>
</body>
</html>