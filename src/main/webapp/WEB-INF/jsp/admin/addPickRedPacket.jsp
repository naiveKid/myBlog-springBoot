<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>pickRedPacket</title>
    <script type="text/javascript">
        function addCheck() {
            var sumMoney = document.getElementById("sumMoney").value;
            if (sumMoney == "" || sumMoney == "0" || sumMoney == "0." || sumMoney == "0.0" || sumMoney == "0.00") {
                layer.alert('红包总金额不能为空!', {icon: 2});
                document.getElementById("sumMoney").focus();
                return false;
            }
            if (!isPriceNumber(sumMoney)) {
                layer.alert('红包总金额格式不正确!', {icon: 2});
                document.getElementById("sumMoney").focus();
                return false;
            }
            var number = document.getElementById("number").value;
            if (number == "" || number == "0" || number == "0." || number == "0.0" || number == "0.00") {
                layer.alert('领取总个数不能为空!', {icon: 2});
                document.getElementById("number").focus();
                return false;
            }
            if (!isPriceNumber(number)) {
                layer.alert('领取总个数格式不正确!', {icon: 2});
                document.getElementById("number").focus();
                return false;
            }
            // 提交表单
            document.forms["addForm"].submit();
        }

        function isPriceNumber(_keyword) {
            if (_keyword == "0" || _keyword == "0." || _keyword == "0.0" || _keyword == "0.00") {
                return true;
            } else {
                var index = _keyword.indexOf("0");
                var length = _keyword.length;
                if (index == 0 && length > 1) {/*0开头的数字串*/
                    var reg = /^[0]{1}[.]{1}[0-9]{1,2}$/;
                    if (!reg.test(_keyword)) {
                        return false;
                    } else {
                        return true;
                    }
                } else {/*非0开头的数字*/
                    var reg = /^[1-9]{1}[0-9]{0,10}[.]{0,1}[0-9]{0,2}$/;
                    if (!reg.test(_keyword)) {
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        }

        function goBack() {
            layer.confirm('确定放弃？', {icon: 3, title: '提示'}, function (index) {
                window.location.href = "/redPacket/manage";
            });
        }
    </script>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="8"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <form id="addForm" name="addForm" action="/redPacket/add" method="post" class="layui-form">
                <table class="layui-table">
                    <tr>
                        <td colspan="2" height="40"><strong style="font-size: 20px;">新增红包活动</strong></td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">红包总金额：</td>
                        <td height="40" style="font-size: 18px;">
                            <input type="text" style="width:90%;height:24px;" name="sumMoney" class="layui-input" id="sumMoney" value="">
                        </td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">领取总个数</td>
                        <td height="30" style="font-size: 18px;">
                            <input type="text" style="width:90%;height:24px;" name="number" class="layui-input" id="number" value="">
                        </td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">红包的类型</td>
                        <td height="30" style="font-size: 18px;">
                            <select name="type">
                                <option value="0" selected="selected">均分金额</option>
                                <option value="1">随机金额</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">备注信息</td>
                        <td height="30" style="font-size: 18px;">
                            <input type="text" style="width:90%;height:24px;" name="remarks" class="layui-input" id="remarks" value="">
                        </td>
                    </tr>
                </table>
                <br/> <br/> <br/>
                <center>
                    <input type="button" class="layui-btn" value="确定" onclick="addCheck();"/>
                    <input type="button" class="layui-btn layui-btn-danger" value="返回" onclick="goBack();"/>
                </center>
            </form>
        </section>
    </aside>
</div>
<script src="/static/js/layui/layui.all.js"></script>
</body>
</html>