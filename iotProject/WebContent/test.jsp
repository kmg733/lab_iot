<%@page import="sun.font.Script"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%
		request.setCharacterEncoding("UTF-8");
		int max = 1024*1024*10;	//	파일크기 제한  - 10MB
		String rootPath = application.getRealPath("/");
		String savePath = rootPath+"WebContent\\WEB-INF\\upload\\";
		System.out.println(rootPath);
		System.out.println(savePath);	
		
		String path = request.getRealPath("WebContent\\WEB-INF\\upload\\");
		System.out.println(path);	
				
	
%>