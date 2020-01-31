package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class UserInfo {
	private static UserInfo instance = new UserInfo();
	
	public static UserInfo getInstance() {
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
	private StringBuilder returnb;
	private String returns; //�޼ҵ� ���� ���� ��ȯ
	private Random rn;	//	��й�ȣ�� ��߱� ������ �������� ���� �����Լ�;
	private int tempPW;
	
	public String userList() {	//	ȸ�� ���� ����Ʈ
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "selecte * from user"; 	//	user���̺�κ��� ��� ������ �ҷ�����
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
	
	public String userAdd( String id, String name, String pwd) {	//	ȸ�� ���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "select * from add_user where id=?"; 	//	user ���̺� ȸ������ �ֱ�
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			if(rs.next()) {	//	add-user���̺� id�� ������ ��
				sql2 = "select * from user where id=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				rs = pstmt2.executeQuery();
				if(rs.next()) {
					returns = "userAlreadExist";
				}
				else {
					sql3 = "insert into user (id, password, name) values (?, ?, ?)"; 	//	user ���̺� ȸ������ �ֱ�
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.executeUpdate();	//	db�� ������ �Է�		
					returns = "userAddSuccess";
				}
			}
			else {	//	�ش� ȸ�������� �������� ���� ��
				returns = "IDnotExist";
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
	
	public String userModify(String before_name, String before_id ,String after_name, String after_id) {	//	ȸ�� ���� ���� - ������ ���� ���� id�� name�� �޾ƿ;���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "update user set id=?, name=? where id=? and name=?"; // user ���̺� id�� name�� ��ġ�ϴ� ȸ�������� ������Ʈ��
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, after_id);
			pstmt.setString(2, after_name);
			pstmt.setString(3, before_id);
			pstmt.setString(4, before_name);			
			pstmt.executeUpdate(); // db�� ������ �Է�
			returns = "userModified";
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
	
	public String userDelete(String name, String id) {	//	ȸ�� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "delete from user where id=? and name=?"; // user ���̺� id�� name�� �ش�Ǵ� ���ڵ� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.executeUpdate(); // db�� ������ �Է�
			returns = "userDeleted";
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
