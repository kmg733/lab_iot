
package DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class User { // ȸ�� ���� ����
	private static User instance = new User();

	public static User getInstance() {
		return instance;
	}

	private DBConnector dbc = new DBConnector(); // DBConnector ��ü����
	private Connection conn; // connecttion:db�� �����ϰ� ���ִ� ��ü
	private String sql = "";
	private String sql2 = "";
	private String sql3 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private ResultSet rs;
	private ResultSet rs2;
	private String returns;

	/* ������ - ȸ�� ���� ���� */

	public String addUser_List() { // ȸ�� ���� ����Ʈ - add_user ���̺� ����Ʈ
		try {
			conn = dbc.getConn();
			sql = "select * from add_user order by id desc"; // user���̺�κ��� ��� ������ �ҷ�����
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // db�� ������ �Է�

			JSONArray jary = new JSONArray();
			boolean flag = true;
			while (rs.next()) {
				JSONObject jobj = new JSONObject();
				jobj.put("name", rs.getString("name"));
				jobj.put("id", rs.getString("id"));
				jary.add(jobj);
				flag = false;
				// https://offbyone.tistory.com/373 (�����ϱ� - json������)
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

	public String user_List(String name, String id) { // ȸ�� ���� ����Ʈ - user ���̺� ����Ʈ
			try {
				conn = dbc.getConn();
				sql = "select * from user where id=? and name=?"; // user���̺�κ��� ��� ������ �ҷ�����
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, name);
				rs = pstmt.executeQuery(); // db�� ������ �Է�
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

	public String addUser_Add(String name, String id) { // �����ڰ� add_user ���̺� ȸ�� ������ �߰��� ��
			try {
				conn = dbc.getConn();
				sql = "select * from add_user where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) { // id�� �̹� ������ ��
					returns = "IDAleadyExist";
				} else { // id�� �������� �ʾƼ� �߰� �� ��
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

	public String user_Delete(String name, String id) { // ȸ�� ���� ���� - user���̺�
			try {
				conn = dbc.getConn();
				sql = "select * from user where id=? and name=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, name);
				rs = pstmt.executeQuery();
				if (rs.next()) { // user���̺� id�� name�� �����ϴ��� Ȯ��
					sql2 = "delete from user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, name);
					pstmt2.executeUpdate(); // db�� ������ �Է�
					returns = "user_deleted";
				} else { // user���̺� id�� ���� ���� ������
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

	public String addUser_Delete(String name, String id) { // ȸ�� ���� ���� - user���̺�� add_user���̺� �Ѵ�
			try {
				conn = dbc.getConn();
				sql = "select * from user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, name);
				rs = pstmt.executeQuery(); // db�� ������ �Է�
				if (rs.next()) { // user���̺� ������ ������ ��
					sql2 = "delete from user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, name);
					pstmt2.executeUpdate(); // db�� ������ �Է�

					sql3 = "delete from add_user where id=? and name=?"; // add_user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
					pstmt3 = conn.prepareStatement(sql2);
					pstmt3.setString(1, id);
					pstmt3.setString(2, name);
					pstmt3.executeUpdate(); // db�� ������ �Է�
					returns = "deleteAllSuccess";
				} else { // user���̺� ������ �������� ���� ��
					sql2 = "select * from add_user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ã��
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, name);
					rs2 = pstmt2.executeQuery(); // db�� ������ �Է�
					if (rs2.next()) { // add_user���̺� ������ ������ ��
						sql3 = "delete from add_user where id=? and name=?"; // add_user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
						pstmt3 = conn.prepareStatement(sql3);
						pstmt3.setString(1, id);
						pstmt3.setString(2, name);
						pstmt3.executeUpdate(); // db�� ������ �Է�
						returns = "deleteAllSuccess";
					} else { // add_user���̺� ������ �������� ���� ��
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

	/* ����(����) */
	public String user_Modify(String before_name, String before_id, String after_name, String after_id,
			String after_password, String after_mail) { // add_user���̺�� user���̺� ��� ���� ����
			
		try {
				conn = dbc.getConn();
				sql = "select * from add_user where id=? and name=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, before_id);
				pstmt.setString(2, before_name);
				rs = pstmt.executeQuery();
				if (rs.next()) { // add_user���̺� ������ id�� name�� ������ ��
					if (user_Delete(before_id, before_name).equals("user_Deleted")) { // user���̺� ���� ������ id�� name�� ������ ��
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
					} else { // add_user���̺��� ������ id�� name�� ������ ��
						sql2 = "update add_user set id=?, name=? where id=? and name=?";
						pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setString(1, after_id);
						pstmt2.setString(2, after_name);
						pstmt2.setString(3, before_id);
						pstmt2.setString(4, before_name);
						pstmt2.executeUpdate();
						returns = "user_modified2";
					}
				} else { // add_user���̺� ������ idȤ�� name�� �������� ���� ��
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
