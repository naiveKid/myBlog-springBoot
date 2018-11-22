$(function() {
	$('.pwd-icon').click(function() {
		var _d = $(this).closest('.lg-psw');
		if (_d.hasClass('pwd-show')) {
			_d.removeClass('pwd-show');
			_d.find('input').attr('type', 'password');
		} else {
			_d.addClass('pwd-show');
			_d.find('input').attr('type', 'text');
		}
	})
})
function checkForm() {
	// 验证用户名框
	var userName = document.getElementById("userName").value;
	if (userName == "") {
		alert("请输入用户名!");
		// 焦点回到"用户名"框
		document.getElementById("userName").focus();
		return false;
	}
	// 验证密码框
	var password = document.getElementById("password").value;
	if (password == "") {
		alert("请输入密码!");
		// 焦点回到"密码"框
		document.getElementById("password").focus();
		return false;
	}
	/* 匹配6-16个字符，只能是数字或字母的正则表达式 */
	var re0 = /^[a-zA-Z0-9]{6,16}$/;
	if (re0.test(password) == false) {
		if (password.length < 6)
			alert("密码至少6位!");
		else if (password.length > 16) {
			alert("密码至多16位!");
		} else {
			alert("请确保密码只含有数字和字母!");
		}
		// 焦点回到"密码"框
		document.getElementById("password").focus();
		return false;
	}

	// 验证码框
	var checkcode = document.getElementById("checkcode").value;
	var re1 = /^[a-zA-Z0-9]{4,4}$/;
	if (checkcode == "") {
		alert("请输入验证码!");
		// 焦点回到"验证"框
		document.getElementById("checkcode").focus();
		return false;
	}
	if (re1.test(checkcode) == false) {
		alert("验证码输入错误!");
		// 焦点回到"验证"框
		document.getElementById("checkcode").focus();
		return false;
	}
	// 提交注册表单
	document.forms["addUserForm"].submit();
}

function checkUserName() {
	var usernameObj = $("#userName");
	var userName = usernameObj.val();

	if (userName != "") {
		var id = document.activeElement.id;
		if (id == "userName") {
			// 每当用户名发生变化时,如果用户名不为空就向服务器端发出AJAX请求查询用户名是否重复
			$.ajax({
				type : "post",
				url : "/user/checkUser",
				data : "random=" + Math.random() + "&userName="
						+ encodeURI(encodeURI(userName)),
				async : false,
				success : function(data) {// 获得数据成功,则显示在id为result的元素处
					if (data == "false") {
						$("#result").html("<font color='red'>该用户名已被使用!</font>");
					} else if (data == "true") {
						$("#result").html("<font color='green'>恭喜您，此用户名可用!</font>");
					}
				},
				error : function() {
					$("#result").html("<font color='red'>网络超时,请刷新重试...</font>");
				}
			});
		}
	} else {
		var id = document.activeElement.id;
		if (id == "password" || id == "email" || id == "checkcode") {
			document.getElementById("result").innerHTML = "用户名不能为空";
			document.getElementById("result").style.color = "red";
		} else {
			document.getElementById("result").innerHTML = "";
		}
	}
}
function enterIn(evt) {
	var evt = evt ? evt : (window.event ? window.event : null);// 兼容IE和FF
	if (evt.keyCode == 13) {
		// 提交当前表单
		checkForm();
	}
}