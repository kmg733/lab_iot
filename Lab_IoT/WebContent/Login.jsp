<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String b_pwd = request.getParameter("b_pwd");
		String mail = request.getParameter("mail");
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수
		
		Login lin = Login.getInstance();
		
		if(type.equals("adminLogin")) {
			String returns = lin.adminLogin(id);
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("login")) {
			String returns = lin.loginDB(name, id);
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("join")) {
			String returns = lin.createAccount(name, id, pwd, mail);
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("find")) {
			String returns = lin.findPW(name, id, mail);
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("change")) {
			String returns = lin.changePW(id, pwd, b_pwd);
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