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
		String title = request.getParameter("title");
		String b_title = request.getParameter("b_title");		
		String text = request.getParameter("text");
		String date = request.getParameter("date");		
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수		
		String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키
		
		//Cross-site Script Check
		XSS xss = new XSS();
		if (title != null) {
			title = xss.prevention(title);
		} else {
			title = "";
		}
		if (b_title != null) {
			b_title = xss.prevention(b_title);
		} else {
			b_title = "";
		}
		if (text != null) {
			text = xss.prevention(text);
		} else {
			text = "";
		}
		if (date != null) {
			date = xss.prevention(date);
		} else {
			date = "";
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
		ScheduleManager sche = new ScheduleManager(pl, title, b_title, text, date);

		
		String returns = sche.getResult();
		out.clear();
		out.print(returns);
		out.flush();

%>