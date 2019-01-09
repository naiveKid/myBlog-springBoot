<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="/static/js/jquery-1.10.2.min.js"></script>
    <!-- layer -->
    <script src="/static/js/layer/layer.js" type="text/javascript"></script>
    <script type="text/javascript">
        function refer(time) {
            //过渡动画
            layer.load();
            setTimeout(function () {
                window.location.href = "/${gotoPage}";//#设定跳转的链接地址
            }, time);//延迟time毫秒
        }

        <c:choose>
        <c:when test="${not empty alert}">
        layer.alert('${alert}', function (index) {
            <c:if test="${not empty gotoPage}">
            refer(500);
            </c:if>
            layer.close(index);
        });
        </c:when>
        <c:when test="${not empty msg}">
        layer.msg('${msg}', {time: 1000}, function () {
            <c:if test="${not empty gotoPage}">
            refer(10);
            </c:if>
        });
        </c:when>
        <c:otherwise>
        refer(500);
        </c:otherwise>
        </c:choose>
    </script>
</head>
<body>
</body>
</html>