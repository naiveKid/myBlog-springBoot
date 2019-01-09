<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <link href="/static/css/aboutMe.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="2"/>
</jsp:include>
<article class="aboutcon">
    <h1 class="t_nav">
        <span>像“草根”一样，紧贴着地面，低调的存在，冬去春来，枯荣无恙。</span>
        <a href="/web/myBlog/index" class="n1">网站首页</a>
        <a href="#" class="n2"><spring:message code="aboutMe"/></a>
    </h1>
    <div class="about left">
        <h2>Just about me</h2>
        <ul>
            <c:if test="${not empty essay}">
                <p>${essay.title}</p>
                <div>${essay.content}</div>
                <c:if test="${not empty aboutMe}">
                    <p>
                        <img src="${aboutMe.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">
                    </p>
                </c:if>
            </c:if>
        </ul>
    </div>
    <aside class="right">
        <div class="about_c">
            <c:if test="${not empty aboutMe}">
                <p>姓名：${aboutMe.realName}</p>
                <p>籍贯：${aboutMe.address}</p>
                <p>现居：${aboutMe.nowAddress}</p>
                <p>喜欢的书：${aboutMe.likeBook}</p>
                <p>喜欢的音乐：${aboutMe.likeMusic}</p>
            </c:if>
        </div>
    </aside>
</article>
<jsp:include page="/WEB-INF/jsp/include/bottom_second.jsp"/>
</body>
</html>