package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rule {	//	��Ģ
private static Rule instance = new Rule();
	
	public static Rule getInstance() {
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
	
	public String ruleShow() {	//	��Ģ ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from rule";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	//	��Ģ�� ������ ��
				returns = rs.getString("save_text") + "-ruleExist";
			}
			else {	//	��Ģ�� �������� ���� ��
				returns = "ruleNotExist";
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
	
	public String ruleAdd(String text) {	//	��Ģ ���� ��� - ������ư �ʿ���� ����� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "select * from rule";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();			
			if(rs.next()) {	//	��Ģ ������ ������ �� - ����
				sql2 = "update rule set save_text=?"; 	//	meetlog �����ͺ��̽��� ���ο� ���� ���
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, text);
				pstmt2.executeUpdate();	//	db�� ������ �Է�
			}
			else {	//	�ش� ��¥�� ȸ�ǳ����� �������� ���� �� - ���λ���
				sql2 = "insert into rule (save_text) values (?)"; 	//	meetlog �����ͺ��̽��� ���ο� ���� ���
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, text);
				pstmt2.executeUpdate();	//	db�� ������ �Է�
			}
			returns = "ruleAdded";
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
