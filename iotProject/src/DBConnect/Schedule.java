package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Schedule {	//	���� ���
	private static Schedule instance = new Schedule();
	
	public static Schedule getInstance() {
		return instance;
	}
	
	private DBConnector dbc = new DBConnector();	//	DBConnector ��ü����
	private Connection conn;    //  connecttion:db�� �����ϰ� ���ִ� ��ü
	private String sql = ""; 		
	private String sql2 = ""; 		
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	private String returns;
	
	public String scheduleList(String date) {	//	���� ���� ��� ��������
		try {			
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = dbc.getConn();
			sql = "select * from calander where save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			
			JSONArray jary = new JSONArray();
			boolean flag = true;
		
			while(rs.next()) {
				JSONObject jobj = new JSONObject();
				jobj.put("save_title", rs.getString("save_title"));
				jary.add(jobj);
				
				flag = false;
			}
			if(flag) {	//	�������� �������� ���� ��
				returns = "scheduleNotExist";
			}
			
			returns = jary.toJSONString();			
					
			
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
			Connection conn = dbc.getConn();
			sql = "select * from calander where save_title=? and save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();			

			if(rs.next()) {	//	�ش� ��¥�� ���� ������ ������ ��
				returns = "scheduleAlreadyExist";
			}
			else {	//	�ش� ��¥�� �ش� ������ ������ �������� ���� ��
				sql2 = "insert into calander (save_title, save_text, save_date) values (?, ?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, title);
				pstmt2.setString(2, text);
				pstmt2.setString(3, date);
				pstmt2.executeUpdate();
				returns = "scheduleAddSuccess";
			
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
	
	public String scheduleShow( String title, String date) {	//	���� �ش��ϴ� ���� ���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = dbc.getConn();
			sql = "select * from calander where save_date=? and save_title=?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(2, title);
			rs = pstmt.executeQuery();	//	db�� ������ �Է�
			
			String get = "";
			if(rs.next()) {
				get = rs.getString("save_date") + "-" + rs.getString("save_title")
				+ "-"+ rs.getString("save_text")+ "-scheduleExist";
				returns = get;
			}
			else {
				returns = "scheduleNotEixst";
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
	
	public String scheduleDelete(String title, String date) {	//	���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = dbc.getConn();
			sql = "select * from calander where save_title=? and save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, date);
		    rs = pstmt.executeQuery();
			if(rs.next()) {	//	������ ������ ��
				sql2 = "delete from calander where save_title=? and save_date=?"; 	//	calander �����ͺ��̽��κ��� title�� �ش�Ǵ� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, title);
				pstmt2.setString(2, date);
				pstmt2.executeUpdate();	//	db�� ������ �Է�
				returns = "scheduleDelete";				
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
		
	public String scheduleModify(String beforeTitle, String afterTitle, String afterText, String date) {	//	���� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			Connection conn = dbc.getConn();
			sql = "select * from calander where save_title=? and save_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, beforeTitle);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	������ ������ ��
				sql2 = "update calander set save_title=?, save_text=? where save_title=? and save_date=?"; 	//	calander �����ͺ��̽��κ��� title�� �ش�Ǵ� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, afterTitle);
				pstmt2.setString(2, afterText);
				pstmt2.setString(3, beforeTitle);
				pstmt2.setString(4, date);
				pstmt2.executeUpdate();	//	db�� ������ �Է�
				returns = "scheduleModify";				
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
