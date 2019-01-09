<%@page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <link href="/static/css/index.css" rel="stylesheet">
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/sliders.js"></script>
    <script src="/static/js/modernizr.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="1"/>
</jsp:include>
<article>
    <div class="l_box f_l">
        <c:choose>
            <c:when test="${not empty pictureList}">
                <div class="banner">
                    <div id="slide-holder">
                        <div id="slide-runner">
                            <c:forEach items="${pictureList}" var="picture" varStatus="status">
                                <a href="/picture/index" target="_blank">
                                    <img id="slide-img-${status.count}" width="100%" height="300px" src="${picture.pictureName}" alt="${picture.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"/>
                                </a>
                            </c:forEach>
                            <div id="slide-controls">
                                <p id="slide-nav"></p>
                            </div>
                        </div>
                    </div>
                    <script>
                        if (!window.slider) {
                            var slider = {};
                        }
                        slider.data = [{
                            "id": "slide-img-1",
                            "client": "",
                            "desc": ""
                        }, {
                            "id": "slide-img-2",
                            "client": "",
                            "desc": ""
                        }, {
                            "id": "slide-img-3",
                            "client": "",
                            "desc": ""
                        }, {
                            "id": "slide-img-4",
                            "client": "",
                            "desc": ""
                        }];
                    </script>
                </div>
            </c:when>
            <c:otherwise>
                暂无相关信息
            </c:otherwise>
        </c:choose>
        <!-- banner代码 结束 -->
        <div class="topnews">
            <c:choose>
                <c:when test="${not empty clickNumList}">
                    <c:forEach items="${clickNumList}" var="essay" varStatus="status">
                        <div class="blogs">
                            <figure>
                                <img src="${essay.pictureName}" width="100%" height="300px" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"></figure>
                            <ul>
                                <h3>
                                    <a href="/essay/detail/${essay.essayId}" target="_blank">
                                        <c:if test="${fn:length(essay.title)>12}">
                                            ${fn:substring(essay.title,0,12)}...
                                        </c:if>
                                        <c:if test="${fn:length(essay.title)<=12}">
                                            ${essay.title}
                                        </c:if>
                                    </a>
                                </h3>
                                <c:if test="${fn:length(essay.content)>100}">
                                    ${fn:substring(essay.content,0,100)}...
                                </c:if>
                                <c:if test="${fn:length(essay.content)<=100}">
                                    ${essay.content}
                                </c:if>
                                <p class="autor">
                                    <span class="lm f_l"><a>个人博客</a></span>
                                    <span class="dtime f_l">${essay.doTime}</span>
                                </p>
                            </ul>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="r_box f_r">
        <div class="bar">
            <form id="searchForm" action="/web/search" method="post">
                <input type="text" name="keyWord" placeholder="请输入您要搜索的内容...">
                <input type="hidden" name="pageIndex" value="1"/>
                <button type="botton" onclick="$('#searchForm').submit();"></button>
            </form>
        </div>
        <div class="tit01">
            <h3>关注我</h3>
            <div class="gzwm">
                <ul>
                    <li>
                        <a class="xlwb" href="http://open.weibo.com/authentication/" target="_blank">新浪微博</a>
                    </li>
                    <li>
                        <a class="txwb" href="http://t.qq.com/" target="_blank">腾讯微博</a>
                    </li>
                    <li>
                        <a class="rss" href="https://www.rssplus.net/auth/login" target="_blank">RSS</a>
                    </li>
                    <li>
                        <a class="wx" href="https://mail.qq.com/" target="_blank">邮箱</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="ad300x100">
            <img src="/static/images/ad300x100.jpg" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">
        </div>
        <div class="moreSelect" id="lp_right_select">
            <script>
                window.onload = function () {
                    var oLi = document.getElementById("tab").getElementsByTagName("li");
                    var oUl = document.getElementById("ms-main").getElementsByTagName("div");

                    for (var i = 0; i < oLi.length; i++) {
                        oLi[i].index = i;
                        oLi[i].onmouseover = function () {
                            for (var n = 0; n < oLi.length; n++)
                                oLi[n].className = "";
                            this.className = "cur";
                            for (var n = 0; n < oUl.length; n++)
                                oUl[n].style.display = "none";
                            oUl[this.index].style.display = "block";
                        }
                    }
                }
            </script>
            <div class="ms-top">
                <ul class="hd" id="tab">
                    <li class="cur"><a href="/essay/listType?show=clickNum" target="_blank">点击排行</a></li>
                    <li><a href="/essay/listType?show=doTime" target="_blank">最新文章</a></li>
                    <li><a href="/essay/listType?show=showLevel" target="_blank">站长推荐</a></li>
                </ul>
            </div>
            <div class="ms-main" id="ms-main">
                <div style="display: block;" class="bd bd-news">
                    <ul>
                        <c:choose>
                            <c:when test="${not empty clickNumList}">
                                <c:forEach items="${clickNumList}" var="essay" varStatus="status">
                                    <li><a href="/essay/detail/${essay.essayId}" target="_blank">
                                        <c:if test="${fn:length(essay.title)>10}">
                                            ${fn:substring(essay.title,0,10)}...
                                        </c:if>
                                        <c:if test="${fn:length(essay.title)<=10}">
                                            ${essay.title}
                                        </c:if>
                                    </a></li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                暂无相关信息
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <div class="bd bd-news">
                    <ul>
                        <c:choose>
                            <c:when test="${not empty doTimeList}">
                                <c:forEach items="${doTimeList}" var="essay" varStatus="status">
                                    <li><a href="/essay/detail/${essay.essayId}" target="_blank">
                                        <c:if test="${fn:length(essay.title)>10}">
                                            ${fn:substring(essay.title,0,10)}...
                                        </c:if>
                                        <c:if test="${fn:length(essay.title)<=10}">
                                            ${essay.title}
                                        </c:if>
                                    </a></li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                暂无相关信息
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
                <div class="bd bd-news">
                    <ul>
                        <c:choose>
                            <c:when test="${not empty showLevelList}">
                                <c:forEach items="${showLevelList}" var="essay" varStatus="status">
                                    <li><a href="/essay/detail/${essay.essayId}" target="_blank">
                                        <c:if test="${fn:length(essay.title)>10}">
                                            ${fn:substring(essay.title,0,10)}...
                                        </c:if>
                                        <c:if test="${fn:length(essay.title)<=10}">
                                            ${essay.title}
                                        </c:if>
                                    </a></li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                暂无相关信息
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
        <div class="tuwen">
            <h3>图文推荐</h3>
            <ul>
                <c:choose>
                    <c:when test="${not empty showLevelList}">
                        <c:forEach items="${showLevelList}" var="essay" varStatus="status">
                            <li>
                                <a href="/essay/detail/${essay.essayId}" target="_blank">
                                    <img src="${essay.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"/>
                                    <b>
                                        <c:if test="${fn:length(essay.title)>10}">
                                            ${fn:substring(essay.title,0,10)}...
                                        </c:if>
                                        <c:if test="${fn:length(essay.title)<=10}">
                                            ${essay.title}
                                        </c:if>
                                    </b>
                                </a>
                                <p>
                                    <span class="tutime">${essay.doTime}</span>
                                </p>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        暂无相关信息
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</article>
<footer>
    <p class="ft-copyright">
        兔小白个人博客&nbsp;&nbsp;
        <c:choose>
        <c:when test="${role=='-1'}">
        <a href="/login.jsp">登录</a>
        </c:when>
        <c:when test="${role=='0'}">
        <a href="/user/logout">注销</a>
        </c:when>
        <c:otherwise>
        <a href="/web/manage" target="_blank">管理</a>
        <a href="/user/logout">注销</a>
        </c:otherwise>
        </c:choose>
    <div style="float:right;">
        <span>相关下载：</span>
        <a href="javascript:void(0);" onclick="downLoad('一睁眼.m4a','static\\music\\一睁眼.m4a')">背景音乐</a>
        &nbsp;|&nbsp;
        <a href="javascript:void(0);" onclick="downLoad('notepad.apk','static\\app\\notepad.apk')">APP</a>
        &nbsp;
    </div>
    </p>
</footer>
<script>
    function downLoad(fileName, filePathSuffix) {
        window.location.href = "/web/downLoad?fileName="
            + encodeURIComponent(fileName) + "&filePathSuffix="
            + encodeURIComponent(filePathSuffix);
    }
</script>
</body>
</html>