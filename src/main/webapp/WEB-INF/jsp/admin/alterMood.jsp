<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <link rel="stylesheet" type="text/css" href="/static/css/webuploader.css">
    <script type="text/javascript" src="/static/js/webuploader.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/js/UEditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/js/UEditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/static/js/UEditor/lang/zh-cn/zh-cn.js"></script>
    <title>mood</title>
    <script type="text/javascript">
        function alterCheck() {
            var pictureName = document.getElementById("pictureName").value;
            if (pictureName == "") {
                layer.alert("文件名不能为空!", {icon: 2});
                document.getElementById("pictureName").focus();
                return false;
            }
            getContent();
            // 提交表单
            document.forms["alterMoodForm"].submit();
        }

        function goBack() {
            layer.confirm('确定放弃？',{icon: 3, title:'提示'}, function(index){
                window.location.href = "/mood/manage";
            });
        }
    </script>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="2"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <form id="alterMoodForm" name="alterMoodForm" action="/mood/alter" method="post">
                <table class="bordered">
                    <tr>
                        <td colspan="2" height="40"><strong style="font-size: 20px;">修改心情信息</strong></td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">心情配图</td>
                        <td height="30" style="font-size: 18px;">
                            <div id="uploader">
                                <!-- 选择文件区域 -->
                                <div id="filePicker" style="width: 40%;float: left;text-align: right;">选择文件</div>
                                <!-- 显示文件列表信息 -->
                                <ul id="fileList" style="float: left;"><img style="width: 50px; height:50px;" src="${mood.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"></ul>
                            </div>
                            <input type="hidden" name="moodId" id="moodId" value="${mood.moodId}">
                            <input type="hidden" name="pictureName" id="pictureName" value="${mood.pictureName}">
                            <input type="hidden" name="pictureId" id="pictureId" value="${mood.pictureId}">
                        </td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">心情内容</td>
                        <td height="30" style="font-size: 18px;">
                            <script id="editor" type="text/plain" style="width:100%;height:200px;">${mood.content}</script>
                            <input id="content" name="content" type="hidden" value="">
                            <input id="text" name="text" type="hidden" value="">
                        </td>
                    </tr>
                </table>
                <br/> <br/> <br/>
                <center>
                    <input type="button" class="mybtn" value="确定" onclick="alterCheck();"/>
                    <input type="button" class="blue" value="返回" onclick="goBack();"/>
                </center>
            </form>
        </section>
    </aside>
</div>
<jsp:include page="./include/uploader.jsp"></jsp:include>
<jsp:include page="./include/UEeditor.jsp"></jsp:include>
</body>
</html>