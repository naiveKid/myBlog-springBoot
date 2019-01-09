<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <link href="/static/css/notice.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/page.css" type="text/css">
    <link rel="stylesheet" href="/static/css/buttons.css" type="text/css">
    <link rel="stylesheet" href="/static/js/layui/css/layui.css">
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <!-- layer -->
    <script src="/static/js/layer/layer.js" type="text/javascript"></script>
    <script type="text/javascript" src="/static/js/sliders.js"></script>
    <script src="/static/js/modernizr.js"></script>
    <script src="/static/js/notice.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="6"/>
</jsp:include>
<article class="moodlist">
    <h1 class="t_nav" style="text-align: right;">
        <span>欢迎留言</span>
        <a href="/web/myBlog/index" class="n1">网站首页</a>
        <a href="#" class="n2"><spring:message code="notice"/></a>
    </h1>
    <br/>
    <div style="font-size: 18px;">留言列表</div>
    <div class="bloglist">
        <c:choose>
            <c:when test="${not empty list}">
                <c:forEach items="${list}" var="notice" varStatus="status">
                    <div style="width: 100%; padding-top: 5px; padding-bottom: 5px;">
                        <table border="0" cellpadding="0" cellspacing="0" width="50%" style="margin: 0 auto;">
                            <tr>
                                <td align="left" style="font-size: 16px; color: green">${notice.content}</td>
                            </tr>
                            <tr>
                                <td align="right">&nbsp;${notice.userName}留言</td>
                            </tr>
                        </table>
                        <p></p>
                        <div style="padding-right: 10%; float: right;"></div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                暂无相关信息
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="/WEB-INF/jsp/include/page.jsp">
        <jsp:param name="link" value="/notice/index"/>
    </jsp:include>
    <div style="padding-top: 50px;"></div>
    <form name="noticeForm" id="noticeForm" method="post" action="/notice/addNotice" class="layui-form">
        <div align="center" style="font-size: 14px;width: 50%;margin-left: 25%" class="layui-form-item layui-form-text">
            <textarea id="content" name="content" class="layui-textarea"></textarea>
        </div>
        <div align="center" style="margin-top: 15px;">
            <a class="button button-glow button-border button-rounded button-primary" href="#" onclick="checkContent();">我要留言</a>
        </div>
    </form>
</article>
</body>
</html>