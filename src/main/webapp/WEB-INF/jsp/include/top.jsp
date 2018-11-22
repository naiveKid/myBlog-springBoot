<%@page pageEncoding="UTF-8" %>
<%@page import="com.base.util.LocaleUtils" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<link href="/static/css/top.css" rel="stylesheet">
<header>
    <div class="f_l">
        <a href="#"><img src="/static/images/logo.png" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"></a>
        <%
            String locale = LocaleUtils.getLocale();
            if ("en".equals(locale)) {
        %>
        <a href="javascript:void(0);"><font color="#a52a2a">English</font></a>&nbsp;|&nbsp;<a href="?lang=zh_CN">中文</a>
        <%
        } else {
        %>
        <a href="?lang=en_US">English</a>&nbsp;|&nbsp;<a href="javascript:void(0);"><font color="#a52a2a">中文</font></a>
        <%
            }
        %>
    </div>
    <nav id="topnav" class="f_r">
        <ul>
            <a href="/web/myBlog/index" <c:if test="${param.type==1}">class="active"</c:if>><spring:message code="home"/></a>
            <a href="/about/index" <c:if test="${param.type==2}">class="active"</c:if>><spring:message code="aboutMe"/></a>
            <a href="/essay/listType?show=doTime" <c:if test="${param.type==3}">class="active"</c:if>><spring:message code="essay"/></a>
            <a href="/mood/index" <c:if test="${param.type==4}">class="active"</c:if>><spring:message code="mood"/></a>
            <a href="/picture/index" <c:if test="${param.type==5}">class="active"</c:if>><spring:message code="photo"/></a>
            <a href="/notice/index" <c:if test="${param.type==6}">class="active"</c:if>><spring:message code="notice"/></a>
        </ul>
    </nav>
</header>