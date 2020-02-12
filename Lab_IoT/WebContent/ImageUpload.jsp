<%@page import="java.util.Enumeration"%>
<%@page import="sun.font.Script"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="DBConnect.*"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%
	request.setCharacterEncoding("UTF-8");
	int number = Integer.parseInt(request.getParameter("number"));
	//String fileName = request.getParameter("fileName");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	
	HttpServletRequest req = request;
	
	//https://riucc.tistory.com/403	파일 업로드 참고하기
	String savePath = request.getRealPath("WebContent\\WEB-INF\\upload\\");

	ImageUpload imgup = ImageUpload.getInstance();
	imgup.setReq(request);
	
	if (type.equals("orgShow")) {
		String returns = imgup.orgShow(number);
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("orgUpload")) {
		String returns = imgup.orgUpload(number, savePath);		
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("orgDelete")) {
		String returns = imgup.orgDelete(number);
		out.clear();
		out.print(returns);
		out.flush();
	} else if(type.equals("strShow")){
		String returns = imgup.strShow(number);
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("strUpload")) {
		String returns = imgup.strUpload(number, savePath);
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("strDelete")) {
		String returns = imgup.strDelete(number);
		out.clear();
		out.print(returns);
		out.flush();
	} else if(type.equals("ipShow")){
		String returns = imgup.ipShow(number);
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("ipUpload")) {
		String returns = imgup.ipUpload(number, savePath);
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("ipDelete")) {
		String returns = imgup.ipDelete(number);
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