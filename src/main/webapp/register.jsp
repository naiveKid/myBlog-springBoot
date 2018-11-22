<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="/static/css/register.css" />
<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<script type="text/javascript" src="/static/js/registerCheck.js"></script>
<title>用户中心-注册</title>
<script>
	function reloadCode() {
		var time = new Date().getTime();
		//同一个URL请求路径，默认从缓存中去读取对应内容,此处要改变图片,必须改变URL地址
		//所以此处会增加一个'没用'的参数d
        document.getElementById("imagecode").src = "/captcha/generatImg?d="
            + time;
	}
</script>
</head>
<body>
	<div class="header_top">
		<a href="/web/myBlog/index" class="top-text">个人博客</a>
	</div>
	<form method="post" name="addUserForm"
		action="/user/register">
		<input name="isAdmin" id="isAdmin" type="hidden" value="0">
		<div class="login_con login_con-r">
			<div class="pr zcon">
				<a href="/login.jsp" class="f18 white login mb40 bg-e8">登陆</a><a
					class="f18 regis mb40">注册</a>
				<div class="w300 login-input-wrap">
					<input name="userName" id="userName" placeholder="请输入用户名"
						class="w290 fc-7B858D f32 login-input" type="text"
						onpropertychange="checkUserName();" oninput="checkUserName();"
						required="required">
				</div>
				<div class="userName-result">
					<span id="result"></span>
				</div>
				<div class="w300 login-input-wrap lg-psw">
					<input id="password" name="password" placeholder="登录密码"
						class="w240 fc-7B858D f32 login-input" value="" type="password"
						required="required" onfocus="checkUserName();">
					<div class="fr cur pwd-icon">&nbsp;</div>
				</div>
				<div class="clearfix">
					<div class="fl w160 login-input-wrap">
						<input name="checkcode" id="checkcode" placeholder="请输入验证码"
							class="w140 login-input" value="" type="text"
							onkeydown="enterIn(event);" required="required"
							onfocus="checkUserName();">
					</div>
					<span class="ilb fl login-yzm" onClick="reloadCode();"><img
						id="imagecode" src="/imageUtil"
						alt="验证码"></span> <span
						class="ilb fl ml5 f12 fc-7B8591 login-yzm-txt cur" id="yzm"
						onClick="reloadCode();">看不清换一张</span>
				</div>
				<input id="regSubmit" value="注 册" class="f18 white bg-red mt20 bn"
					type="button" onClick="checkForm();">
				<div class="f14 gray ml55 ml30">
					已有账号？<span class="fc-red ml15"><a
						href="/login.jsp">直接登录</a></span>
				</div>
			</div>
		</div>
	</form>
</body>
</html>