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
		String text = request.getParameter("text");
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키
		
		PropLoad pl = new PropLoad(securityKey, type);
		RuleManager rule = new RuleManager(pl);
		
		//Cross-site Script Check
				if(text != null) {
					XSS xss = new XSS();
					text = xss.prevention(text);			
				} else {
					text = "";
				}	
				if(type != null) {
					XSS xss = new XSS();
					type = xss.prevention(type);
				} else {
					type = "";
				}
		
		if(type.equals("ruleShow")) {	
			String returns = rule.ruleShowCheck();
			
			out.clear();
			out.print(returns);
			out.flush();
		}		
		else if(type.equals("ruleAdd")) {	//	수정도 가능
			String returns = rule.ruleAddCheck(text);
			
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else {
			String returns = rule.ruleError();
			
			out.clear();
			out.print(returns);
			out.flush();
		}
%>