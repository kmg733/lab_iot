package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Schedule {	
	private static Schedule instance = new Schedule();
	
	public static Schedule getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector 객체생성
	private Connection conn;	//	db에 접근하게 해주는 객체
	private String sql = ""; 	//	쿼리1(MariaDB에 들어갈 명령어지문)
	private PreparedStatement pstmt;		//	db에 sql문을 전달해주는 객체
	private ResultSet rs;	//	db에서 쿼리의 실행결과를 가져오는 객체
	private StringBuilder returnb; 
	private String returns;
	
	public String scheduleList() {	//	일정 제목 목록 가져오기
		try {			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인
			sql = "select save_title from calander"; 	//	calander 데이터베이스로부터  svae_title 컬럼들의 내용들을 가져온다
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			returnb = new StringBuilder("");
			while(rs.next()) {
				returnb.append("{save_title:" +rs.getString("save_title") + "}");	//	returns문에 json데이터 형태로 보내줌
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
		
	public String scheduleAdd(String title, String text, String date) {	//	일정 등록
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "insert into calander (save_title, save_text, save_date) values (?, ?, ?)"; 	//	calander 데이터베이스로부터 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, text);
			pstmt.setString(3, date);
			pstmt.executeUpdate();	//	db에 쿼리문 입력
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
	
	public String scheduleShow(String title) {	//	일정 보기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "select * from calander where svae_title=?"; 	//	calander 데이터베이스로부터 title에 해당되는 정보 보기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			while(rs.next()) {
				returns = "{save_title:"+ rs.getString("svae_title") +",save_text:" + rs.getString("svae_text")+",save_date:"+rs.getString("save_date") +"}"; //	returns문에 json데이터 형태로 보내줌
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
	
	public String scheduleDelete(String title) {	//	일정 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	데이터베이스 접근을 위한 로그인 
			sql = "delete from calander where svae_title=?"; 	//	calander 데이터베이스로부터 title에 해당되는 레코드 삭제
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.executeUpdate();	//	db에 쿼리문 입력
			returns = "scheduleDelete";
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
