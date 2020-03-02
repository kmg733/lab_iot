package DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Conference {	//	회의록
private static Conference instance = new Conference();

	public static Conference getInstance() {
		return instance;
	}
	
	//	DB접근
	private DBConnector dbc = new DBConnector();	//	DBConnector 객체생성
	private Connection conn;    //  connecttion:db에 접근하게 해주는 객체
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;	
	
	//	결과값
	private String returns;
	
	public String cfShow(String date) {	//	회의록 내용 보기
			try {
				conn = dbc.getConn();
				sql = "select * from meetlog where save_date=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, date);
				rs = pstmt.executeQuery();			
				if(rs.next()) {	//	date에 해당되는 날자의 회의록이 존재할 떄
					returns = rs.getString("save_text") + "-cfExist";
				}
				else {	//	date에 해당되는 날자의 회의록이 존재하지 않을 때
					returns = "cfNotExist";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("Conference cfShow SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("Conference cfShow SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("Conference cfShow SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("Conference cfShow SQLException error");
						returns = "error";
					}
			}

		return returns;
	}
	
			
	
	public String cfAdd(String date, String text) {	//	회의 내용 등록 - 수정버튼 필요없이 저장시 새로 갱신
			try {
				conn = dbc.getConn();
				sql = "select * from meetlog where save_date=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, date);
				rs = pstmt.executeQuery();
				if(rs.next()) {	//	해당 날짜의 회의내용이 존재할 때 - 수정
					sql2 = "update meetlog set save_text=? where save_date=?"; 	//	meetlog 데이터베이스에 새로운 정보 등록
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, text);
					pstmt2.setString(2, date);
					pstmt2.executeUpdate();	//	db에 쿼리문 입력	
				}
				else {	//	해당 날짜의 회의내용이 존재하지 않을 때 - 새로생성
					sql2 = "insert into meetlog (save_date, save_text) values (?, ?)"; 	//	meetlog 데이터베이스에 새로운 정보 등록
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, date);
					pstmt2.setString(2, text);
					pstmt2.executeUpdate();	//	db에 쿼리문 입력
				}
				returns = "cfAdded";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("Conference cfAdd SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("Conference cfAdd SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("Conference cfAdd SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("Conference cfAdd SQLException error");
						returns = "error";
					}
			}
			
		return returns;
	}	
	
	
}
