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
function checkLoginForm() {
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
        if (password.length < 6) alert("密码至少6位!");
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
    if (checkcode == "") {
        alert("请输入验证码!");
        // 焦点回到"验证"框
        document.getElementById("checkcode").focus();
        return false;
    }
    var re1 = /^[a-zA-Z0-9]{4,4}$/;
    if (re1.test(checkcode) == false) {
        alert("验证码输入错误!");
        // 焦点回到"验证"框
        document.getElementById("checkcode").focus();
        return false;
    }
    // 提交表单
    document.forms["loginForm"].submit();
}
function enterIn(evt) {
    var evt = evt ? evt: (window.event ? window.event: null); // 兼容IE和FF
    if (evt.keyCode == 13) {
        // 提交当前表单
        checkLoginForm();
    }
}