
package DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class User { // 회원 정보 관리
	private static User instance = new User();

	public static User getInstance() {
		return instance;
	}

	private DBConnector dbc = new DBConnector(); // DBConnector 객체생성
	private Connection conn; // connecttion:db에 접근하게 해주는 객체
	private String sql = "";
	private String sql2 = "";
	private String sql3 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private ResultSet rs;
	private ResultSet rs2;
	private String returns;

	/* 관리자 - 회원 정보 관리 */

	public String addUser_List() { // 회원 정보 리스트 - add_user 테이블 리스트
		try {
			conn = dbc.getConn();
			sql = "select * from add_user order by id desc"; // user테이블로부터 모든 정보를 불러오기
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // db에 쿼리문 입력

			JSONArray jary = new JSONArray();
			boolean flag = true;
			while (rs.next()) {
				JSONObject jobj = new JSONObject();
				jobj.put("name", rs.getString("name"));
				jobj.put("id", rs.getString("id"));
				jary.add(jobj);
				flag = false;
				// https://offbyone.tistory.com/373 (참고하기 - json데이터)
				// https://dololak.tistory.com/625
			}
			returns = jary.toJSONString();
			if (flag) {
				returns = "addUser_NotExist";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("User addUser_List SQLException error");
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_List SQLException error");
					returns = "error";
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_List SQLException error");
					returns = "error";
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_List SQLException error");
					returns = "error";
				}				
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
					System.err.println("User addUser_List SQLException error");
					returns = "error";
				}
		}	
		return returns;
	}

	public String user_List(String name, String id) { // 회원 정보 리스트 - user 테이블 리스트
			try {
				conn = dbc.getConn();
				sql = "select * from user where id=? and name=?"; // user테이블로부터 모든 정보를 불러오기
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, name);
				rs = pstmt.executeQuery(); // db에 쿼리문 입력
				String get = "";
				if (rs.next()) {
					get = rs.getString("id") + "-" + rs.getString("name") + "-" + rs.getString("mail") + "-userListExist";
					returns = get;
				} else {
					returns = "user_NotExist";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("User user_List SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_List SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_List SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_List SQLException error");
						returns = "error";
					}				
			}	
			
		return returns;
	}

	public String addUser_Add(String name, String id) { // 관리자가 add_user 테이블에 회원 정보를 추가할 때
			try {
				conn = dbc.getConn();
				sql = "select * from add_user where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) { // id가 이미 존재할 때
					returns = "IDAleadyExist";
				} else { // id가 존재하지 않아서 추가 할 때
					sql2 = "insert into add_user (id, name) values (?, ?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, name);
					pstmt2.executeUpdate();
					returns = "addUser_addSuccess";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("User addUser_Add SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Add SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Add SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Add SQLException error");
						returns = "error";
					}	
				if (pstmt2 != null)
					try {
						pstmt2.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Add SQLException error");
						returns = "error";
					}
			}	
		return returns;

	}

	public String user_Delete(String name, String id) { // 회원 정보 삭제 - user테이블만
			try {
				conn = dbc.getConn();
				sql = "select * from user where id=? and name=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, name);
				rs = pstmt.executeQuery();
				if (rs.next()) { // user테이블에 id와 name이 존재하는지 확인
					sql2 = "delete from user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 삭제
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, name);
					pstmt2.executeUpdate(); // db에 쿼리문 입력
					returns = "user_deleted";
				} else { // user테이블에 id가 존재 하지 않을때
					returns = "user_valueNotExistToDelete";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("User user_Delete SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("User user_Delete SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("User user_Delete SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("User user_Delete SQLException error");
						returns = "error";
					}	
				if (pstmt2 != null)
					try {
						pstmt2.close();
					} catch (SQLException ex) {
						System.err.println("User user_Delete SQLException error");
						returns = "error";
					}
			}	
			
		return returns;
	}

	public String addUser_Delete(String name, String id) { // 회원 정보 삭제 - user테이블과 add_user테이블 둘다
			try {
				conn = dbc.getConn();
				sql = "select * from user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 삭제
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, name);
				rs = pstmt.executeQuery(); // db에 쿼리문 입력
				if (rs.next()) { // user테이블에 정보가 존재할 때
					sql2 = "delete from user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 삭제
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, name);
					pstmt2.executeUpdate(); // db에 쿼리문 입력

					sql3 = "delete from add_user where id=? and name=?"; // add_user 테이블에 id와 name에 해당되는 레코드 삭제
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, name);
					pstmt3.executeUpdate(); // db에 쿼리문 입력
					returns = "deleteAllSuccess";
				} else { // user테이블에 정보가 존재하지 않을 때
					sql2 = "select * from add_user where id=? and name=?"; // user 테이블에 id와 name에 해당되는 레코드 찾기
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, name);
					rs2 = pstmt2.executeQuery(); // db에 쿼리문 입력
					if (rs2.next()) { // add_user테이블에 정보가 존재할 때
						sql3 = "delete from add_user where id=? and name=?"; // add_user 테이블에 id와 name에 해당되는 레코드 삭제
						pstmt3 = conn.prepareStatement(sql3);
						pstmt3.setString(1, id);
						pstmt3.setString(2, name);
						pstmt3.executeUpdate(); // db에 쿼리문 입력
						returns = "deleteAllSuccess";
					} else { // add_user테이블에 정보가 존재하지 않을 때
						returns = "addUserDataNotExist";
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("User addUser_Delete SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}	
				if (pstmt2 != null)
					try {
						pstmt2.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}
				if (rs2 != null)
					try {
						rs2.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}	
				if (pstmt3 != null)
					try {
						pstmt3.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}
			}	 
			
		return returns;
	}

	/* 공통(수정) */
	public String user_Modify(String before_name, String before_id, String after_name, String after_id,
			String after_password, String after_mail) { // add_user테이블과 user테이블 모두 수정 가능
			
		try {
				conn = dbc.getConn();
				sql = "select * from add_user where id=? and name=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, before_id);
				pstmt.setString(2, before_name);
				rs = pstmt.executeQuery();
				if (rs.next()) { // add_user테이블에 수정할 id와 name이 존재할 때
					if (user_Delete(before_id, before_name).equals("user_Deleted")) { // user테이블 에도 수정할 id와 name이 존재할 때
						sql2 = "update add_user set id=?, name=? where id=? and name=?";
						pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setString(1, after_id);
						pstmt2.setString(2, after_name);
						pstmt2.setString(3, before_id);
						pstmt2.setString(4, before_name);
						pstmt2.executeUpdate();

						sql3 = "insert into user (id, password, name, mail) values (?, ?, ?, ?)";
						pstmt3 = conn.prepareStatement(sql3);
						pstmt3.setString(1, after_id);
						pstmt3.setString(2, after_password);
						pstmt3.setString(3, after_name);
						pstmt3.setString(4, after_mail);
						pstmt3.executeUpdate();
						returns = "user_modified";
					} else { // add_user테이블에만 수정할 id와 name이 존재할 때
						sql2 = "update add_user set id=?, name=? where id=? and name=?";
						pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setString(1, after_id);
						pstmt2.setString(2, after_name);
						pstmt2.setString(3, before_id);
						pstmt2.setString(4, before_name);
						pstmt2.executeUpdate();
						returns = "user_modified2";
					}
				} else { // add_user테이블에 수정할 id혹은 name이 존재하지 않을 때
					returns = "user_valueNotExist";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("User user_Modify SQLException error");
				returns = "error";
			} finally {
				if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}	
				if (pstmt2 != null)
					try {
						pstmt2.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}				
				if (pstmt3 != null)
					try {
						pstmt3.close();
					} catch (SQLException ex) {
						System.err.println("User addUser_Delete SQLException error");
						returns = "error";
					}
			}	
		return returns;
	}
}
