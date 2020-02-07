package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Schedule {	
	private static Schedule instance = new Schedule();
	
	public static Schedule getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector ��ü����
	private Connection conn;	//	db�� �����ϰ� ���ִ� ��ü
	private String sql = ""; 	//	����1(MariaDB�� �� ��ɾ�����)
	private PreparedStatement pstmt;		//	db�� sql���� �������ִ� ��ü
	private ResultSet rs;	//	db���� ������ �������� �������� ��ü
	private StringBuilder returnb; 
	private String returns;
	
	public String scheduleList() {	//	���� ���� ��� ��������
		try {			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α���
			sql = "select save_title from calander"; 	//	calander �����ͺ��̽��κ���  svae_title �÷����� ������� �����´�
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			returnb = new StringBuilder("");
			while(rs.next()) {
				returnb.append("{save_title:" +rs.getString("save_title") + "}");	//	returns���� json������ ���·� ������
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
		
	public String scheduleAdd(String title, String text, String date) {	//	���� ���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "insert into calander (save_title, save_text, save_date) values (?, ?, ?)"; 	//	calander �����ͺ��̽��κ��� 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, text);
			pstmt.setString(3, date);
			pstmt.executeUpdate();	//	db�� ������ �Է�
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
	
	public String scheduleShow(String title) {	//	���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "select * from calander where svae_title=?"; 	//	calander �����ͺ��̽��κ��� title�� �ش�Ǵ� ���� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			while(rs.next()) {
				returns = "{save_title:"+ rs.getString("svae_title") +",save_text:" + rs.getString("svae_text")+",save_date:"+rs.getString("save_date") +"}"; //	returns���� json������ ���·� ������
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
	
	public String scheduleDelete(String title) {	//	���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());	//	�����ͺ��̽� ������ ���� �α��� 
			sql = "delete from calander where svae_title=?"; 	//	calander �����ͺ��̽��κ��� title�� �ش�Ǵ� ���ڵ� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.executeUpdate();	//	db�� ������ �Է�
			returns = "scheduleDelete";
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
