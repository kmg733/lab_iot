<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String date = request.getParameter("date");
		String text = request.getParameter("text");
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		
		Conference conf = Conference.getInstance();
		
		if(type.equals("cfShow")) {	
			String returns = conf.cfShow(date);
			out.clear();
			out.print(returns);
			out.flush();
		}		
		else if(type.equals("cfAdd")) {	//	수정도 가능
			String returns = conf.cfAdd(date, text);
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