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
	String mail = request.getParameter("mail");
	String b_name = request.getParameter("b_name");
	String b_id = request.getParameter("b_id");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키

	//Cross-site Script Check
	XSS xss = new XSS();
	if (name != null) {
		name = xss.prevention(name);
	} else {
		name = "";
	}
	if (id != null) {
		id = xss.prevention(id);
	} else {
		id = "";
	}
	if (pwd != null) {
		pwd = xss.prevention(pwd);
	} else {
		pwd = "";
	}
	if (mail != null) {
		mail = xss.prevention(mail);
	} else {
		mail = "";
	}
	if (b_name != null) {
		b_name = xss.prevention(b_name);
	} else {
		b_name = "";
	}
	if (b_id != null) {
		b_id = xss.prevention(b_id);
	} else {
		b_id = "";
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
	UserManager user = new UserManager(pl, name, id, pwd, mail, b_name, b_id);

	String returns = user.getResult();
	out.clear();
	out.print(returns);
	out.flush();
%>