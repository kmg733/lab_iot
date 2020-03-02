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
		
		PropLoad pl = new PropLoad(securityKey, type);
		ScheduleManager sche = new ScheduleManager(pl);

		
		
		if(type.equals("scheduleList")) {
			String returns = sche.scheduleListCheck(date);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("scheduleAdd")) {	
			String returns = sche.scheduleAddCheck(title, text, date);
			
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("scheduleShow")) {
			String returns = sche.scheduleShowCheck(title, date);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		
		else if(type.equals("scheduleDelete")) {
			String returns = sche.scheduleDeleteCheck(title, date);
			
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("scheduleModify")) {
			String returns = sche.scheduleModifyCheck(b_title, title, text, date);
			
			out.clear();
			out.print(returns);
			out.flush();
		}

		else {
			String returns = sche.scheduleError();
			
			out.clear();
			out.print(returns);
			out.flush();
		}
%>