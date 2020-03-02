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

	PropLoad pl = new PropLoad(securityKey, type);
	LoginManager logm = new LoginManager(pl);
	
	
	if (pl.getType().equals("adminLogin")) {
		
		String returns = logm.adminLoginCheck(id);		
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (pl.getType().equals("login")) {
		String returns = logm.loginCheck(id, name);
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (pl.getType().equals("join")) {
		String returns = logm.joinCheck(id, name, pwd, mail);
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (pl.getType().equals("find")) {
		String returns = logm.findCheck(id, name, mail);
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (pl.getType().equals("change")) {
		String returns = logm.changeCheck(id, pwd, b_pwd);
		out.clear();
		out.print(returns);
		out.flush();
		
	} else {
		String returns = logm.loginError();
		out.clear();
		out.print(returns);
		out.flush();
	}
%>