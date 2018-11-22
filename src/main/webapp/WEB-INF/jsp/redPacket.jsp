<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <script src="/static/js/jquery.min.js"></script>
    <link href="/static/css/aboutMe.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" href="/static/css/buttons.css" type="text/css">
    <style type="text/css">
        #cover {
            position: absolute;
            left: 0px;
            top: 0px;
            background: rgba(0, 0, 0, 0.4);
            width: 100%; /*宽度设置为100%，这样才能使隐藏背景层覆盖原页面*/
            height: 100%;
            filter: alpha(opacity=60); /*设置透明度为60%*/
            opacity: 0.6; /*非IE浏览器下设置透明度为60%*/
            display: none;
            z-Index: 99;
        }

        #modal {
            margin: 50px auto;
            width: 300px;
            text-align: center;
            background: rgba(202, 106, 255, 0.2);
            border-color: #fff;
            color:#ff5c69;
            display: none;
            cursor: pointer;
            z-Index: 999;
        }
    </style>
    <script>
        function doRedPacket(packetId) {
            showZz();
            $.post('/web/doRedPacket?_method=put',{'packetId':packetId},
                function (result) {
                    alert(result);
                    hiddenZz();
                }
            )
        }

        function showZz() {
            cover.style.display = "block";   //显示遮罩层
            modal.style.display = "block";   //显示弹出层
        }

        function hiddenZz() {
            cover.style.display = "none";   //隐藏遮罩层
            modal.style.display = "none";   //隐藏弹出层
        }
    </script>
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
    <div align="center">
        <section class="content">
            <div style="font-size: 22px;margin-top: 30px;">红包活动列表</div>
            <ul>
                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach items="${list}" var="pickRedPacket" varStatus="status">
                            <li style="height:50px;font-size: 18px;margin-top: 10px;line-height:50px;color: #fff">
                                <div style="width:85%;float: left;background:#48AE15;">
                                    (${status.count})红包总个数：${pickRedPacket.number}&nbsp;&nbsp;发布时间：${pickRedPacket.doTime}
                                </div>
                                <div style="height:15%;float: right;">
                                    <a class="button button-glow button-border button-rounded button-primary" href="#" onclick="doRedPacket('${pickRedPacket.id}');">立即抢</a>
                                </div>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <li style="height:60px;">
                            暂无相关活动
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </section>
    </div>
    <div id="cover"></div>
    <div id="modal">
        <div style="font-size: 28px;">正在秒杀中...</div>
    </div>
</article>
<jsp:include page="/WEB-INF/jsp/include/bottom_second.jsp"/>
</body>
</html>