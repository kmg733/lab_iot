package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Login {
	private static Login instance = new Login();
		
	public static Login getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();//	DBConnector ��ü����
	private Connection conn;	//	db�� �����ϰ� ���ִ� ��ü
	private String sql = ""; 	//	����1(MariaDB�� �� ��ɾ�����)
	private String sql2 = "";
	private PreparedStatement pstmt;		//	db�� sql���� �������ִ� ��ü
	private PreparedStatement pstmt2;
	private ResultSet rs;	//	db���� ������ �������� �������� ��ü
	private String returns; //�޼ҵ� ���� ���� ��ȯ
	private Random rn;	//	��й�ȣ�� ��߱� ������ �������� ���� �����Լ�;
	private int tempPW;
	
	public String adminLogin(String name, String id, String pwd) { //	������ �α���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=? and password=? and name=?"; //	Admin ������ user���̺� id�� ����ִ��� Ȯ���ϴ� ��������
			pstmt = conn.prepareStatement(sql);	//	db�� �����ϱ� ���� ����(sql����)�� ����
			pstmt.setString(1, id);	//	ù��° ?��  �Ű����� id�Է�
			pstmt.setString(2, pwd);	//	�ι�° ?�� �Ű����� pwd�Է�
			pstmt.setString(3, name);	//	����° ?�� �Ű����� name�Է�
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			if (rs.next()) {	//	getString(�ش� ���̺� �÷��� �ʵ尪).equals(�޼ҵ� ����)
				if (rs.getString("id").equals(id) && rs.getString("pasword").equals(pwd) && rs.getString("name").equals(name)) {	//	user ���̺� ������ name, id, pwd�� �ִ°�?
					returns = "adminSuccess";	// ������ �α��� ����
				}
				else {
					returns = "adminFail"; 	// ������ �α��� ����
				}
			}
			else {
				returns = "tableEmpty";	// user ���̺� ������ ����� ��
			}
		}
		catch (Exception e) {
			returns = "error";
		}
		finally {
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String loginDB(String name, String id, String pwd) {	//	���Ǻο� �α���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=? and password=? and name=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			if (rs.next()) {	//	getString(�ش� ���̺� �÷��� �ʵ尪).equals(�޼ҵ� ����)
				if (rs.getString("id").equals(id) && rs.getString("pasword").equals(pwd) && rs.getString("name").equals(name)) {
					returns = "loginSuccess";	// �Ϲ� �α��� ����
				}
				else {
					returns = "loginFail";	// �Ϲ� �α��� ����
				}
			}
			else {
				returns = "emptyTable"; // user ���̺� ������ ����� ��
			}
		}
		catch (Exception e) {
			returns = "error";
		}
		finally {
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String createAccount(String name, String id, String pwd) {	//	ȸ������
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α���
			sql = "select id from user where id=?";	//	��������
			pstmt = conn.prepareStatement(sql);	//	db�� �����ϱ� ���� ����(sql����)�� ����
			pstmt.setString(1, id);	// ?�� id������ ����
			rs = pstmt.executeQuery();	//	db�� ������ �Է�		
			if(rs.next()){	//	ȸ������ ������� ��
				if(rs.getString("name").equals(name)) {	//	ȸ�������� ���� ��
					returns = "userAleadyExist";
				}
				else {	//	ȸ�������� ���� ��
					sql2 = "insert into user (id, password, name) values (?, ?, ?)";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, pwd);
					pstmt2.setString(3, name);
					pstmt2.executeUpdate();	//	db�� ������ ����
					returns = "userCreatComplete";
				}
			}			
			else { 	//	ȸ������ ����ڰ� �ƴҶ�
				returns = "notMember";
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "userDataNotSaved";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	//�ֹι�ȣ�� �����ϴ� ���̺��� ���� <- ��ȭ��ȣ�� ��ü��, ��߱��� �ƴ϶� pw�� �����ִ� �������� ������
	public String findPW(String id, String name, String phoneNum) {	//	��й�ȣ ��߱�
		try {
			rn = new Random();
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α���
			sql = "select * from member where id=? and name=? and phone=?";	//	��������
			pstmt = conn.prepareStatement(sql);	//	db�� �����ϱ� ���� ����(sql����)�� ����
			pstmt.setString(1, id);		
			pstmt.setString(2, name);		
			pstmt.setString(3, phoneNum);		
			rs = pstmt.executeQuery();	//	db�� ������ �Է�	
			if(rs.next()) {	//	member �����ͺ��̽��� �ش� id, name, phoneNum�� ���� �����Ͱ� ���� ��
				tempPW = rn.nextInt(100);
				sql2 = "update user set password=? where id=?";	//	��������
				pstmt2 = conn.prepareStatement(sql2);	//	db�� �����ϱ� ���� ����(sql2����)�� ����
				pstmt2.setString(1, "security" + tempPW);	//	�ӽú�й�ȣ�� securityXX�� ����
				pstmt2.setString(2, id);
				pstmt2.executeUpdate();		//	�������� ������Ʈ��
				returns = rs.getString("password");
			}
			else {	//	member �����ͺ��̽��� �ش� �����Ͱ� ���� ��
				returns = "memberNotExist";
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String modifyPW(String id ,String pw) {	//	�������������� ��й�ȣ ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α���
			sql = "update user set password=? where id=?";	//	��������
			pstmt = conn.prepareStatement(sql);	//	db�� �����ϱ� ���� ����(sql����)�� ����
			pstmt.setString(1, pw);	
			pstmt.setString(2, id);	
			pstmt.executeUpdate();	//	db�� ������ �Է�	
			returns = "modifySuccess";
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "userDataNotFound";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	
}
