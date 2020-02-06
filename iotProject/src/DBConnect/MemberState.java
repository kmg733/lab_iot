package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class MemberState {	//	인원 현황 관리
	private static MemberState instance = new MemberState();
	
	public static MemberState getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector 객체생성
	private Connection conn;	
	private String sql = ""; 	
	private String sql2 = "";
	private PreparedStatement pstmt;	
	private PreparedStatement pstmt2;
	private ResultSet rs;	
	private StringBuilder returnb;
	private String returns; 	
	private Random rn;	
	private int tempPW;
	
	public String memShow() {	//	회원 정보 리스트
		try {			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			sql = "select * from member"; 	//	member 테이블로부터 모든 정보를 가져옴
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			returnb = new StringBuilder("");
			while(rs.next()) {
				returnb.append("{name:" +rs.getString("name") +",phone:" + rs.getString("phone")
			+ ",dept:"+ rs.getString("dept") +",team:"+ rs.getString("team") +"}");	//	returns문에 json데이터 형태로 보내주기 위해 returnb에 appned함
				//https://freegae.tistory.com/5  (참고하기 - json데이터)
			}
			returns = returnb.toString();
		}
 		catch(Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}	
		return returns;
	}
	
	public String memAdd(String name, String phone, String dept, String team) {	//	회원 등록
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from member where name=? and phone=?"; // 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery(); // db에 쿼리문 입력
			if(rs.next()) {	//	이미 해당 내용이 존재할 때
				returns = "memAleadyExist";
			}
			else {	//	존재하지 않을 때
				sql2 = "insert into member (name, phone, dept, team) values (?, ?, ?, ?)"; // 
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, name);
				pstmt2.setString(2, phone);
				pstmt2.setString(3, dept);
				pstmt2.setString(4, team);
				pstmt2.executeUpdate();
				returns = "memAddSuccess";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error " + e;
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	//회원정보 수정할 때 수정하기 전의 정보도 같이 줘야 수정이 가능함
	public String memModify(String beforeName, String beforePhone, String afterName, String afterPhone, String afterDept, String afterTeam) {	//	회원 정보 수정
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from member where name=? and phone=?"; // 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, beforeName);
			pstmt.setString(2, beforePhone);
			rs = pstmt.executeQuery(); // db에 쿼리문 입력
			if(rs.next()) {	//	해당 name과 phone 데이터가 존재할 때
				sql2 = "update member set name=?, phone=?, dept=?, team=? where name=? and phone=?"; // 
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, afterName);
				pstmt2.setString(2, afterPhone);
				pstmt2.setString(3, afterDept);
				pstmt2.setString(4, afterTeam);
				pstmt2.setString(5, beforeName);
				pstmt2.setString(6, beforePhone);
				pstmt2.executeUpdate();
				returns = "memModifySuccess";
			}
			else {	//	존재하지 않을 때
				returns = "memNotExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String memDelete(String name, String phone, String dept, String team) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from member where name=? and phone=? and dept=? and team=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setString(3, dept);
			pstmt.setString(4, team);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	해당 내용의 멤버가 존재할 때
				sql2 = "delete from member where name=? and phone=? and dept=? and team=?"; // user 테이블에 id와 name에 해당되는 레코드 삭제
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, name);
				pstmt2.setString(2, phone);
				pstmt2.setString(3, dept);
				pstmt2.setString(4, team);
				pstmt2.executeUpdate(); // db에 쿼리문 입력
				returns = "memDeleted";				
			}
			else {	//	해당 내용의 멤버가 존재하지 않을 때
				returns = "memNotExist";
			}			
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}	
}
