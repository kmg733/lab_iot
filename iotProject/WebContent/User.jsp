<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="util.DataRSA"%>
<%@	page import="util.DataAES"%>
<%@ page import="DBConnect.*"%>

<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String mail = request.getParameter("mail");
	String b_name = request.getParameter("b_name");
	String b_id = request.getParameter("b_id");
	String type = request.getParameter("type"); //	사용자가 무슨요청을 했는지 구분하는 변수
	String securityKey = request.getParameter("securitykey"); //	Front-End에서 보내주는 대칭 키

	User user = User.getInstance();

	String propFile = "C:/Users/securityLab_5/eclipse-workspace/IoT/src/util/PRIVATEkey.properties";
	Properties props = new Properties();
	FileInputStream fis = new FileInputStream(propFile);
	props.load(new java.io.BufferedInputStream(fis));

	String rsaPrivatekey = props.getProperty("key");

	if (rsaPrivatekey != null) {
		securityKey = DataRSA.rsaDecryption(securityKey, rsaPrivatekey);
		type = DataAES.aesDecryption(type, securityKey);
	}

	
	
	if (type.equals("addUser_List")) {		
		String returns = user.addUser_List(); //add-user테이블에 추가된 정보 찾기
		returns = DataAES.aesEncryption(returns, securityKey);
		
		out.clear();
		out.print(returns);
		out.flush();
	
	} else if (type.equals("user_List")) {
		name = DataAES.aesDecryption(name, securityKey);
		id = DataAES.aesDecryption(id, securityKey);
		
		String returns = user.user_List(name, id); //추가할 이름, 추가할 아이디
		returns = DataAES.aesEncryption(returns, securityKey);
		
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (type.equals("addUser_Add")) {
		name = DataAES.aesDecryption(name, securityKey);
		id = DataAES.aesDecryption(id, securityKey);
		
		String returns = user.addUser_Add(name, id); //추가할 이름, 추가할 아이디
		returns = DataAES.aesEncryption(returns, securityKey);
		
		out.clear();
		out.print(returns);
		out.flush();
		
	} else if (type.equals("addUser_Delete")) {
		name = DataAES.aesDecryption(name, securityKey);
		id = DataAES.aesDecryption(id, securityKey);
				
		String returns = user.addUser_Delete(name, id); //삭제할 이름, 삭제할 아이디
		returns = DataAES.aesEncryption(returns, securityKey);
		
		out.clear();
		out.print(returns);
		out.flush();
		
	} /*
		else if (type.equals("user_Modify")) {	//add_user와 user 수정을 할 때 모두 이 메소드를 이용
		String returns = user.user_Modify(b_name, b_id, name, id, pwd, mail); //수정전 id,name - 수정후id,name,pwd
		out.clear();
		out.print(returns);
		out.flush();
		}*/
	else {
		String returns = "error/nonTypeRequest";
		returns = DataAES.aesEncryption(returns, securityKey);
		
		out.clear();
		out.print(returns);
		out.flush();
		
	}
%>