<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String b_name = request.getParameter("b_name");
		String b_id = request.getParameter("b_id");
		String type = request.getParameter("type");	//	사용자구 무슨요청을 했는지 구분하는 변수
		
		UserInfo user = UserInfo.getInstance();
		
		if(type.equals("userList")) {
			String returns = user.userList();
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("userAdd")) {
			String returns = user.userAdd(name, id);	//추가할 이름, 추가할 아이디
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("userModify")) {
			String returns = user.userModify(b_name, b_id, name, id);	//수정된 이름, 수정된 아이디
			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("userDelete")) {
			String returns = user.userDelete(name, id);	//삭제할 이름, 삭제할 아이디, 삭제할 핀
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