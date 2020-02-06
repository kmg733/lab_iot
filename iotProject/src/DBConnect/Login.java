package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Login {	//	�α��� ȸ������ ����
	private static Login instance = new Login();
		
	public static Login getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();
	private Connection conn;
	private String sql = ""; 
	private String sql2 = "";
	private String sql3 = "";
	private PreparedStatement pstmt;		
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private ResultSet rs;
	private ResultSet rs2;
	private String returns;
	private Random rn;	//	��й�ȣ�� ��߱� ������ �������� ���� �����Լ�;
	private int tempPW;	
		
	public String adminLogin(String name, String id, String pwd) { //	������ �α���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from user where id=? and password=? and name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);	//	id�� admin id �ֱ�
			pstmt.setString(2, pwd);	// pwd�� admin pwd �ֱ�
			pstmt.setString(3, name);	//	name�� admin name �ֱ�
			rs = pstmt.executeQuery();
			if (rs.next()) {	//	getString(�ش� ���̺� �÷��� �ʵ尪).equals(�޼ҵ� ����)

				if (rs.getString("id").equals(id) && rs.getString("password").equals(pwd) && rs.getString("name").equals(name)) {	//	user ���̺� ������ name, id, pwd�� �ִ°�?
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
			rs = pstmt.executeQuery();
			if (rs.next()) {	//	�Է��� id, pwd, name�� ��ġ�ϴ� ȸ���� ������ ��
				//	������ ���� �ѹ��� �˻�
				 if ((rs.getString("id").equals(id) && rs.getString("password").equals(pwd)) 
						 && rs.getString("name").equals(name)) {// �Ϲ� �α��� ����
					returns = "loginSuccess";	
				}
				else {	//	�α��� ����
					returns = "loginFailed";	
				} 
			}
			else {	//	�α��� ����
				returns = "loginFailed"; // user ���̺� ������ ����� ��
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
			sql = "select * from add_user where id=? and name=?";	//	��������
			pstmt = conn.prepareStatement(sql);	//	db�� �����ϱ� ���� ����(sql����)�� ����
			pstmt.setString(1, id);	
			pstmt.setString(2, name);	
			rs = pstmt.executeQuery();
			if(rs.next()){	//	ȸ������ ������� ��				
				sql2 = "select * from user where id=? and password=? and name=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.setString(3, name);
				rs2 = pstmt2.executeQuery();	
				if(rs2.next()) {	//	�̹� ȸ�������� �Ǿ� ���� ��
					returns = "acountAleadyExist";										
				}
				else {	//	ȸ�������� ���� ��
					sql3 = "insert into user (id, password, name) values (?, ?, ?)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.executeUpdate();	
					returns = "accountCreated";
				}
			}			
			else { 	//	ȸ������ ����ڰ� �ƴҶ�
				returns = "notMember";
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String changePW(String id ,String pw) {	//	�������������� ��й�ȣ ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	id�� user���̺� ������ ��
				sql2 = "update user set password=? where id=?";	//	��������
				pstmt2 = conn.prepareStatement(sql2);	//	db�� �����ϱ� ���� ����(sql����)�� ����
				pstmt2.setString(1, pw);	
				pstmt2.setString(2, id);	
				pstmt2.executeUpdate();	//	db�� ������ �Է�	
				returns = "pwChangeSuccess";
			}
			else {	//	id�� user���̺� �������� ���� ��
				returns = "pwChangeFailed";
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "error";
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	//�ֹι�ȣ�� �����ϴ� ���̺��� ���� <- ��ȭ��ȣ�� ��ü��, ��߱��� �ƴ϶� pw�� �����ִ� �������� ������ << Ȯ�θ��� �ݿ糯���� Ȯ���ϱ�
	public String findPW(String name, String id, String phoneNum) {	//	��й�ȣ ��߱�
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
	
	
	
	
}
