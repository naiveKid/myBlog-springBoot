<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!-- isErrorPage="true"才可以使用这个隐藏对象exception，它可以获得当前的错误信息 -->
<!-- 此处不需要输出错误信息 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/static/css/404.css" />
    <script type="text/javascript" src="/static/js/404.js"></script>
    <title>访问的页面不存在</title>
</head>
<body>
<center>
    <img src="/static/images/404.jpg" alt="404" width="480px" height="200px"></img>
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td>
                <ul class="text">
                    <li>请检查该地址是否输入错误，比如将"www.example.com"错写成"ww.example.com"</li>
                    <li>如果您无法载入任何页面，请检查您计算机的网络连接。</li>
                    <li>如果您的计算机或网络受到防火墙或者代理服务器的保护，请确认浏览器已被授权访问网络。</li>
                </ul>
            </td>
        </tr>
    </table>
    <p>
        <a href="/">◂返回首页</a> <a
            href="#" onclick="goBack()">◂返回上一页</a>
    </p>
</center>
</body>
</html>