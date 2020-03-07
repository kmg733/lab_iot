<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="util.DataRSA"%>
<%@	page import="util.DataAES"%>
<%@ page import="DBConnect.*"%>
<%@ page import="jspConnect.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String b_pwd = request.getParameter("b_pwd");
	String mail = request.getParameter("mail");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키

	if(name.equals(null)){
		name = "";
	}
	if(id.equals(null)){
		id = "";
	}
	if(pwd.equals(null)){
		pwd = "";
	}
	if(b_pwd.equals(null)){
		b_pwd = "";
	}
	if(mail.equals(null)){
		mail = "";
	}
	
	
	PropLoad pl = new PropLoad(securityKey, type);
	LoginManager logm = new LoginManager(pl, name, id, pwd, b_pwd, mail);
		
	
	String returns = logm.getResult();
	out.clear();
	out.print(returns);
	out.flush();
		
	
%>