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
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String dept = request.getParameter("dept");		//	과정
		String team = request.getParameter("team");		//	소속
		String beforeName = request.getParameter("b_name");	
		String beforePhone = request.getParameter("b_phone");	
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키
		
		//Cross-site Script Check
		XSS xss = new XSS();
		if(name != null) {
			name = xss.prevention(name);			
		} else {
			name = "";
		}
		if(phone != null) {
			phone = xss.prevention(phone);			
		} else {
			phone = "";
		}
		if(dept != null) {
			dept = xss.prevention(dept);			
		} else {
			dept = "";
		}
		if(team != null) {
			team = xss.prevention(team);			
		} else {
			team = "";
		}
		if(beforeName != null) {
			beforeName = xss.prevention(beforeName);			
		} else {
			beforeName = "";
		}
		if(beforePhone != null) {
			beforePhone = xss.prevention(beforePhone);			
		} else {
			beforePhone = "";
		}
		
		
		PropLoad pl = new PropLoad(securityKey, type);
		MemberStateManager mem = new MemberStateManager(pl, name, phone, dept, 
				team, beforeName, beforePhone);
		
		
		String returns = mem.getResult();
		out.clear();
		out.print(returns);
		out.flush();
%>