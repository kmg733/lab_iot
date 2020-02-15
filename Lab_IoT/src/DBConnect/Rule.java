package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rule {	//	규칙
private static Rule instance = new Rule();
	
	public static Rule getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector 객체생성
	private Connection conn;
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;	
	private String returns;
	
	public String ruleShow() {	//	규칙 내용 보기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from rule";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	//	규칙이 존재할 때
				returns = rs.getString("save_text") + "-ruleExist";
			}
			else {	//	규칙이 존재하지 않을 때
				returns = "ruleNotExist";
			} 
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
	
	public String ruleAdd(String text) {	//	규칙 내용 등록 - 수정버튼 필요없이 저장시 새로 갱신
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "select * from rule";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();			
			if(rs.next()) {	//	규칙 내용이 존재할 때 - 수정
				sql2 = "update rule set save_text=?"; 	//	meetlog 데이터베이스에 새로운 정보 등록
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, text);
				pstmt2.executeUpdate();	//	db에 쿼리문 입력
			}
			else {	//	해당 날짜의 회의내용이 존재하지 않을 때 - 새로생성
				sql2 = "insert into rule (save_text) values (?)"; 	//	meetlog 데이터베이스에 새로운 정보 등록
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, text);
				pstmt2.executeUpdate();	//	db에 쿼리문 입력
			}
			returns = "ruleAdded";
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
	
}
