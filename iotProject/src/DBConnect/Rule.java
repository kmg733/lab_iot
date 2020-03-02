package DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rule {	//	규칙
private static Rule instance = new Rule();
	
	public static Rule getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector 객체생성
	private Connection conn;    //  connecttion:db에 접근하게 해주는 객체
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;	
	private String returns;
	
	public String ruleShow() {	//	규칙 내용 보기
			try {
				conn = dbc.getConn();
				sql = "select * from rule";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {	//	규칙이 존재할 때
					returns = rs.getString("save_text") + "-ruleExist";
				}
				else {	//	규칙이 존재하지 않을 때
					returns = "ruleNotExist";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("Rule ruleShow SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("Rule ruleShow SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("Rule ruleShow SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("Rule ruleShow SQLException error");
						returns = "error";
					}
			}	

		return returns;
	}
	
	public String ruleAdd(String text) {	//	규칙 내용 등록 - 수정버튼 필요없이 저장시 새로 갱신
			try {
				conn = dbc.getConn();
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("Rule ruleAdd SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("Rule ruleAdd SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("Rule ruleAdd SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("Rule ruleAdd SQLException error");
						returns = "error";
					}
				if (pstmt2 != null)
					try {
						pstmt2.close();
					} catch (SQLException ex) {
						System.err.println("Rule ruleAdd SQLException error");
						returns = "error";
					}
			}	
		return returns;
	}		
	
}
