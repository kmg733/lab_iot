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

	if(name.equals(null)){
		name = "";
	}
	if(id.equals(null)){
		id = "";
	}
	if(pwd.equals(null)){
		pwd = "";
	}
	if(mail.equals(null)){
		mail = "";
	}
	if(b_name.equals(null)){
		b_name = "";
	}
	if(b_id.equals(null)){
		b_id = "";
	}
	
	
	PropLoad pl = new PropLoad(securityKey, type);
	UserManager user = new UserManager(pl, name, id, pwd, mail, b_name, b_id);
	
	
	String returns = user.getResult();
	out.clear();
	out.print(returns);
	out.flush();
%>