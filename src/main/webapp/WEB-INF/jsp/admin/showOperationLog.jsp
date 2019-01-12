<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <link rel="stylesheet" type="text/css" href="/static/css/webuploader.css">
    <script type="text/javascript" src="/static/js/webuploader.js"></script>
    <title>showOperator</title>
    <script type="text/javascript">
        function goBack() {
            window.location.href = "/systemLog/listPagedOperationLog";
        }
    </script>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="7"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <table class="layui-table">
                <tr>
                    <td colspan="2" height="40"><strong style="font-size: 20px;">日志详情</strong></td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">操作用户：</td>
                    <td height="40" style="font-size: 18px;">${log.userName}</td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">操作时间：</td>
                    <td height="40" style="font-size: 18px;"><fmt:formatDate value='${log.operationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">耗时：</td>
                    <td height="40" style="font-size: 18px;">${log.timeConsuming}ms</td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">URI：</td>
                    <td height="40" style="font-size: 18px;">${log.requestUri}</td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">请求类型：</td>
                    <td height="40" style="font-size: 18px;">${log.method}</td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">请求参数：</td>
                    <td height="40" style="font-size: 18px;">${log.params}</td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">IP地址：</td>
                    <td height="40" style="font-size: 18px;">${log.remoteAddr}</td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">用户代理：</td>
                    <td height="40" style="font-size: 18px;">${log.userAgent}</td>
                </tr>
                <tr>
                    <td height="30" style="font-size: 18px;">异常信息：</td>
                    <td height="40" style="font-size: 18px;">${log.exception}</td>
                </tr>
            </table>
            <br/>
            <center>
                <input type="button" class="layui-btn layui-btn-danger" value="返回" onclick="goBack();"/>
            </center>
            </form>
        </section>
    </aside>
</div>
<jsp:include page="./include/bottom.jsp"></jsp:include>
</body>
</html>