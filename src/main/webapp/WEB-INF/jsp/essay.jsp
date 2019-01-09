<%@page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <link href="/static/css/essay.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/page.css" type="text/css">
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/sliders.js"></script>
    <script src="/static/js/modernizr.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="3"/>
</jsp:include>
<article class="blogs">
    <h1 class="t_nav" style="text-align: right;">
        “慢生活”不是懒惰，放慢速度不是拖延时间，而是让我们在生活中寻找到平衡。
        <a href="/web/myBlog/index" class="n1">网站首页</a>
        <a href="#" class="n2"><spring:message code="essay"/></a>
    </h1>
    <div class="newblog left">
        <c:choose>
            <c:when test="${not empty list}">
                <c:forEach items="${list}" var="essay" varStatus="status">
                    <!-- 循环开始 -->
                    <h2>
                        <a title="${essay.title}" href="#">
                            <c:if test="${fn:length(essay.title)>10}">
                                ${fn:substring(essay.title,0,10)}...
                            </c:if>
                            <c:if test="${fn:length(essay.title)<=10}">
                                ${essay.title}
                            </c:if>
                        </a>
                    </h2>
                    <p class="dateview">发布时间：${essay.doTime}</p>
                    <figure>
                        <a title="${essay.title}" href="#">
                            <img src="${essay.pictureName}" width="100%" height="200px" alt="${essay.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">
                        </a>
                    </figure>
                    <ul class="nlist">
                        <p>
                            <c:if test="${fn:length(essay.content)>100}">
                                ${fn:substring(essay.content,0,100)}...
                            </c:if>
                            <c:if test="${fn:length(essay.content)<=100}">
                                ${essay.content}
                            </c:if>
                        </p>
                        <a href="/essay/detail/${essay.essayId}" title="${essay.title}" target="_top" class="readmore">阅读全文&gt;&gt;</a>
                    </ul>
                    <div class="line"></div>
                    <!-- 循环结束 -->
                </c:forEach>
            </c:when>
            <c:otherwise>
                <font style="font-size: 16px;">暂无相关信息</font>
            </c:otherwise>
        </c:choose>
        <jsp:include page="/WEB-INF/jsp/include/page.jsp">
            <jsp:param name="link" value="/essay/listType?show=${type}"/>
        </jsp:include>
    </div>
</article>
</body>
</html>