package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conference {
private static Conference instance = new Conference();
	
	public static Conference getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector 객체생성
	private Connection conn;	//	db에 접근하게 해주는 객체
	private String sql = ""; 	//	쿼리1(MariaDB에 들어갈 명령어지문)
	private PreparedStatement pstmt;		//	db에 sql문을 전달해주는 객체
	private ResultSet rs;	//	db에서 쿼리의 실행결과를 가져오는 객체
	private String returns; //메소드 성공 여부 반환
	
	public String cfAdd(String date, String text) {	//	회의 내용 등록
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "insert into meetlog (save_date, save_text) values (?, ?)"; 	//	meetlog 데이터베이스에 새로운 정보 등록
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(1, text);
			pstmt.executeUpdate();	//	db에 쿼리문 입력
			returns = "cfAdd";
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String cfModify(String date, String text) {	//	회의록 내용 수정
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "update meetlog set save_text=? where save_date=?"; 	//	meetlog 데이터베이스로부터 save_date가 date인 text컬럼의 내용을 수정함
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, text);
			pstmt.setString(2, date);
			pstmt.executeUpdate();	//	db에 쿼리문 입력
			returns = "cfModify";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	
	public String cfShow(String date) {	//	회의록 내용 보기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "select * from meetlog where svae_date=?"; 	//	calander 데이터베이스로부터 title에 해당되는 정보 보기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			if(rs.next()) {	//	date에 해당되는 날자의 회의록이 존재할 떄
				returns = rs.getString("save_text");
			}
			else {	//	date에 해당되는 날자의 회의록이 존재하지 않을 때
				returns = "cfNotExist";
			} 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
}
