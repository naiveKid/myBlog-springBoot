<%@page language="java" contentType="text/html; charset=utf-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/static/css/login.css"/>
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/login.js"></script>
    <title>用户中心-登录</title>
    <script>
        function reloadCode1() {
            var time = new Date().getTime();
            //同一个URL请求路径，默认从缓存中去读取对应内容,此处要改变图片,必须改变URL地址
            //所以此处会增加一个'没用'的参数d
            document.getElementById("imagecode1").src = "/captcha/generatImg?d="
                + time;
        }
    </script>
</head>
<body>
<div class="header_top">
    <a href="/web/myBlog/index" class="top-text">个人博客</a>
</div>
<form method="post" name="loginForm"
      action="/user/login">
    <div class="login_con">
        <div class="con">
            <div class="pr zcon">
                <a class="f18 white login mb40">登陆</a><a
                    href="/register.jsp"
                    class="f18 regis mb40 bg-e8">注册</a> <span
                    class="pa pl25 fc-red name-tip"></span>
                <div class="pr w300 login-input-wrap">
                    <input name="userName" id="userName" placeholder="用户名"
                           class="w290 fc-7B858D f32 login-input" value="" type="text"
                           required="required">
                    <div class="cb"></div>
                </div>
                <div class="w300 login-input-wrap lg-psw">
                    <input id="password" name="password" placeholder="登录密码"
                           class="w240 fc-7B858D f32 login-input" value="" type="password"
                           required="required">
                    <div class="fr cur pwd-icon">&nbsp;</div>
                </div>
                <div class="clearfix">
                    <div class="fl w160 login-input-wrap">
                        <input name="checkcode" id="checkcode" placeholder="请输入验证码"
                               class="w140 login-input" value="" type="text"
                               required="required" onkeydown="enterIn(event);">
                    </div>
                    <span class="ilb fl login-yzm" onClick="reloadCode1();"><img
                            id="imagecode1" src="/captcha/generatImg"
                            alt="验证码"></span> <span
                        class="ilb fl ml5 f12 fc-7B8591 login-yzm-txt cur"
                        onClick="reloadCode1();">看不清换一张</span>
                </div>
                <div style="width:80px;float: right;">
                    <input type="checkbox" name="rememberMe"/><font style="font-size:12px;">记住我</font>
                </div>
                <input id="loginSubmit" value="登 录"
                       class="f18 white bg-red w423 mt20 bn" type="button"
                       onClick="checkLoginForm();">

                <div class="f14 gray ml55 ml30">
                    没有账号？<span class="fc-red ml15"><a
                        href="/register.jsp">免费注册</a></span>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>