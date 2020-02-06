package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Schedule {	//	일정 등록
	private static Schedule instance = new Schedule();
	
	public static Schedule getInstance() {
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
	
	public String scheduleList() {	//	일정 제목 목록 가져오기
		try {			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select save_title from calander";
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
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from calander where save_title=? and save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();			

			if(rs.next()) {	//	해당 날짜에 해당 제목의 일정이 이미 존재 할 때
				returns = "scheduleAlreadyExist";
			}
			else {	//	해당 날짜에 해당 제목의 일정이 존재하지 않을 때
				sql2 = "insert into calander (save_title, save_text, save_date) values (?, ?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, title);
				pstmt2.setString(2, text);
				pstmt2.setString(3, date);
				pstmt2.executeUpdate();
				returns = "scheduleAddSuccess";
			
			}
		}
		catch(Exception e) {
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
	
	public String scheduleShow(String date) {	//	날자별 일정 보기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from calander where save_date=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();	//	db에 쿼리문 입력
			returnb = new StringBuilder("");
			if(rs.next()) {	//	해당 날짜의 일정이 존재할 때
				returnb.append("{save_title:"+ rs.getString("save_title") +",save_text:"
			+ rs.getString("save_text")+",save_date:"+rs.getString("save_date") +"}"); //	returns문에 json데이터 형태로 보내줌				
				while(rs.next()) {
					returnb.append("{save_title:"+ rs.getString("save_title") +",save_text:"
				+ rs.getString("save_text")+",save_date:"+rs.getString("save_date") +"}");
				}				
				returns = returnb.toString();
			}
			else {	//	해당 날짜의 일정이 존재하지 않을 때
				returns = "scheduleNotExist";
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
	
	public String scheduleDelete(String title, String date) {	//	일정 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); 
			sql = "select * from calander where save_title=? and save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, date);
		    rs = pstmt.executeQuery();
			if(rs.next()) {	//	일정이 존재할 때
				sql2 = "delete from calander where save_title=? and save_date=?"; 	//	calander 데이터베이스로부터 title에 해당되는 레코드 삭제
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, title);
				pstmt2.setString(2, date);
				pstmt2.executeUpdate();	//	db에 쿼리문 입력
				returns = "scheduleDelete";				
			}
			else {	//	일정이 존재하지 않을 때
				returns = "scheduleNotExist";
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
		
	public String scheduleModify(String beforeTitle, String afterTitle, String afterText, String date) {	//	일정 수정
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	 
			sql = "select * from calander where save_title=? and save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, beforeTitle);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	일정이 존재할 때
				sql2 = "update calander set save_title=?, save_text=? where save_title=? and save_date=?"; 	//	calander 데이터베이스로부터 title에 해당되는 레코드 삭제
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, afterTitle);
				pstmt2.setString(2, afterText);
				pstmt2.setString(3, beforeTitle);
				pstmt2.setString(4, date);
				pstmt2.executeUpdate();	//	db에 쿼리문 입력
				returns = "scheduleModified";				
			}
			else {	//	일정이 존재하지 않을 때
				returns = "scheduleNotExist";
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
}
