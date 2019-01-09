<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <title>Photo</title>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="4"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <table width="80%" align="center">
                <tr>
                    <td height="40" align="center"><strong style="font-size: 20px;">相片列表</strong></td>
                </tr>
            </table>
            <p>
                <a style="margin-left: 1%; color: black;" href="/picture/addPage">更多操作：
                    <font color="red">发表新相册</font>
                </a>
            </p>
            <table class="bordered">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <th width="160px">缩略图</th>
                    <th>相片名</th>
                    <th width="20%">相片标题</th>
                    <th width="30%">相片描述</th>
                    <th width="10%">操作</th>
                </tr>
                </thead>

                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach items="${list}" var="picture" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td><img src="${picture.pictureName}" alt="${picture.pictureName}" style="width:100px;height:100px;" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"/></td>
                                <td><c:if test="${fn:length(picture.pictureName)>30}">
                                    ${fn:substring(picture.pictureName,0,30)}...
                                </c:if> <c:if
                                        test="${fn:length(picture.pictureName)<=30}">
                                    ${picture.pictureName}
                                </c:if></td>
                                <td><c:if test="${fn:length(picture.pictureTitle)>20}">
                                    ${fn:substring(picture.pictureTitle,0,20)}...
                                </c:if> <c:if
                                        test="${fn:length(picture.pictureTitle)<=20}">
                                    ${picture.pictureTitle}
                                </c:if></td>
                                <td>
                                    <c:if test="${fn:length(picture.pictureContent)>20}">
                                        ${fn:substring(picture.pictureContent,0,20)}...
                                    </c:if>
                                    <c:if test="${fn:length(picture.pictureContent)<=20}">
                                        ${picture.pictureContent}
                                    </c:if>
                                </td>
                                <td>
                                    <a href="/picture/alterPage/${picture.pictureId}">修改</a>
                                    <a href="#" onclick="del('/picture/deletePicture/${picture.pictureId}');">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6">还没有发布图片!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </section>
        <jsp:include page="/WEB-INF/jsp/include/page.jsp">
            <jsp:param name="link" value="/picture/manage"/>
        </jsp:include>
    </aside>
</div>
</body>
</html>