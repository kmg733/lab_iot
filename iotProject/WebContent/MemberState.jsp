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
		
		if(name.equals(null)){
			name = "";
		}
		if(phone.equals(null)){
			phone = "";
		}
		if(dept.equals(null)){
			dept = "";
		}
		if(team.equals(null)){
			team = "";
		}
		if(beforeName.equals(null)){
			beforeName = "";
		}
		if(beforePhone.equals(null)){
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