<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>Mood</title>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="2"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <table width="80%" align="center">
                <tr>
                    <td height="40" align="center"><strong style="font-size: 20px;">发表心情列表</strong></td>
                </tr>
            </table>
            <p>
                <a style="margin-left: 1%; color: black;" href="/mood/addPage">更多操作
                    <font color="red">发表新心情</font>
                </a>
            </p>
            <table class="bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>配图</th>
                    <th>时间</th>
                    <th>内容</th>
                    <th>操作</th>
                </tr>
                </thead>
                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach items="${list}" var="mood" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>
                                    <img style="width: 50px; height:50px;" src="${mood.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"></td>
                                <td>${mood.doTime}</td>
                                <td>
                                    <c:if test="${fn:length(mood.text)>30}">
                                        ${fn:substring(mood.text,0,30)}...
                                    </c:if>
                                    <c:if test="${fn:length(mood.text)<=30}">
                                        ${mood.text}
                                    </c:if>
                                </td>
                                <td>
                                    <a href="/mood/alterPage/${mood.moodId}">修改</a>
                                    <a href="#" onclick="del('/mood/deleteMood/${mood.moodId}');">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5">还没有发布心情!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </section>
        <jsp:include page="/WEB-INF/jsp/include/page.jsp">
            <jsp:param name="link" value="/mood/manage"/>
        </jsp:include>
    </aside>
</div>
</body>
</html>