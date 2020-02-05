package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class User {
	private static User instance = new User();
	
	public static User getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector ��ü����
	private Connection conn;	//	db�� �����ϰ� ���ִ� ��ü
	private String sql = ""; 	//	����1(MariaDB�� �� ��ɾ�����)
	private String sql2 = "";
	private String sql3 = "";
	private PreparedStatement pstmt;		//	db�� sql���� �������ִ� ��ü
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private ResultSet rs;	//	db���� ������ �������� �������� ��ü
	private ResultSet rs2;
	private StringBuilder returnb;
	private String returns; //�޼ҵ� ���� ���� ��ȯ
	private Random rn;	//	��й�ȣ�� ��߱� ������ �������� ���� �����Լ�;
	private int tempPW;
	
	/*
	public String user_Add( String id, String name, String pwd) {	//	����ڰ� ȸ�� ������ ��
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "select * from add_user where id=? and name=?"; 	//	user ���̺� ȸ������ �ֱ�
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			if(rs.next()) {	//	add_user���̺� id�� ������ ��
				sql2 = "select * from user where id=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				rs = pstmt2.executeQuery();
				if(rs.next()) {	//	user���̺� id�� ������ ��
					returns = "user_AlreadExist";
				}
				else {	//	user���̺� id�� �������� ���� ��
					sql3 = "insert into user (id, password, name) values (?, ?, ?)"; 	//	user ���̺� ȸ������ �ֱ�
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.executeUpdate();	//	db�� ������ �Է�		
					returns = "user_AddSuccess";
				}
			}
			else {	//	�ش� ȸ�������� �������� ���� �� Ȥ�� �߸� �Է����� ��
				returns = "idOrNameNotExist";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			returns = ""+e;
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	*/
	
	/* ������ - ȸ�� ���� ���� */	
	
	public String user_List() {	//	ȸ�� ���� ����Ʈ - user ���̺� ����Ʈ
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "select * from user"; 	//	user���̺�κ��� ��� ������ �ҷ�����
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			returnb = new StringBuilder("");
			while(rs.next()) {
				returnb.append("{name:"+rs.getString("name")+",id:" +rs.getString("id")+"}");	//	ȸ�� ����Ʈ�� ǥ���� �̸��� id�� ������, json������ ���·� ������
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
	
	public String addUser_List() {	//	ȸ�� ���� ����Ʈ - add_user ���̺� ����Ʈ
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "select * from add_user"; 	//	user���̺�κ��� ��� ������ �ҷ�����
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			returnb = new StringBuilder("");
			while(rs.next()) {
				returnb.append("{name:"+rs.getString("name")+",id:" +rs.getString("id")+"}");	//	ȸ�� ����Ʈ�� ǥ���� �̸��� id�� ������, json������ ���·� ������
				//https://freegae.tistory.com/5  (�����ϱ� - json������)
			}
			returns = returnb.toString();
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
	
	public String addUser_Add(String name, String id) {	//	�����ڰ� add_user ���̺� ȸ�� ������ �߰��� ��
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from add_user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				returns = "IDAleadyExist";
			}
			else {
				sql2 = "insert into add_user (id, name) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, name);	
				pstmt2.executeUpdate();
				returns = "addUser_addSuccess";
			}
		} catch(Exception e) {
			e.printStackTrace();
			returns = ""+e;
		}		
		return returns;
		
	}
	
	public String user_Delete(String name, String id) {	//	ȸ�� ���� ���� - user���̺�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=? and name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	user���̺� id�� name�� �����ϴ��� Ȯ��
				sql2 = "delete from user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, name);
				pstmt2.executeUpdate(); // db�� ������ �Է�
				returns = "user_deleted";				
			}
			else {
				returns = "user_valueNotExistToDelete";
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
	
	public String addUser_Delete(String name, String id) {	//	ȸ�� ���� ���� - user���̺�� add_user���̺� �Ѵ�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery(); // db�� ������ �Է�
			if(rs.next()) {	//	user���̺� ������ ������ ��
				sql2 = "delete from user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, name);
				pstmt2.executeUpdate(); // db�� ������ �Է�
				
				sql3 = "delete from add_user where id=? and name=?";	//	add_user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
				pstmt3 = conn.prepareStatement(sql2);
				pstmt3.setString(1, id);
				pstmt3.setString(2, name);
				pstmt3.executeUpdate(); // db�� ������ �Է�
				returns ="deleteAllSuccess";
			}
			else {	//	user���̺� ������ �������� ���� ��
				sql2 = "select * from add_user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ã��
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, name);
				rs2 = pstmt2.executeQuery(); // db�� ������ �Է�
				if(rs2.next()) {	//	add_user���̺� ������ ������ ��
					sql3 = "delete from add_user where id=? and name=?";	//	add_user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, name);
					pstmt3.executeUpdate(); // db�� ������ �Է�
					returns ="deleteAllSuccess2";
				}
				else {	//	add_user���̺� ������ �������� ���� ��
					returns = "addUserDataNotExist";
				}
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
	
	/* ����(����) */
	public String user_Modify(String before_name, String before_id ,String after_name, String after_id, String after_password) {	//	add_user���̺�� user���̺� ��� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from add_user where id=? and name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, before_id);
			pstmt.setString(2, before_name);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	add_user���̺� ������ id�� name�� ������ ��				
				if(user_Delete(before_id, before_name).equals("user_Deleted")) { // user���̺� ���� ������ id�� name�� ������ ��
					conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
					sql2 = "update add_user set id=?, name=? where id=? and name=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, after_id);
					pstmt2.setString(2, after_name);
					pstmt2.setString(3, before_id);
					pstmt2.setString(4, before_name);
					pstmt2.executeUpdate();	
					
					sql3 = "insert into user (id, password, name) values (?, ?, ?)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, after_id);					
					pstmt3.setString(2, after_password);
					pstmt3.setString(3, after_name);
					pstmt3.executeUpdate();
					returns = "user_modified";
				}
				else {	//	add_user���̺��� ������ id�� name�� ������ ��
					conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
					sql2 = "update add_user set id=?, name=? where id=? and name=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, after_id);
					pstmt2.setString(2, after_name);
					pstmt2.setString(3, before_id);
					pstmt2.setString(4, before_name);
					pstmt2.executeUpdate();
					returns = "user_modified2";
				}
			}
			else {	//	add_user���̺� ������ idȤ�� name�� �������� ���� ��
				returns = "user_valueNotExist";
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
