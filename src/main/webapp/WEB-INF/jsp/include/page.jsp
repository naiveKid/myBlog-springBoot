<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="com.base.pojo.Page" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<script type="text/javascript" src="/static/js/page.js"></script>
<%
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
<div id="pagination" class="pagination"></div>
<script>
    window.onload = function () {
        new Page({
            id: 'pagination',
            pageTotal: <%=totalPage%>, //必填,总页数
            pageAmount: <%=pageSize%>,  //每页多少条
            dataTotal: <%=totalCount%>, //总共多少条数据
            curPage:<%=currentPage%>, //当前页码,不填默认为1
            pageSize: <%=pageSize%>, //分页个数,不填默认为5
            showPageTotalFlag:true, //是否显示数据统计,不填默认不显示
            showSkipInputFlag:true, //是否支持跳转,不填默认不显示
            getPage: function (page) {
                if('${param.link}'.indexOf("?")!=-1){
                    window.location.href="${param.link}&offset="+(page-1)*<%=pageSize%>;
                }else {
                    window.location.href="${param.link}?offset="+(page-1)*<%=pageSize%>;
                }
            }
        })
    }
</script>