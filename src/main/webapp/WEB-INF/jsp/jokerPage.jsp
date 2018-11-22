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
        function checkForm() {
            var boyName = document.getElementById("boyName").value;
            var girlName = document.getElementById("girlName").value;
            if (boyName == null || boyName == "" || boyName.indexOf(" ") >= 0) {
                if (boyName.indexOf(" ") >= 0) {
                    alert("你给男主输入的啥名字？咋还有空格呢？");
                    return false;
                }
                alert("你倒是输入个男主名字啊兄弟！");
                return false;
            }
            if (girlName == null || girlName == "" || girlName.indexOf(" ") >= 0) {
                if (girlName.indexOf(" ") >= 0) {
                    alert("你给女主输入的啥名字？咋还有空格呢？");
                    return false;
                }
                alert("输入了男主名字就不输入女主名字了？兄弟！");
                return false;
            }
            window.location.href = "/web/joker?boyName=" + encodeURIComponent(boyName) + "&girlName=" + encodeURIComponent(girlName);
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
            <div style="font-size: 16px;">
                <section class="content">
                    <span class="input input--minoru">
                        <input class="input__field input__field--minoru" type="text" maxlength="32" id="boyName" name="boyName"/>
                        <label class="input__label input__label--minoru" for="boyName">
                            <span class="input__label-content input__label-content--minoru">输入男主角姓名</span>
                        </label>
                    </span>
                    <span class="input input--minoru">
                        <input class="input__field input__field--minoru" type="text" maxlength="32" id="girlName" name="girlName"/>
                        <label class="input__label input__label--minoru" for="boyName">
                            <span class="input__label-content input__label-content--minoru">输入女主角姓名</span>
                        </label>
                    </span>
                </section>
                <div>
                    <a class="button button-glow button-border button-rounded button-primary" href="#" onclick="checkForm()">提交</a>
                </div>
            </div>
        </section>
    </div>
</article>
<jsp:include page="/WEB-INF/jsp/include/bottom_second.jsp"/>
</body>
</html>