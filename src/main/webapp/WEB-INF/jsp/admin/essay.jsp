<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>Essay</title>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="3"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <table width="80%" align="center">
                <tr>
                    <td height="40" align="center"><strong style="font-size: 20px;">发表文章列表</strong></td>
                </tr>
            </table>
            <blockquote class="layui-elem-quote layui-quote-nm">
                <p style="font-size: 14px;">
                    更多操作
                    <a style="margin-left: 1%; color: black;" href="/essay/addPage">
                        <font color="red">发表新文章</font>
                    </a>
                    <a style="margin-left: 1%; color: black;" href="/essay/publishMessagePage">
                        <font color="red">发布订阅消息</font>
                    </a>
                </p>
            </blockquote>
            <table class="layui-table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>文章标题</th>
                    <th>发布时间</th>
                    <th>文章内容</th>
                    <th>文章配图</th>
                    <th>操作</th>
                </tr>
                </thead>
                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach items="${list}" var="essay" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>
                                    <c:if test="${fn:length(essay.title)>10}">
                                        ${fn:substring(essay.title,0,10)}...
                                    </c:if>
                                    <c:if test="${fn:length(essay.title)<=10}">
                                        ${essay.title}
                                    </c:if>
                                </td>
                                <td>${essay.doTime}</td>
                                <td>
                                    <c:if test="${fn:length(essay.content)>30}">
                                        ${fn:substring(essay.content,0,30)}...
                                    </c:if>
                                    <c:if test="${fn:length(essay.content)<=30}">
                                        ${essay.content}
                                    </c:if>
                                </td>
                                <td><img style="width: 50px; height:50px;" src="${essay.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"></td>
                                <td>
                                    <a href="/essay/alterPage/${essay.essayId}">修改</a>
                                    <a href="#" onclick="del('/essay/deleteEssay/${essay.essayId}');">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6">还没有发布文章!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </section>
        <jsp:include page="/WEB-INF/jsp/include/page.jsp">
            <jsp:param name="link" value="/essay/manage"/>
        </jsp:include>
    </aside>
</div>
<jsp:include page="./include/bottom.jsp"></jsp:include>
</body>
</html>