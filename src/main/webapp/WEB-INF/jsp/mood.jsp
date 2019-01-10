<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <link href="/static/css/mood.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/page.css" type="text/css">
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/sliders.js"></script>
    <script src="/static/js/modernizr.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="4"/>
</jsp:include>
<article class="moodlist">
    <h1 class="t_nav" style="text-align: right;">
        <span>删删写写，回回忆忆，虽无法行云流水，却也可碎言碎语。</span>
        <a href="/web/myBlog/index" class="n1">网站首页</a>
        <a href="#" class="n2"><spring:message code="mood"/></a>
    </h1>
    <div class="bloglist">
        <c:choose>
            <c:when test="${not empty list}">
                <c:forEach items="${list}" var="mood" varStatus="status">
                    <ul class="arrow_box">
                        <div class="sy">
                            <p>
                                <img src="${mood.pictureName}" alt="${mood.pictureName}" width="200" height="180" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">${mood.content}
                            </p>
                        </div>
                        <span class="dateview">${mood.doTime}</span>
                    </ul>
                </c:forEach>
            </c:when>
            <c:otherwise>
                暂无相关信息
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="/WEB-INF/jsp/include/page.jsp">
        <jsp:param name="link" value="/mood/index"/>
    </jsp:include>
</article>
</body>
</html>