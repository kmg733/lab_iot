package DBConnect;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ImageUpload { // �̹��� ���� ���ε�
	private static ImageUpload instance = new ImageUpload();
	
	public static ImageUpload getInstance( ) {
		return instance;
	}

	private DBConnector dbc = new DBConnector(); // DBConnector ��ü����
	private Connection conn;
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	private String returns;
	private int result;

	private int max = 1024 * 1024 * 10; // ����ũ�� ���� - 10MB
	private String originFile = ""; // ���ε��� ���� ���ϸ�
	private HttpServletRequest req;
	 
	// �̹��� ���� ���� - https://aristatait.tistory.com/16?category=672398,
	// https://hks003.tistory.com/11

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}
	
	public String orgShow(int number) {	//	������ ������ �̹��� �ҷ�����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from organization where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	���ε��� �̹��� ������ ������ ��
				returns = rs.getString("organization_image");
			} 
			else {	//	���ε��� �̹��� ������ �������� ���� ��
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	public String orgUpload(int number, String savePath) { // ������ ������ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			MultipartRequest multi = new MultipartRequest(req, savePath, max, "utf-8"); // savePath ��ο� ���� ����
																						//������ ���ϸ��� ���� ��� ����� �Ѵ�
			
			Enumeration files = multi.getFileNames();
			String str = (String) files.nextElement();
			originFile = multi.getOriginalFileName(str); // ������ ���� �̸�
			String filePath = savePath+originFile;
			
			sql = "select * from organization where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();

			if (rs.next()) { // �̹���(���)�� ���� ��
				sql2 = "update organization set organization_image=? where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate();
			} else { // �̹���(���)�� ���� ��
				sql2 = "insert into organization (number, organizaion_image) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setString(2, filePath);
				pstmt2.executeUpdate();
			}
			returns = "orgUploaded";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String orgDelete(int number) { // ������ ������ �̹��� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from organization where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sql2 = "delete from organization where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery();
				returns = "orgDeleted";
			} else {
				returns = "orgNotExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	
	public String strShow(int number) {	//	������ ������ �̹��� �ҷ�����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from structure where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	���ε��� �̹��� ������ ������ ��
				returns = rs.getString("structure_image");
			} 
			else {	//	���ε��� �̹��� ������ �������� ���� ��
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	
	public String strUpload(int number, String savePath) { // ������ ������ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			MultipartRequest multi = new MultipartRequest(req, savePath, max, "utf-8"); // savePath ��ο� ���� ����
																						//������ ���ϸ��� ���� ��� ����� �Ѵ�
			
			Enumeration files = multi.getFileNames();
			String str = (String) files.nextElement();
			originFile = multi.getOriginalFileName(str); // ������ ���� �̸�
			String filePath = savePath+originFile;
			
			sql = "select * from structure where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();

			if (rs.next()) { // �̹���(���)�� ���� ��
				sql2 = "update structure set structure_image=? where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate();
			} else { // �̹���(���)�� ���� ��
				sql2 = "insert into structure (number, structure_image) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setString(2, filePath);
				pstmt2.executeUpdate();
			}
			returns = "strUploaded";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	
	public String strDelete(int number) { // ������ ������ �̹��� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from structure where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sql2 = "delete from structure where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery();
				returns = "strDeleted";
			} else {
				returns = "strNotExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String ipShow(int number) {	//	������ ip �� ����Ű �̹��� �ҷ�����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from ip where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if(rs.next()) {	//	���ε��� �̹��� ������ ������ ��
				returns = rs.getString("ip_image");
			} 
			else {	//	���ε��� �̹��� ������ �������� ���� ��
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}
	
	
	public String ipUpload(int number, String savePath) { // ������ ������ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			MultipartRequest multi = new MultipartRequest(req, savePath, max, "utf-8"); // savePath ��ο� ���� ����
																						//������ ���ϸ��� ���� ��� ����� �Ѵ�
			
			Enumeration files = multi.getFileNames();
			String str = (String) files.nextElement();
			originFile = multi.getOriginalFileName(str); // ������ ���� �̸�
			String filePath = savePath+originFile;
			
			sql = "select * from ip where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();

			if (rs.next()) { // �̹���(���)�� ���� ��
				sql2 = "update ip set ip_image=? where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.setInt(2, number);
				pstmt2.executeUpdate();
			} else { // �̹���(���)�� ���� ��
				sql2 = "insert into ip (number, ip_image) values (?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.setString(2, filePath);
				pstmt2.executeUpdate();
			}
			returns = "ipUploaded";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	
	public String ipDelete(int number) { // ������ ip �� ����Ű �̹��� ����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from ip where number=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sql2 = "delete from ip where number=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, number);
				pstmt2.executeQuery();
				returns = "ipDeleted";
			} else {
				returns = "ipNotExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

}
