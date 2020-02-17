<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		String b_title = request.getParameter("b_title");		
		String text = request.getParameter("text");
		String date = request.getParameter("date");		
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		
		Schedule sche = Schedule.getInstance();
		
		if(type.equals("scheduleList")) {
			String returns = sche.scheduleList(date);
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("scheduleAdd")) {	
			String returns = sche.scheduleAdd(title, text, date);	
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("scheduleShow")) {
			String returns = sche.scheduleShow(title, date);	
			out.clear();
			out.print(returns);
			out.flush();
		}
		
		else if(type.equals("scheduleDelete")) {
			String returns = sche.scheduleDelete(title, date);	
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("scheduleModify")) {
			String returns = sche.scheduleModify(b_title, title, text, date);	
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