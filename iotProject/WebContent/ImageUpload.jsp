<%@ page import="java.util.Enumeration"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="DBConnect.*"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.io.FileInputStream" %>
<%
	request.setCharacterEncoding("UTF-8");
	String imgFile = request.getParameter("imgFile");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	
	ImageUpload imgup = new ImageUpload();

	//참고 : https://m.blog.naver.com/PostView.nhn?blogId=haengro&logNo=220663411505&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F	
	//실제경로 구하기  
	String savePath = this.getServletContext().getRealPath("/")+ "WEB-INF\\upload\\";
	
	if (type.equals("orgShow")) {
		String returns = imgup.orgShow();
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("orgUpload")) {
		String returns = imgup.orgUpload(savePath, imgFile);		
		out.clear();
		out.print(returns);
		out.flush();
	}  else if(type.equals("strShow")){
		String returns = imgup.strShow();
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("strUpload")) {
		String returns = imgup.strUpload(savePath, imgFile);
		out.clear();
		out.print(returns);
		out.flush();
	}  else if(type.equals("ipShow")){
		String returns = imgup.ipShow();
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("ipUpload")) {
		String returns = imgup.ipUpload(savePath, imgFile);
		out.clear();
		out.print(returns);
		out.flush();
	} else {
		String returns = "error/nonTypeRequest";
		out.clear();
		out.print(returns);
		out.flush();
	} 
	
%>