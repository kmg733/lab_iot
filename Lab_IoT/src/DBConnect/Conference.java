package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conference {	//	ȸ�Ƿ�
private static Conference instance = new Conference();
	
	public static Conference getInstance() {
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

	
	public String cfShow(String date) {	//	ȸ�Ƿ� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from meetlog where save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	//	date�� �ش�Ǵ� ������ ȸ�Ƿ��� ������ ��
				returns = rs.getString("save_text");
			}
			else {	//	date�� �ش�Ǵ� ������ ȸ�Ƿ��� �������� ���� ��
				returns = "cfNotExist";
			} 
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
	
	public String cfAdd(String date, String text) {	//	ȸ�� ���� ��� - ������ư �ʿ���� ����� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "select * from meetlog where save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	//	�ش� ��¥�� ȸ�ǳ����� ������ �� - ����
				sql2 = "update meetlog set save_text=? where save_date=?"; 	//	meetlog �����ͺ��̽��� ���ο� ���� ���
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, text);
				pstmt2.setString(2, date);
				pstmt2.executeUpdate();	//	db�� ������ �Է�
				returns = "cfModified";	
			}
			else {	//	�ش� ��¥�� ȸ�ǳ����� �������� ���� �� - ���λ���
				sql2 = "insert into meetlog (save_date, save_text) values (?, ?)"; 	//	meetlog �����ͺ��̽��� ���ο� ���� ���
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, date);
				pstmt2.setString(2, text);
				pstmt2.executeUpdate();	//	db�� ������ �Է�
				returns = "cfAdded";
			}
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
	
	
}
