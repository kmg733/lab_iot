<%@ page import="java.util.Enumeration"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="DBConnect.*"%>
<%@ page import="java.util.Enumeration"%>
<%
	request.setCharacterEncoding("UTF-8");
	String imgName = request.getParameter(request.getParameter("imgName"));
	String imgFile = request.getParameter("imgFile");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	
	ImageUpload imgup = new ImageUpload();
	//https://riucc.tistory.com/403	파일 업로드 참고하기
	String savePath = request.getRealPath("WebContent\\WEB-INF\\upload\\");
	
	if (type.equals("orgShow")) {
		String returns = imgup.orgShow();
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("orgUpload")) {
		String returns = imgup.orgUpload(imgName, savePath, imgFile);		
		out.clear();
		out.print(returns);
		out.flush();
	}  else if(type.equals("strShow")){
		String returns = imgup.strShow();
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("strUpload")) {
		String returns = imgup.strUpload(imgName, savePath, imgFile);
		out.clear();
		out.print(returns);
		out.flush();
	}  else if(type.equals("ipShow")){
		String returns = imgup.ipShow();
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("ipUpload")) {
		String returns = imgup.ipUpload(imgName, savePath, imgFile);
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