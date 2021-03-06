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
    <title>Photo</title>
    <script type="text/javascript">
        function addCheck() {
            var pictureId = document.getElementById("pictureId").value;
            if (pictureId == "") {
                layer.alert('文件名不能为空!', {icon: 2});
                return false;
            }
            var pictureTitle = document.getElementById("pictureTitle").value;
            if (pictureTitle == "") {
                layer.alert('相片标题不能为空!', {icon: 2});
                document.getElementById("pictureTitle").focus();
                return false;
            }
            var pictureContent = document.getElementById("pictureContent").value;
            if (pictureContent == "") {
                layer.alert('相片描述不能为空!', {icon: 2});
                document.getElementById("pictureContent").focus();
                return false;
            }
            // 提交表单
            document.forms["addPhotoForm"].submit();
        }

        function goBack() {
            layer.confirm('确定放弃？',{icon: 3, title:'提示'}, function(index){
                window.location.href = "/picture/manage";
            });
        }
    </script>
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
            <form id="addPhotoForm" name="addPhotoForm" action="/picture/add" method="post">
                <input type="hidden" name="pictureId" id="pictureId" value="">
                <input type="hidden" name="pictureName" id="pictureName" value="">
                <input type="hidden" name="pictureType" id="pictureType" value="photo">
                <table class="layui-table">
                    <tr>
                        <td colspan="2" height="40"><strong style="font-size: 20px;">添加相片信息</strong></td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">上传相片文件：</td>
                        <td height="40" style="font-size: 18px;">
                            <div id="uploader">
                                <!-- 选择文件区域 -->
                                <div id="filePicker" style="float: left;"><i class="layui-icon">&#xe67c;</i>上传图片</div>
                                <!-- 显示文件列表信息 -->
                                <ul id="fileList" style="float: left;margin-left:10px;"></ul>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">相片标题</td>
                        <td height="30" style="font-size: 18px;">
                            <input type="text" style="width:90%;height:24px;" name="pictureTitle" class="layui-input" id="pictureTitle" value="">
                        </td>
                    </tr>
                    <tr>
                        <td height="30" style="font-size: 18px;">相片描述</td>
                        <td height="30" style="font-size: 18px;">
                            <input type="text" style="width:90%;height:24px;" name="pictureContent" class="layui-input" id="pictureContent" value="">
                        </td>
                    </tr>
                </table>
                <br/> <br/> <br/>
                <center>
                    <input type="button" class="layui-btn" value="确定" onclick="addCheck();"/>
                    <input type="button" class="layui-btn layui-btn-danger" value="返回" onclick="goBack();"/>
                </center>
            </form>
        </section>
    </aside>
</div>
<jsp:include page="./include/uploader.jsp"></jsp:include>
<jsp:include page="./include/bottom.jsp"></jsp:include>
</body>
</html>