<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Exception</title>
</head>
<body>
<% Exception ex = (Exception) request.getAttribute("ex"); %>
<H2>Exception: <%=ex.getMessage()%></H2>
<P><% ex.printStackTrace(new java.io.PrintWriter(out)); %></P>
</body>
</html>