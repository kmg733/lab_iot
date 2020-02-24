<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="util.DataRSA"%>
<%@	page import="util.DataAES"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String date = request.getParameter("date");
		String text = request.getParameter("text");
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키
		
		Conference conf = Conference.getInstance();
		
		
		
		String propFile = "C:/Users/securityLab_5/eclipse-workspace/IoT/src/util/PRIVATEkey.properties";
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(propFile);
		props.load(new java.io.BufferedInputStream(fis));

		String rsaPrivatekey = props.getProperty("key");

		if (rsaPrivatekey != null) {
			securityKey = DataRSA.rsaDecryption(securityKey, rsaPrivatekey);
			type = DataAES.aesDecryption(type, securityKey);
		}
		
		if(type.equals("cfShow")) {	
			date = DataAES.aesDecryption(date, securityKey);
			
			String returns = conf.cfShow(date);
			returns = DataAES.aesEncryption(returns, securityKey);
			out.clear();
			out.print(returns);
			out.flush();
		}		
		else if(type.equals("cfAdd")) {	//	수정도 가능
			date = DataAES.aesDecryption(date, securityKey);
			text = DataAES.aesDecryption(text, securityKey);
			
			String returns = conf.cfAdd(date, text);
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