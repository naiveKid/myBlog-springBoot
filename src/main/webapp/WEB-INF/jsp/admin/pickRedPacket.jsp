<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>pickRedPacket</title>
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
            <table width="80%" align="center">
                <tr>
                    <td height="40" align="center"><strong style="font-size: 20px;">红包活动列表</strong></td>
                </tr>
            </table>
            <blockquote class="layui-elem-quote layui-quote-nm">
                <p style="font-size: 14px;">
                    更多操作
                    <a style="margin-left: 1%; color: black;" href="/redPacket/addPage">
                        <font color="red">发布新红包活动</font>
                    </a>
                </p>
            </blockquote>
            <table class="layui-table">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <th width="260px">发布时间</th>
                    <th width="160px">总金额</th>
                    <th width="150px">总个数</th>
                    <th width="160px">剩余个数</th>
                    <th width="160px">类型</th>
                    <th>备注信息</th>
                </tr>
                </thead>

                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach items="${list}" var="redPacket" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${redPacket.doTime}</td>
                                <td>${redPacket.sumMoney}</td>
                                <td>${redPacket.number}</td>
                                <td>${redPacket.restNumber}</td>
                                <td>
                                    <c:if test="${redPacket.type=='0'}">
                                        均分金额
                                    </c:if>
                                    <c:if test="${redPacket.type=='1'}">
                                        随机金额
                                    </c:if>
                                </td>
                                <td>${redPacket.remarks}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="7">还没有发布红包!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </section>
        <jsp:include page="/WEB-INF/jsp/include/page.jsp">
            <jsp:param name="link" value="/redPacket/manage"/>
        </jsp:include>
    </aside>
</div>
<jsp:include page="./include/bottom.jsp"></jsp:include>
</body>
</html>