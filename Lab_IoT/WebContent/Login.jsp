<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String beforePwd = request.getParameter("b_pwd");
		String pwd = request.getParameter("pwd");
		String phoneNum = request.getParameter("phone");
		String type = request.getParameter("type");	//	사용자구 무슨요청을 했는지 구분하는 변수
		
		Login lin = Login.getInstance();
		
		if(type.equals("adminLogin")) {
			String returns = lin.adminLogin(name, id, pwd);
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
			String returns = lin.createAccount(name, id, pwd);
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("change")) {
			String returns = lin.changePwd(id, pwd, beforePwd);
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("find")) {
			String returns = lin.findPwd(name, id, phoneNum);
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