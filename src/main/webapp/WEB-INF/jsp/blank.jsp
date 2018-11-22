<%@page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <c:if test="${not empty msg}">
        <script type="text/javascript">
            alert('${msg}');
        </script>
    </c:if>
    <c:if test="${not empty gotoPage}">
        <script type="text/javascript">
            setInterval("refer()", 500);
            var goUrl = "/${gotoPage}";

            function refer() {
                window.location.href = goUrl;//#设定跳转的链接地址
            }
        </script>
    </c:if>
</head>
<body>
</body>
</html>