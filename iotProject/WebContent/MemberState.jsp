<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String dept = request.getParameter("dept");		//과정
		String team = request.getParameter("team");		//소속
		String beforeName = request.getParameter("name");	//modify를 할 때 데이터 베이스에서 해당 회원 정보를 찾기위해 수정 전의 정보가 필요
		String beforePhone = request.getParameter("phone");	//modify를 할 때 데이터 베이스에서 해당 회원 정보를 찾기위해 수정 전의 정보가 필요
		String type = request.getParameter("type");	//	사용자구 무슨요청을 했는지 구분하는 변수
		
		MemberState memState = MemberState.getInstance();
		
		if(type.equals("memShow")) {
			String returns = memState.memShow();
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("memAdd")) {	
			String returns = memState.memAdd(name, phone, dept, team);	
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("memModify")) {
			String returns = memState.memModify(beforeName, beforePhone, name, phone, dept, team);	
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("memDelete")) {
			String returns = memState.memDelete(name, phone, dept, team);	
			out.clear();
			out.print(returns);
			out.flush();
		}
		else {
			String returns = "error/nonTypeRequest";
			out.clear();
			out.print(returns);
			out.flush();
		}
%>