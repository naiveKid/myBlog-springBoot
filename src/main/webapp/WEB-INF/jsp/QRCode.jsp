<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <script src="/static/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/input/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/input/demo.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/input/component.css"/>
    <link rel="stylesheet" href="/static/css/buttons.css" type="text/css">
    <link href="/static/css/aboutMe.css" type="text/css" rel="stylesheet"/>
    <link href="/static/css/layer.css" type="text/css" rel="stylesheet"/>
    <script>
        function getQRCode() {
            if ($("#qrcode").val() == "") {
                alert("输入信息不能为空.");
                return false;
            } else {
                $.post("/web/QRCode", {
                    info: $("#qrcode").val()
                }, function (data) {
                    if (data != "fail") {
                        $("#ewmsrc").attr('src', data);
                        $('#code').center();
                        $('#goodcover').show();
                        $('#code').fadeIn();
                    }
                });
            }
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
    <form action="" method="post">
        <div align="center">
            <section class="content">
                <h2>生成自定义的二维码</h2>
                <span class="input input--minoru">
                        <input class="input__field input__field--minoru" type="text" maxlength="32" id="qrcode" name="qrcode"/>
                        <label class="input__label input__label--minoru" for="qrcode">
                            <span class="input__label-content input__label-content--minoru">输入二维码信息</span>
                        </label>
                    </span>
            </section>
            <p>
                <a class="button button-glow button-border button-rounded button-primary" href="#" onclick="getQRCode()">提交</a>
            </p>
        </div>
    </form>
    <div id="goodcover"></div>
    <div id="code">
        <div class="close1">
            <a href="javascript:void(0)" id="closebt"><img src="/static/images/close.gif" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"></a>
        </div>
        <div class="goodtxt">
            <p>微信扫一扫</p>
        </div>
        <div class="code-img">
            <img id="ewmsrc" src="#">
        </div>
    </div>
</article>
<jsp:include page="/WEB-INF/jsp/include/bottom_second.jsp"/>
</body>
<script>
    $(function () {
        $('#closebt').click(function () {
            $('#code').hide();
            $('#goodcover').hide();
        });
        $('#goodcover').click(function () {
            $('#code').hide();
            $('#goodcover').hide();
        });
        jQuery.fn.center = function (loaded) {
            var obj = this;
            body_width = parseInt($(window).width());
            body_height = parseInt($(window).height());
            block_width = parseInt(obj.width());
            block_height = parseInt(obj.height());

            left_position = parseInt((body_width / 2) - (block_width / 2) + $(window).scrollLeft());
            if (body_width < block_width) {
                left_position = 0 + $(window).scrollLeft();
            }
            ;

            top_position = parseInt((body_height / 2) - (block_height / 2) + $(window).scrollTop());
            if (body_height < block_height) {
                top_position = 0 + $(window).scrollTop();
            }
            ;

            if (!loaded) {
                obj.css({
                    'position': 'absolute'
                });
                obj.css({
                    'top': ($(window).height() - $('#code').height()) * 0.5,
                    'left': left_position
                });
                $(window).bind('resize', function () {
                    obj.center(!loaded);
                });
                $(window).bind('scroll', function () {
                    obj.center(!loaded);
                });
            } else {
                obj.stop();
                obj.css({
                    'position': 'absolute'
                });
                obj.animate({
                    'top': top_position
                }, 200, 'linear');
            }
        }
    })
</script>
</html>