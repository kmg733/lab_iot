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
		
		PropLoad pl = new PropLoad(securityKey, type);
		ConferenceManager cfm = new ConferenceManager(pl);
		
		if(pl.getType().equals("cfShow")) {	
			String returns = cfm.cfShowCheck(date);
			out.clear();
			out.print(returns);
			out.flush();
		}		
		else if(pl.getType().equals("cfAdd")) {	//	수정도 가능
			String returns = cfm.cfAddCheck(date, text);
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else {
			String returns = cfm.cfError();
			out.clear();
			out.print(returns);
			out.flush();
		}
%>