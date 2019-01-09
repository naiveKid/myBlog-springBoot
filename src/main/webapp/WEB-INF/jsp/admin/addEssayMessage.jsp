<%@ page language="java" contentType="text/html; charset=utf-8" %>
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
                layer.alert("订阅发布信息不能为空!", {icon: 2});
                document.getElementById("message").focus();
                return false;
            }
            $.ajax({
                type : "post",
                url : "/essay/publishMessage",
                data : "random=" + Math.random()+"&message="+message,
                async : false,
                success : function(data){}
            });
        }

        function goBack() {
            layer.confirm('确定放弃？',{icon: 3, title:'提示'}, function(index){
                window.location.href = "/essay/manage";
            });
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
            <table class="layui-table">
                <tr>
                    <td colspan="2" height="40"><strong style="font-size: 20px;">添加订阅发布信息</strong></td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">订阅发布内容</td>
                    <td height="30" style="font-size: 18px;">
                        <input type="text" style="width:90%;height:24px;" name="message" id="message" class="layui-input" value="">
                    </td>
                </tr>
            </table>
            <br> <br><br>
            <center>
                <input type="button" class="layui-btn" value="确定" onclick="addCheck();">
                <input type="button" class="layui-btn layui-btn-danger" value="返回" onclick="goBack();">
            </center>
        </section>
    </aside>
</div>
</body>
</html>