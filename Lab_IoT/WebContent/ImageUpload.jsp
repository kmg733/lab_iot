<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		int number = Integer.parseInt(request.getParameter("number"));
		String fileName = request.getParameter("fileName");
		String type = request.getParameter("type");	//	사용자구 무슨요청을 했는지 구분하는 변수
		
		ImageUpload imgup = ImageUpload.getInstance();
		
		if(type.equals("orgUpload")) {
			String returns = imgup.orgUpload(number, fileName);
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("orgDelete")) {
			String returns = imgup.orgDelete(number);
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("strUpload")) {
			String returns = imgup.strUpload(number, fileName);
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("strDelete")) {
			String returns = imgup.strDelete(number);
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("ipUpload")) {
			String returns = imgup.ipUpload(number, fileName);
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("ipDelete")) {
			String returns = imgup.ipDelete(number);
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