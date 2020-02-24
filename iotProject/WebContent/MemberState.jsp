<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="util.DataRSA"%>
<%@	page import="util.DataAES"%>    
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String dept = request.getParameter("dept");		//	과정
		String team = request.getParameter("team");		//	소속
		String beforeName = request.getParameter("b_name");	
		String beforePhone = request.getParameter("b_phone");	
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키
		
		MemberState memState = MemberState.getInstance();
		
		
		
		String propFile = "C:/Users/securityLab_5/eclipse-workspace/IoT/src/util/PRIVATEkey.properties";
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(propFile);
		props.load(new java.io.BufferedInputStream(fis));

		String rsaPrivatekey = props.getProperty("key");

		if (rsaPrivatekey != null) {
			securityKey = DataRSA.rsaDecryption(securityKey, rsaPrivatekey);
			type = DataAES.aesDecryption(type, securityKey);
		}
		
		
		if(type.equals("memShow")) {
			String returns = memState.memShow();
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("memAdd")) {	
			name = DataAES.aesDecryption(name, securityKey);
			phone = DataAES.aesDecryption(phone, securityKey);
			dept = DataAES.aesDecryption(dept, securityKey);
			team = DataAES.aesDecryption(team, securityKey);
			
			String returns = memState.memAdd(name, phone, dept, team);	
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("memModify")) {
			beforeName = DataAES.aesDecryption(beforeName, securityKey);
			beforePhone = DataAES.aesDecryption(beforePhone, securityKey);
			name = DataAES.aesDecryption(name, securityKey);
			phone = DataAES.aesDecryption(phone, securityKey);
			dept = DataAES.aesDecryption(dept, securityKey);
			team = DataAES.aesDecryption(team, securityKey);
			
			String returns = memState.memModify(beforeName, beforePhone, name, phone, dept, team);	
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("memDelete")) {
			name = DataAES.aesDecryption(name, securityKey);
			phone = DataAES.aesDecryption(phone, securityKey);
			dept = DataAES.aesDecryption(dept, securityKey);
			team = DataAES.aesDecryption(team, securityKey);
			
			String returns = memState.memDelete(name, phone, dept, team);	
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		else {
			String returns = "error/nonTypeRequest";
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
%>