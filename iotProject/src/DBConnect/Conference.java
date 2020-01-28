package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conference {
private static Conference instance = new Conference();
	
	public static Conference getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector ��ü����
	private Connection conn;	//	db�� �����ϰ� ���ִ� ��ü
	private String sql = ""; 	//	����1(MariaDB�� �� ��ɾ�����)
	private PreparedStatement pstmt;		//	db�� sql���� �������ִ� ��ü
	private ResultSet rs;	//	db���� ������ �������� �������� ��ü
	private String returns; //�޼ҵ� ���� ���� ��ȯ
	
	public String cfAdd(String date, String text) {	//	ȸ�� ���� ���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "insert into meetlog (save_date, save_text) values (?, ?)"; 	//	meetlog �����ͺ��̽��� ���ο� ���� ���
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(1, text);
			pstmt.executeUpdate();	//	db�� ������ �Է�
			returns = "cfAdd";
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String cfModify(String date, String text) {	//	ȸ�Ƿ� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "update meetlog set save_text=? where save_date=?"; 	//	meetlog �����ͺ��̽��κ��� save_date�� date�� text�÷��� ������ ������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, text);
			pstmt.setString(2, date);
			pstmt.executeUpdate();	//	db�� ������ �Է�
			returns = "cfModify";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
	
	
	public String cfShow(String date) {	//	ȸ�Ƿ� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "select * from meetlog where svae_date=?"; 	//	calander �����ͺ��̽��κ��� title�� �ش�Ǵ� ���� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			if(rs.next()) {	//	date�� �ش�Ǵ� ������ ȸ�Ƿ��� ������ ��
				returns = rs.getString("save_text");
			}
			else {	//	date�� �ش�Ǵ� ������ ȸ�Ƿ��� �������� ���� ��
				returns = "cfNotExist";
			} 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
}
