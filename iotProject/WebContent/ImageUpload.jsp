<%@ page import="java.util.Enumeration"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="DBConnect.*"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="jspConnect.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	String imgFile = request.getParameter("imgFile");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키

	//Cross-site Script Check
	XSS xss = new XSS();
	if(imgFile != null) {
		imgFile = xss.prevention(imgFile);			
	} else {
		imgFile = "";
	}	
	if(type != null) {
		type = xss.prevention(type);			
	} else {
		type = "";
	}	
	if(securityKey != null) {
		securityKey = xss.prevention(securityKey);			
	} else {
		securityKey = "";
	}
	
	PropLoad pl = new PropLoad(securityKey, type);
	ImageUploadManager imgup = new ImageUploadManager(pl, imgFile);
	
	
	String returns = imgup.getResult();
	out.clear();
	out.print(returns);
	out.flush();
	
%>