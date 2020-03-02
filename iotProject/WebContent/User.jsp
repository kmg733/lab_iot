<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="util.DataRSA"%>
<%@	page import="util.DataAES"%>
<%@ page import="DBConnect.*"%>
<%@ page import="jspConnect.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String mail = request.getParameter("mail");
	String b_name = request.getParameter("b_name");
	String b_id = request.getParameter("b_id");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키

	PropLoad pl = new PropLoad(securityKey, type);
	UserManager user = new UserManager(pl);

	
	
	//Cross-site Script Check
	if(name != null) {
		XSS xss = new XSS();
		name = xss.prevention(name);			
	} else {
		name = "";
	}	
	if(id != null) {
		XSS xss = new XSS();
		id = xss.prevention(id);			
	} else {
		id = "";
	}	
	if(pwd != null) {
		XSS xss = new XSS();
		pwd = xss.prevention(pwd);			
	} else {
		pwd = "";
	}	
	if(mail != null) {
		XSS xss = new XSS();
		mail = xss.prevention(mail);			
	} else {
		mail = "";
	}	
	if(b_name != null) {
		XSS xss = new XSS();
		b_name = xss.prevention(b_name);			
	} else {
		b_name = "";
	}	
	if(b_id != null) {
		XSS xss = new XSS();
		b_id = xss.prevention(b_id);			
	} else {
		b_id = "";
	}	
	if(type != null) {
		XSS xss = new XSS();
		type = xss.prevention(type);
	} else {
		type = "";
	}
	
	
	if (type.equals("addUser_List")) {		
		String returns = user.addUser_ListCheck();
		
		out.clear();
		out.print(returns);
		out.flush();
	
	} else if (type.equals("user_List")) {
		String returns = user.user_ListCheck(name, id);
		
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (type.equals("addUser_Add")) {
		String returns = user.addUser_AddCheck(name, id);
		
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (type.equals("addUser_Delete")) {
		String returns = user.addUser_DeleteCheck(name, id);
		
		out.clear();
		out.print(returns);
		out.flush();
		
	} 
	/*
		else if (type.equals("user_Modify")) {	//add_user와 user 수정을 할 때 모두 이 메소드를 이용
		String returns = user.user_Modify(b_name, b_id, name, id, pwd, mail); //수정전 id,name - 수정후id,name,pwd
		out.clear();
		out.print(returns);
		out.flush();
		}*/
	else {
		String returns = user.userError();
		
		out.clear();
		out.print(returns);
		out.flush();
	}
%>