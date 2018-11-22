<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="com.base.pojo.Page" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%
    List<String> pageList = new ArrayList<String>();
    int offset = Page.getOffset();//当前偏移量,从0开始
    int totalCount = Page.getTotalCount();//总记录数
    int pageSize = Page.getPageSize();//每页显示多少条记录
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
    <a href="${param.link}?offset=<%=offset-pageSize%>" class="page-prev"></a>
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
    <a href="${param.link}?offset=<%=offset+pageSize%>" class="page-next"></a>
    <%
    } else {
    %>
    <a href="${param.link}?offset=<%=pageSize*(Integer.parseInt(s)-1)%>"><%=s%>
    </a>
    <%
                }
                i++;
            }
        }
    %>
</div>