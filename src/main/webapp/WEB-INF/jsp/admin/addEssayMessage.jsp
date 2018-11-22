<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>essay</title>
    <script type="text/javascript">
        function addCheck() {
            var message = document.getElementById("message").value;
            if (message == "") {
                alert("订阅发布信息不能为空!");
                document.getElementById("message").focus();
                return false;
            }
            // 提交表单
            document.forms["addMessageForm"].submit();
        }

        function goBack() {
            if (window.confirm("确定放弃?")) {
                window.location.href = "/essay/manage";
            }
        }
    </script>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="3"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <form id="addMessageForm" name="addMessageForm" action="/essay/publishMessage" method="post">
                <table class="bordered">
                    <tr>
                        <td colspan="2" height="40"><strong style="font-size: 20px;">添加订阅发布信息</strong></td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">订阅发布内容</td>
                        <td height="30" style="font-size: 18px;">
                            <input type="text" style="width:90%;height:24px;" name="message" id="message" value="">
                        </td>
                    </tr>
                </table>
                <br> <br><br>
                <center>
                    <input type="button" class="mybtn" value="确定" onclick="addCheck();">
                    <input type="button" class="blue" value="返回" onclick="goBack();">
                </center>
            </form>
        </section>
    </aside>
</div>
</body>
</html>