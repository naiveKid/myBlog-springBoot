<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/WEB-INF/jsp/admin/include/header.jsp" />
<title>Photo</title>
<script type="text/javascript">
	function alterCheck() {
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
		document.forms["alterPhotoForm"].submit();
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
	<jsp:include page="/WEB-INF/jsp/admin/include/top.jsp" />
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<jsp:include page="/WEB-INF/jsp/admin/include/left.jsp">
			<jsp:param name="type" value="4" />
		</jsp:include>
		<aside id="rightMenu" class="right-side">
            <section class="content">
                <form id="alterPhotoForm" name="alterPhotoForm" action="/picture/alter" method="post">
                    <table class="bordered">
                        <tr>
                            <td colspan="2" height="40"><strong style="font-size: 20px;">修改相片信息</strong></td>
                        </tr>
                        <tr>
                            <td height="30" style="font-size: 18px;">文件名：</td>
                            <td height="30">
                                <img style="width: 25%;height: revert;" src="${picture.pictureName}" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null">
                                <input type="hidden" name="pictureId" id="pictureId" value="${picture.pictureId}">
                                <input type="hidden" name="pictureName" id="pictureName" value="${picture.pictureName}">
                                <input type="hidden" name="pictureType" id="pictureType" value="${picture.pictureType}">
                            </td>
                        </tr>
                        <tr>
				    	    <td height="30" style="font-size: 18px;">相片标题</td>
                            <td height="30" style="font-size: 18px;">
                                <input type="text" style="width:90%;height:24px;" name="pictureTitle" id="pictureTitle" value="${picture.pictureTitle}">
                            </td>
                        </tr>
                        <tr>
                            <td height="30" style="font-size: 18px;">相片描述</td>
                            <td height="30" style="font-size: 18px;"><input type="text" style="width:90%;height:24px;" name="pictureContent" id="pictureContent" value="${picture.pictureContent}"></td>
                        </tr>
                    </table>
                    <br/> <br/> <br/>
                    <center>
                        <input type="button" class="mybtn" value="确定" onclick="alterCheck();" />
                        <input type="button" class="blue" value="返回" onclick="goBack();" />
                    </center>
                </form>
            </section>
        </aside>
	</div>
</body>
</html>