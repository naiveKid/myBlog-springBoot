<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <link href="/static/css/detail.css" rel="stylesheet">
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/sliders.js"></script>
    <script src="/static/js/modernizr.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="3"/>
</jsp:include>
<article class="blogs">
    <div class="index_about">
        <c:if test="${not empty essay}">
            <h2 class="c_titile">${essay.title}</h2>
            <p class="box_c">
                <span class="d_time">发布时间：${essay.doTime}</span>
            </p>
            <ul class="infos">
                <p>${essay.content}</p>
                <c:if test="${not empty picture}">
                    <p>
                        <img src="${picture.pictureName}" alt="${picture.pictureName}" width="400" height="290" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">
                    </p>
                </c:if>
            </ul>
        </c:if>
    </div>
</article>
</body>
</html>