<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="DBConnect.*"%>
<%@ page import="jspConnect.*" %>
<%
		request.setCharacterEncoding("UTF-8");
		String date = request.getParameter("date");
		String text = request.getParameter("text");
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키

		//Cross-site Script Check
		XSS xss = new XSS();
		if(date != null) {
			date = xss.prevention(date);			
		} else {
			date = "";
		}	
		if(text != null) {
			text = xss.prevention(text);			
		} else {
			text = "";
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
		ConferenceManager cfm = new ConferenceManager(pl, date, text);
		
		String returns = cfm.getResult();
		out.clear();
		out.print(returns);
		out.flush();
		
%>