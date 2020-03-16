<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String callback = request.getParameter("callback");
	out.print(callback + "({\"msg\" : \"jsp页面响应的数据\"});");
%>