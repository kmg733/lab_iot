package DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MemberState {	//	인원 현황 관리
	private static MemberState instance = new MemberState();
	
	public static MemberState getInstance() {
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
	
	public String memShow() {	//	회원 정보 리스트
			try {
				conn = dbc.getConn();
				sql = "select * from member order by name asc"; 	//	member 테이블로부터 모든 정보를 가져옴
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();	//	db에 쿼리문 입력
				
				JSONArray jary = new JSONArray();
				boolean flag = true;
				
				while(rs.next()) {
					JSONObject jobj = new JSONObject();
					jobj.put("name", rs.getString("name"));
					jobj.put("phone", rs.getString("phone"));
					jobj.put("dept", rs.getString("dept"));
					jobj.put("team", rs.getString("team"));
					jary.add(jobj);
					
					flag = false;
					//https://offbyone.tistory.com/373 (참고하기 - json데이터)
					//https://dololak.tistory.com/625
				}
				returns = jary.toJSONString();//	json형태로 리스트 보내기
				
				if(flag) {
					returns = "memNotExist";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("MemberState memShow SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("MemberState findPW SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("MemberState findPW SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("MemberState findPW SQLException error");
						returns = "error";
					}				
			}	

		return returns;
	}
	
	public String memAdd(String name, String phone, String dept, String team) {	//	회원 등록
			try {
				conn = dbc.getConn();
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("MemberState memAdd SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memAdd SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memAdd SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memAdd SQLException error");
						returns = "error";
					}
				if (pstmt2 != null)
					try {
						pstmt2.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memAdd SQLException error");
						returns = "error";
					}
			}	
			
		return returns;
	}
	
	//회원정보 수정할 때 수정하기 전의 정보도 같이 줘야 수정이 가능함
	public String memModify(String beforeName, String beforePhone, String afterName, String afterPhone, String afterDept, String afterTeam) {	//	회원 정보 수정
			try {
				conn = dbc.getConn();
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("MemberState memModify SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memModify SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memModify SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memModify SQLException error");
						returns = "error";
					}
				if (pstmt2 != null)
					try {
						pstmt2.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memModify SQLException error");
						returns = "error";
					}
			}	
			
		return returns;
	}
	
	public String memDelete(String name, String phone, String dept, String team) {
			try {
				conn = dbc.getConn();
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("MemberState memDelete SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memModify SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memModify SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memModify SQLException error");
						returns = "error";
					}
				if (pstmt2 != null)
					try {
						pstmt2.close();
					} catch (SQLException ex) {
						System.err.println("MemberState memModify SQLException error");
						returns = "error";
					}
			}	

		return returns;
	}	
}
