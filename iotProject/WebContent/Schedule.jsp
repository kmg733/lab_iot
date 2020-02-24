<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="util.DataRSA"%>
<%@	page import="util.DataAES"%>
<%@ page import="DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		String b_title = request.getParameter("b_title");		
		String text = request.getParameter("text");
		String date = request.getParameter("date");		
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수		
		String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키
		
		Schedule sche = Schedule.getInstance();
		
		
		
		String propFile = "C:/Users/securityLab_5/eclipse-workspace/IoT/src/util/PRIVATEkey.properties";
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(propFile);
		props.load(new java.io.BufferedInputStream(fis));

		String rsaPrivatekey = props.getProperty("key");

		if (rsaPrivatekey != null) {
			securityKey = DataRSA.rsaDecryption(securityKey, rsaPrivatekey);
			type = DataAES.aesDecryption(type, securityKey);
		}
		
		
		if(type.equals("scheduleList")) {
			date = DataAES.aesDecryption(date, securityKey);
			
			String returns = sche.scheduleList(date);
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("scheduleAdd")) {	
			title = DataAES.aesDecryption(title, securityKey);
			text = DataAES.aesDecryption(text, securityKey);
			date = DataAES.aesDecryption(date, securityKey);			
			
			String returns = sche.scheduleAdd(title, text, date);
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("scheduleShow")) {
			title = DataAES.aesDecryption(title, securityKey);
			date = DataAES.aesDecryption(date, securityKey);
			
			String returns = sche.scheduleShow(title, date);
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		
		else if(type.equals("scheduleDelete")) {
			title = DataAES.aesDecryption(title, securityKey);
			date = DataAES.aesDecryption(date, securityKey);
			
			String returns = sche.scheduleDelete(title, date);	
			returns = DataAES.aesEncryption(returns, securityKey);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("scheduleModify")) {
			b_title = DataAES.aesDecryption(b_title, securityKey);
			title = DataAES.aesDecryption(title, securityKey);
			text = DataAES.aesDecryption(text, securityKey);	
			date = DataAES.aesDecryption(date, securityKey);
			
			String returns = sche.scheduleModify(b_title, title, text, date);	
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