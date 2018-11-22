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
    <link rel="stylesheet" type="text/css" href="/static/css/input/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/input/demo.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/input/component.css"/>
    <link rel="stylesheet" href="/static/css/buttons.css" type="text/css">
    <script>
        function getMobileInfo() {
            $.post('/web/getMobileInfo',{'telPhoneNumber':$("#telPhoneNumber").val()},
                function (result) {
                    alert(result);
                }
            )
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
            <h2>号码归属地查询</h2>
            <span class="input input--minoru">
                        <input class="input__field input__field--minoru" type="text" maxlength="32" id="telPhoneNumber" name="telPhoneNumber"/>
                        <label class="input__label input__label--minoru" for="telPhoneNumber">
                            <span class="input__label-content input__label-content--minoru">输入电话号码</span>
                        </label>
                    </span>
        </section>
        <p>
            <a class="button button-glow button-border button-rounded button-primary" href="#" onclick="getMobileInfo()">提交</a>
        </p>
    </div>
</article>
<jsp:include page="/WEB-INF/jsp/include/bottom_second.jsp"/>
</body>
</html>