<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>Other</title>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="6"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <table width="80%" align="center">
                <tr>
                    <td height="40" align="center"><strong style="font-size: 20px;">首页幻灯片</strong></td>
                </tr>
            </table>
            <blockquote class="layui-elem-quote layui-quote-nm">
                <p style="font-size: 14px;">
                    更多操作
                    <a style="margin-left: 1%; color: black;" href="/web/other/addPage">
                        <font color="red">发布新幻灯片</font>
                    </a>
                </p>
            </blockquote>
            <table class="layui-table">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <th width="160px">缩略图</th>
                    <th>相片名</th>
                    <th width="10%">操作</th>
                </tr>
                </thead>
                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach items="${list}" var="picture" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td><img src="${picture.pictureName}" alt="${picture.pictureName}" style="width:100px;height:100px;" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"/></td>
                                <td>
                                    <c:if test="${fn:length(picture.pictureName)>30}">
                                        ${fn:substring(picture.pictureName,0,30)}...
                                    </c:if>
                                    <c:if test="${fn:length(picture.pictureName)<=30}">
                                        ${picture.pictureName}
                                    </c:if>
                                </td>
                                <td>
                                    <a href="#" onclick="del('/web/other/del/${picture.pictureId}');">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="3">还没有发布图片!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </section>
        <jsp:include page="/WEB-INF/jsp/include/page.jsp">
            <jsp:param name="link" value="/web/other"/>
        </jsp:include>
    </aside>
</div>
</body>
</html>
