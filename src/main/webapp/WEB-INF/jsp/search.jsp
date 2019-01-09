<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <link href="/static/css/essay.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/page.css" type="text/css">
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/sliders.js"></script>
    <script src="/static/js/modernizr.js"></script>
    <script>
        function goUrl(pageIndex) {
            $("#searchPageIndex").val(pageIndex);
            $('#searchPageForm').submit();
        }
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="3"/>
</jsp:include>
<article class="blogs">
    <div class="bar">
        <form id="searchForm" action="/web/search" method="post">
            <input type="text" name="keyWord" placeholder="请输入您要搜索的内容..." value="${keyWord}">
            <input type="hidden" name="pageIndex" value="${pageIndex}"/>
            <button type="botton" onclick="$('#searchForm').submit();"></button>
        </form>
    </div>
    <div class="newblog left">
        <c:choose>
            <c:when test="${not empty list}">
                <div class="reslutDiv">关键字&nbsp;<font color="red">${keyWord}</font>&nbsp;搜索相关记录,如下：</div>
                <c:forEach items="${list}" var="essay" varStatus="status">
                    <!-- 循环开始 -->
                    <div class="searchDiv">本文总计出现了<font color="red">${essay.searchtimes}</font>次</div>
                    <h2>
                        <a title="${essay.title}" href="#">
                            <c:if test="${fn:length(essay.title)>10}">
                                ${fn:substring(essay.title,0,10)}...
                            </c:if>
                            <c:if test="${fn:length(essay.title)<=10}">
                                ${essay.title}
                            </c:if>
                        </a>
                    </h2>
                    <p class="dateview">发布时间：${essay.doTime}</p>
                    <figure>
                        <a title="${essay.title}" href="#">
                            <img src="${essay.pictureName}" width="200px" height="200px" alt="${essay.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">
                        </a>
                    </figure>
                    <ul class="nlist">
                        <p>
                            <c:if test="${fn:length(essay.content)>500}">
                                ${fn:substring(essay.content,0,500)}...
                            </c:if>
                            <c:if test="${fn:length(essay.content)<=500}">
                                ${essay.content}
                            </c:if>
                        </p>
                        <a href="/essay/detail/${essay.essayId}" title="${essay.title}" target="_top" class="readmore">阅读全文&gt;&gt;</a>
                    </ul>
                    <c:if test="${!status.last}">
                        <div class="line"></div>
                    </c:if>
                    <!-- 循环结束 -->
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="reslutDiv">关键字&nbsp;<font color="red">${keyWord}</font>&nbsp;未搜索到相关记录..</div>
            </c:otherwise>
        </c:choose>
    </div>
    <form id="searchPageForm" action="/web/search" method="post">
        <input type="hidden" name="keyWord" value="${keyWord}">
        <input type="hidden" name="pageIndex" id="searchPageIndex" value="1"/>
    </form>
    <%
        List<String> pageList = new ArrayList<String>();
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//当前偏移量,从0开始
        int totalCount = Integer.parseInt(session.getAttribute("totalCount").toString());//总记录数
        int pageSize = Integer.parseInt(session.getAttribute("pageSize").toString());//每页显示多少条记录
        int offset = (pageIndex - 1) * pageSize;//当前偏移量,从0开始
        if (offset >= totalCount) {//偏移量大于总记录数
            offset = 0;//显示第一页
        }
        //计算当前页
        int currentPage = ((offset + 1) % pageSize == 0) ? ((offset + 1) / pageSize) : (((offset + 1) / pageSize) + 1);
        // 总页数
        int totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : ((totalCount / pageSize) + 1);
    %>
    <div class="pagnation" id="pagnation">
        <%
            if (totalPage > 0) {
                if (currentPage > 1) {// 当前页大于1
                    pageList.add((currentPage - 1) + "");// 上一页
                } else {
                    pageList.add("back");//不可用
                }
                if (totalPage <= 5) {
                    for (int i = 1; i <= totalPage; i++) {
                        if (i == currentPage) {
                            pageList.add("now");//不可用
                        } else {
                            pageList.add(i + "");
                        }
                    }
                } else {
                    if (currentPage <= totalPage - 3) {// 采取当前-1,当前,当前+1,当前+2,当前+3
                        for (int i = currentPage - 1; i <= currentPage + 3; i++) {
                            if (i == 0) {
                                continue;
                            }
                            if (i == currentPage) {
                                pageList.add("now");
                            } else {
                                pageList.add(i + "");
                            }
                        }
                    } else {// 采取最后一页-4,最后一页-3,最后一页-2,最后一页-1,最后一页
                        for (int i = totalPage - 4; i <= totalPage; i++) {
                            if (i == currentPage) {
                                pageList.add("now");
                            } else {
                                pageList.add(i + "");
                            }
                        }
                    }
                }
                if (currentPage != totalPage) {// 当前页不为最后一页
                    pageList.add((currentPage + 1) + "");// 下一页
                } else {
                    pageList.add("next");
                }
                int i = 1;
                for (String s : pageList) {
                    if (s.equals("back")) {
        %>
        <a href="javascript:void(0);" class="page-prev"></a>
        <%
        } else if (i == 1) {
        %>
        <a href="javascript:void(0);" onclick="goUrl('<%=currentPage-1%>')" class="page-prev"></a>
        <%
        } else if (s.equals("now")) {
        %>
        <a href="javascript:void(0);" class="current"><%=currentPage%>
        </a>
        <%
        } else if (s.equals("next")) {
        %>
        <a href="javascript:void(0);" class="page-next"></a>
        <%
        } else if (i == pageList.size()) {
        %>
        <a href="javascript:void(0);" onclick="goUrl('<%=currentPage+1%>')" class="page-next"></a>
        <%
        } else {
        %>
        <a href="javascript:void(0);" onclick="goUrl('<%=Integer.parseInt(s)%>')"><%=s%>
        </a>
        <%
                    }
                    i++;
                }
            }
        %>
    </div>
</article>
</body>
</html>