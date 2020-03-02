package DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MemberState {	//	�ο� ��Ȳ ����
	private static MemberState instance = new MemberState();
	
	public static MemberState getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector ��ü����
	private Connection conn;    //  connecttion:db�� �����ϰ� ���ִ� ��ü
	private String sql = ""; 	
	private String sql2 = "";
	private PreparedStatement pstmt;	
	private PreparedStatement pstmt2;
	private ResultSet rs;	
	private String returns; 	
	
	public String memShow() {	//	ȸ�� ���� ����Ʈ
			try {
				conn = dbc.getConn();
				sql = "select * from member order by name asc"; 	//	member ���̺�κ��� ��� ������ ������
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();	//	db�� ������ �Է�
				
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
					//https://offbyone.tistory.com/373 (�����ϱ� - json������)
					//https://dololak.tistory.com/625
				}
				returns = jary.toJSONString();//	json���·� ����Ʈ ������
				
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
	
	public String memAdd(String name, String phone, String dept, String team) {	//	ȸ�� ���
			try {
				conn = dbc.getConn();
				sql = "select * from member where name=? and phone=?"; // 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, phone);
				rs = pstmt.executeQuery(); // db�� ������ �Է�
				if(rs.next()) {	//	�̹� �ش� ������ ������ ��
					returns = "memAleadyExist";
				}
				else {	//	�������� ���� ��
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
	
	//ȸ������ ������ �� �����ϱ� ���� ������ ���� ��� ������ ������
	public String memModify(String beforeName, String beforePhone, String afterName, String afterPhone, String afterDept, String afterTeam) {	//	ȸ�� ���� ����
			try {
				conn = dbc.getConn();
				sql = "select * from member where name=? and phone=?"; // 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, beforeName);
				pstmt.setString(2, beforePhone);
				rs = pstmt.executeQuery(); // db�� ������ �Է�
				if(rs.next()) {	//	�ش� name�� phone �����Ͱ� ������ ��
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
				else {	//	�������� ���� ��
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
				if(rs.next()) {	//	�ش� ������ ����� ������ ��
					sql2 = "delete from member where name=? and phone=? and dept=? and team=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, name);
					pstmt2.setString(2, phone);
					pstmt2.setString(3, dept);
					pstmt2.setString(4, team);
					pstmt2.executeUpdate(); // db�� ������ �Է�
					returns = "memDeleted";				
				}
				else {	//	�ش� ������ ����� �������� ���� ��
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
