<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		
		Rule rule = Rule.getInstance();
		
		if(type.equals("ruleShow")) {	
			String returns = rule.ruleShow();
			out.clear();
			out.print(returns);
			out.flush();
		}		
		else if(type.equals("ruleAdd")) {	//	수정도 가능
			String returns = rule.ruleAdd(text);
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