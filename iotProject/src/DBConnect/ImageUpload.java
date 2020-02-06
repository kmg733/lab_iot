package DBConnect;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageUpload {	//	�̹��� ���� ���ε�
	private static ImageUpload instance = new ImageUpload();
	
	public static ImageUpload getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector ��ü����
	private Connection conn;
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	private String returns;
	private int result;
	
	//�̹��� ���� ���� - https://aristatait.tistory.com/16?category=672398, https://hks003.tistory.com/11
	public String orgUpload(int number, String fileName) {	//	������ ������ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			File imgFile = new File(fileName);
			FileInputStream fin = new FileInputStream(imgFile);
			sql = "select * from organization where number=?"; // organization ���̺��� �̹��� ���ڿ� �����̿� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	//	�̹����� ���� ��
				sql2 = "update organization set organization_image=? where number=?"; // organization ���̺��� �̹��� ������Ʈ
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setBinaryStream(1, fin, (int)imgFile.length());	//	������ ���̽��� �̹��� ���� ���ε�
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate(); // db�� ������ �Է�						
				returns = "orgModified";
			}
			else {	//	�̹����� ���� ��
				sql2 = "insert into organization (number, organizaion_image) values (?, ?)"; // organization ���̺��� �̹��� �ֱ�
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setBinaryStream(2, fin, (int)imgFile.length());	//	������ ���̽��� �̹��� ���� ���ε�
				pstmt2.executeUpdate(); // db�� ������ �Է�				
				returns = "orgUploaded";
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

	public String orgDelete(int number) {	//	������ ������ �̹��� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from organization where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				sql2 = "delete from organization where number=?"; // organization���̺��� number�� �ش�Ǵ� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery(); // db�� ������ �Է�
				returns = "orgDeleted";				
			}
			else {
				returns = "orgNotExist";
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
	
	public String strUpload(int number, String fileName) {	//	������ ������ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			File imgFile = new File(fileName);
			FileInputStream fin = new FileInputStream(imgFile);
			sql = "select * from structure where number=?"; // structure ���̺��� �̹��� ���ڿ� �����̿� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	�̹����� ���� ��
				sql2 = "update structure set structure_image=? where number=?"; // structure ���̺��� �̹��� ������Ʈ
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setBinaryStream(1, fin, (int)imgFile.length());	//	������ ���̽��� �̹��� ���� ���ε�
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate(); // db�� ������ �Է�
				returns = "strModified";
			}
			else {	//	�̹����� ���� ��
				sql2 = "insert into structure (number, structure_image) values (?, ?)"; // structure ���̺��� �̹��� �ֱ�
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setBinaryStream(2, fin, (int)imgFile.length());	//	������ ���̽��� �̹��� ���� ���ε�
				pstmt2.executeUpdate(); // db�� ������ �Է�				
				returns = "strUploaded";
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

	public String strDelete(int number) {	//	������ ������ �̹��� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from structure where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				sql2 = "delete from structure where number=?"; // organization���̺��� number�� �ش�Ǵ� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery(); // db�� ������ �Է�
				returns = "strDeleted";				
			}
			else {
				returns = "strNotExist";
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
	
	public String ipUpload(int number, String fileName) {	//	������ ip �� ����Ű �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			File imgFile = new File(fileName);
			FileInputStream fin = new FileInputStream(imgFile);
			sql = "select * from ip where number=?"; // ip ���̺��� �̹��� ���ڿ� �����̿� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	�̹����� ���� ��
				sql2 = "update ip set ip_image=? where number=?"; // ip ���̺��� �̹��� ������Ʈ
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setBinaryStream(1, fin, (int)imgFile.length());	//	������ ���̽��� �̹��� ���� ���ε�
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate(); // db�� ������ �Է�		
				returns = "ipModified";
			}
			else {	//	�̹����� ���� ��
				sql2 = "insert into ip (number, ip_image) values (?, ?)"; // ip ���̺��� �̹��� �ֱ�
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setBinaryStream(2, fin, (int)imgFile.length());	//	������ ���̽��� �̹��� ���� ���ε�
				pstmt2.executeUpdate(); // db�� ������ �Է�	
				returns = "ipUploaded";
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

	public String ipDelete(int number) {	//	������ ip �� ����Ű �̹��� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from ip where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				sql2 = "delete from ip where number=?"; // organization���̺��� number�� �ش�Ǵ� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery(); // db�� ������ �Է�
				returns = "ipDeleted";				
			}
			else {
				returns = "ipNotExist";
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

	public String ruleUpload(int number, String fileName) {	//	������ ��Ģ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			File imgFile = new File(fileName);
			FileInputStream fin = new FileInputStream(imgFile);
			sql = "select * from rule where number=?"; // rule ���̺��� �̹��� ���ڿ� �����̿� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	�̹����� ���� ��
				sql2 = "update rule set role_image=? where number=?"; // rule ���̺��� �̹��� ������Ʈ
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setBinaryStream(1, fin, (int)imgFile.length());	//	������ ���̽��� �̹��� ���� ���ε�
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate(); // db�� ������ �Է�	
				returns = "ruleModified";
			}
			else {	//	�̹����� ���� ��
				sql2 = "insert into rule (number, role_image) values (?, ?)"; // rule ���̺��� �̹��� �ֱ�
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setBinaryStream(2, fin, (int)imgFile.length());	//	������ ���̽��� �̹��� ���� ���ε�
				pstmt2.executeUpdate(); // db�� ������ �Է�	
				returns = "ruleUploaded";
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

	public String ruleDelete(int number) {	//	������ ��Ģ �̹��� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from rule where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				sql2 = "delete from rule where number=?"; // organization���̺��� number�� �ش�Ǵ� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery(); // db�� ������ �Է�
				returns = "ruleDeleted";				
			}
			else {
				returns = "ruleNotExist";
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
