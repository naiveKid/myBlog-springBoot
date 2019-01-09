<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/admin/include/header.jsp"/>
    <link rel="stylesheet" type="text/css" href="/static/css/webuploader.css">
    <script type="text/javascript" src="/static/js/webuploader.js"></script>
    <!-- ckeditor5 -->
    <link type="text/css" href="/static/js/ckeditor5/sample/css/sample.css" rel="stylesheet"/>
    <title>aboutMe</title>
    <script type="text/javascript">
        function alterCheck() {
            var realName = document.getElementById("realName").value;
            if (realName == "") {
                layer.alert('真实姓名不能为空!', {icon: 2});
                document.getElementById("realName").focus();
                return false;
            }
            var address = document.getElementById("address").value;
            if (address == "") {
                layer.alert('籍贯不能为空!', {icon: 2});
                document.getElementById("address").focus();
                return false;
            }
            var nowAddress = document.getElementById("nowAddress").value;
            if (nowAddress == "") {
                layer.alert('现居地不能为空!', {icon: 2});
                document.getElementById("nowAddress").focus();
                return false;
            }
            var likeBook = document.getElementById("likeBook").value;
            if (likeBook == "") {
                layer.alert('喜欢书籍不能为空!', {icon: 2});
                document.getElementById("likeBook").focus();
                return false;
            }
            var likeMusic = document.getElementById("likeMusic").value;
            if (likeMusic == "") {
                layer.alert('喜欢音乐不能为空!', {icon: 2});
                document.getElementById("likeMusic").focus();
                return false;
            }
            var pictureId = document.getElementById("pictureId").value;
            if (pictureId == "") {
                layer.alert('文件名不能为空!', {icon: 2});
                return false;
            }
            var title = document.getElementById("title").value;
            if (title == "") {
                layer.alert('文章标题不能为空!', {icon: 2});
                document.getElementById("title").focus();
                return false;
            }
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
                    <td height="40" align="center"><strong style="font-size: 20px;">个人信息</strong></td>
                </tr>
            </table>
            <form name="alterAboutMeForm" id="alterAboutMeForm" action="/about/alter" method="post">
                <input type="hidden" name="aboutMeId" value="${aboutMe.aboutMeId}">
                <input type="hidden" name="essayId" value="${aboutMe.essayId}">
                <input type="hidden" name="pictureName" id="pictureName" value="${aboutMe.pictureName}">
                <input type="hidden" name="pictureId" id="pictureId" value="${aboutMe.pictureId}">
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>真实姓名</th>
                        <th>籍贯</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td width="50%"><input id="realName" name="realName" class="layui-input" type="text" value="${aboutMe.realName}"></td>
                        <td width="50%"><input id="address" name="address" class="layui-input" type="text" value="${aboutMe.address}"></td>
                    </tr>
                    </tbody>
                </table>
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>现居地</th>
                        <th>喜爱书籍</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td width="50%"><input id="nowAddress" name="nowAddress" class="layui-input" type="text" value="${aboutMe.nowAddress}"></td>
                        <td width="50%"><input id="likeBook" name="likeBook" class="layui-input" type="text" value="${aboutMe.likeBook}"></td>
                    </tr>
                    </tbody>
                </table>
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>喜爱音乐</th>
                        <th>配图</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td width="50%">
                            <input id="likeMusic" name="likeMusic" class="layui-input" type="text" value="${aboutMe.likeMusic}">
                        </td>
                        <td width="50%">
                            <div id="uploader">
                                <!-- 选择文件区域 -->
                                <div id="filePicker" style="float: left;"><i class="layui-icon">&#xe67c;</i>上传图片</div>
                                <!-- 显示文件列表信息 -->
                                <ul id="fileList" style="float: left;margin-left:10px;">
                                    <c:if test="${not empty aboutMe.pictureName}">
                                        <img style="width: 50px; height:50px;" src="${aboutMe.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">
                                    </c:if>
                                </ul>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <table class="layui-table">
                    <tr>
                        <td>文章标题：</td>
                        <td align="center">
                            <textarea rows="5" style="width:100%;box-sizing:border-box; -webkit-box-sizing:border-box;-moz-box-sizing:border-box;-o-box-sizing:border-box;resize:none" id="title" name="title" class="layui-textarea">${essay.title}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>文章内容：</td>
                        <td>
                            <textarea rows="5" style="width:100%;box-sizing:border-box; -webkit-box-sizing:border-box;-moz-box-sizing:border-box;-o-box-sizing:border-box;resize:none" id="editor" name="content" class="layui-textarea">${essay.content}</textarea>
                        </td>
                    </tr>
                </table>
                <br/>
                <center>
                    <input type="button" class="layui-btn" onclick="alterCheck();" value="更 新">
                </center>
            </form>
        </section>
    </aside>
</div>
<jsp:include page="./include/uploader.jsp"></jsp:include>
<script src="/static/js/ckeditor5/ckeditor.js"></script>
<script>
    ClassicEditor.create( document.querySelector( '#editor' ), {
    } ).then( editor => {
        window.editor = editor;
    } ).catch( err => {
        console.error( err.stack );
    } );
</script>
</body>
</html>