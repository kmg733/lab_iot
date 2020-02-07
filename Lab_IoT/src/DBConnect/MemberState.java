package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class MemberState {	//	�ο� ��Ȳ ����
	private static MemberState instance = new MemberState();
	
	public static MemberState getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector ��ü����
	private Connection conn;	
	private String sql = ""; 	
	private String sql2 = "";
	private PreparedStatement pstmt;	
	private PreparedStatement pstmt2;
	private ResultSet rs;	
	private StringBuilder returnb;
	private String returns; 	
	private Random rn;	
	private int tempPW;
	
	public String memShow() {	//	ȸ�� ���� ����Ʈ
		try {			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α���
			sql = "select * from member"; 	//	member ���̺�κ��� ��� ������ ������
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			returnb = new StringBuilder("");
			while(rs.next()) {
				returnb.append("{name:" +rs.getString("name") +",phone:" + rs.getString("phone")
			+ ",dept:"+ rs.getString("dept") +",team:"+ rs.getString("team") +"}");	//	returns���� json������ ���·� �����ֱ� ���� returnb�� appned��
				//https://freegae.tistory.com/5  (�����ϱ� - json������)
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
	
	public String memAdd(String name, String phone, String dept, String team) {	//	ȸ�� ���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
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
		} catch (Exception e) {
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
	
	//ȸ������ ������ �� �����ϱ� ���� ������ ���� ��� ������ ������
	public String memModify(String beforeName, String beforePhone, String afterName, String afterPhone, String afterDept, String afterTeam) {	//	ȸ�� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
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
		} catch (Exception e) {
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
	
	public String memDelete(String name, String phone, String dept, String team) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
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
		} catch (Exception e) {
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
