<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="util.DataRSA"%>
<%@	page import="util.DataAES"%>
<%@ page import="DBConnect.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String b_pwd = request.getParameter("b_pwd");
	String mail = request.getParameter("mail");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키

	Login lin = Login.getInstance();

	String propFile = "C:/Users/securityLab_5/eclipse-workspace/IoT/src/util/PRIVATEkey.properties";
	Properties props = new Properties();
	FileInputStream fis = new FileInputStream(propFile);
	props.load(new java.io.BufferedInputStream(fis));

	String rsaPrivatekey = props.getProperty("key");

	if (rsaPrivatekey != null) {
		securityKey = DataRSA.rsaDecryption(securityKey, rsaPrivatekey);
		type = DataAES.aesDecryption(type, securityKey);
	}

	
	
	if (type.equals("adminLogin")) {
		id = DataAES.aesDecryption(id, securityKey);

		String returns = lin.adminLogin(id);
		returns = DataAES.aesEncryption(returns, securityKey);
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (type.equals("login")) {
		id = DataAES.aesDecryption(id, securityKey);
		name = DataAES.aesDecryption(name, securityKey);

		String returns = lin.loginDB(name, id);
		returns = DataAES.aesEncryption(returns, securityKey);
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (type.equals("join")) {
		id = DataAES.aesDecryption(id, securityKey);
		name = DataAES.aesDecryption(name, securityKey);
		pwd = DataAES.aesDecryption(pwd, securityKey);
		mail = DataAES.aesDecryption(mail, securityKey);

		String returns = lin.createAccount(name, id, pwd, mail);
		returns = DataAES.aesEncryption(returns, securityKey);
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (type.equals("find")) {
		id = DataAES.aesDecryption(id, securityKey);
		name = DataAES.aesDecryption(name, securityKey);
		mail = DataAES.aesDecryption(mail, securityKey);

		String returns = lin.findPW(name, id, mail);
		returns = DataAES.aesEncryption(returns, securityKey);
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (type.equals("change")) {
		id = DataAES.aesDecryption(id, securityKey);
		pwd = DataAES.aesDecryption(pwd, securityKey);
		b_pwd = DataAES.aesDecryption(b_pwd, securityKey);

		String returns = lin.changePW(id, pwd, b_pwd);
		returns = DataAES.aesEncryption(returns, securityKey);
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