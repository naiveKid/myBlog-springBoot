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
    <script type="text/javascript" charset="utf-8" src="/static/UEditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/UEditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/static/UEditor/lang/zh-cn/zh-cn.js"></script>
    <title>aboutMe</title>
    <script type="text/javascript">
        function alterCheck() {
            var realName = document.getElementById("realName").value;
            if (realName == "") {
                alert("真实姓名不能为空!");
                document.getElementById("realName").focus();
                return false;
            }
            var address = document.getElementById("address").value;
            if (address == "") {
                alert("籍贯不能为空!");
                document.getElementById("address").focus();
                return false;
            }
            var nowAddress = document.getElementById("nowAddress").value;
            if (nowAddress == "") {
                alert("现居地不能为空!");
                document.getElementById("nowAddress").focus();
                return false;
            }
            var likeBook = document.getElementById("likeBook").value;
            if (likeBook == "") {
                alert("喜欢书籍不能为空!");
                document.getElementById("likeBook").focus();
                return false;
            }
            var likeMusic = document.getElementById("likeMusic").value;
            if (likeMusic == "") {
                alert("喜欢音乐不能为空!");
                document.getElementById("likeMusic").focus();
                return false;
            }
            var pictureName = document.getElementById("pictureName").value;
            if (pictureName == "") {
                alert("文件名不能为空!");
                document.getElementById("pictureName").focus();
                return false;
            }
            var title = document.getElementById("title").value;
            if (title == "") {
                alert("文章标题不能为空!");
                document.getElementById("title").focus();
                return false;
            }
            getContent();
            // 提交表单
            document.forms["alterAboutMeForm"].submit();
        }
    </script>
</head>
<body class="skin-black">
<!-- 头部 -->
<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp"/>
<div class="wrapper row-offcanvas row-offcanvas-left">
    <jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
        <jsp:param name="type" value="1"/>
    </jsp:include>
    <aside id="rightMenu" class="right-side">
        <section class="content">
            <table width="80%" align="center">
                <tr>
                    <td height="40" align="center"><strong style="font-size: 20px;">我的个人信息</strong></td>
                </tr>
            </table>
            <form name="alterAboutMeForm" id="alterAboutMeForm" action="/about/alter" method="post">
                <table class="zebra">
                    <thead>
                    <tr>
                        <th>真实姓名</th>
                        <th>籍贯</th>
                        <th>现居地</th>
                        <th>喜爱书籍</th>
                        <th>喜爱音乐</th>
                        <th>配图</th>
                    </tr>
                    </thead>
                    <tr>
                        <td width="10%"><input id="realName" name="realName" type="text" value="${aboutMe.realName}"></td>
                        <td width="20%"><input id="address" name="address" type="text" value="${aboutMe.address}"></td>
                        <td width="20%"><input id="nowAddress" name="nowAddress" type="text" value="${aboutMe.nowAddress}"></td>
                        <td width="15%"><input id="likeBook" name="likeBook" type="text" value="${aboutMe.likeBook}"></td>
                        <td width="15%"><input id="likeMusic" name="likeMusic" type="text" value="${aboutMe.likeMusic}"></td>
                        <td width="20%">
                            <div id="uploader">
                                <!-- 选择文件区域 -->
                                <div id="filePicker" style="width: 35%;float: left;text-align: right;">选择文件</div>
                                <!-- 显示文件列表信息 -->
                                <ul id="fileList" style="width: 65%;float: left;">
                                    <c:if test="${not empty aboutMe.pictureName}">
                                        <img style="width: 50px; height:50px;" src="${aboutMe.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">
                                    </c:if>
                                </ul>
                            </div>
                            <input type="hidden" name="aboutMeId" value="${aboutMe.aboutMeId}">
                            <input type="hidden" name="essayId" value="${aboutMe.essayId}">
                            <input type="hidden" name="pictureName" id="pictureName" value="">
                            <input type="hidden" name="pictureId" id="pictureId" value="">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">文章标题：</td>
                        <td colspan="4" align="center">
                            <textarea rows="5" style="width:100%;box-sizing:border-box; -webkit-box-sizing:border-box;-moz-box-sizing:border-box;-o-box-sizing:border-box;resize:none" id="title" name="title">${essay.title}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">文章内容：</td>
                        <td colspan="4">
                            <script id="editor" type="text/plain" style="width:100%;height:200px;">${essay.content}</script>
                            <input id="content" name="content" type="hidden" value="">
                            <input id="text" name="text" type="hidden" value="">
                        </td>
                    </tr>
                </table>
                <center>
                    <input type="button" class="mybtn" onclick="alterCheck();" value="确定"/>
                </center>
            </form>
        </section>
    </aside>
</div>
<jsp:include page="./include/uploader.jsp"></jsp:include>
<jsp:include page="./include/UEeditor.jsp"></jsp:include>
</body>
</html>