package DBConnect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.util.codec.binary.Base64;

public class ImageUpload { // �̹��� ���� ���ε�
	private static ImageUpload instance = new ImageUpload();

	public static ImageUpload getInstance() {
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

	private int max = 1024 * 1024 * 10; // ����ũ�� ���� - 10MB
	private String originFile = ""; // ���ε��� ���� ���ϸ�

	// �̹��� ���� ���� - https://aristatait.tistory.com/16?category=672398
	// https://hks003.tistory.com/11

	public String orgShow() { // ������ ������ �̹��� �ҷ�����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from organization";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) { // ���ε��� �̹��� ������ ��ΰ� ������ ��
				String filePath = rs.getString("organization_image");
				File imgFile = new File(filePath);
				FileInputStream fis = new FileInputStream(imgFile);

				byte[] encoding = new byte[(int) imgFile.length()];
				fis.read(encoding);
				String encoded = Base64.encodeBase64String(encoding);
				fis.close();

				returns = encoded;
			} else { // ���ε��� �̹��� ������ ��ΰ� �������� ���� ��
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	public String orgUpload(String savePath, String imgFile) { // ������ ������ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			String name = "org.bmp";
			String filePath = savePath + name;

			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);

			byte decoded[] = Base64.decodeBase64(imgFile);
			fos.write(decoded);
			fos.close();

			sql = "select * from organization where organization_image=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filePath);
			rs = pstmt.executeQuery();

			if (rs.next()) { // ��ο� �̹����� ���� ��
				sql2 = "update organization set organization_image=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.executeUpdate();
			} else { // ��ο� �̹����� ���� ��
				sql2 = "insert into organization (organization_image) values (?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
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

	public String strShow() { // ������ ������ �̹��� �ҷ�����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from structure";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) { // ���ε��� �̹��� ������ ��ΰ� ������ ��
				String filePath = rs.getString("structure_image");
				File imgFile = new File(filePath);
				FileInputStream fis = new FileInputStream(imgFile);

				byte[] encoding = new byte[(int) imgFile.length()];
				fis.read(encoding);
				String encoded = Base64.encodeBase64String(encoding);
				fis.close();

				returns = encoded;
			} else { // ���ε��� �̹��� ������ �������� ���� ��
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	public String strUpload(String savePath, String imgFile) { // ������ ������ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			String name = "str";
			String filePath = savePath + name;

			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);

			byte decoded[] = Base64.decodeBase64(imgFile);
			fos.write(decoded);
			fos.close();

			sql = "select * from structure where structure_image=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filePath);
			rs = pstmt.executeQuery();

			if (rs.next()) { // ��ο� �̹����� ���� ��
				sql2 = "update structure set structure_image=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.executeUpdate();
			} else { // ��ο� �̹����� ���� ��
				sql2 = "insert into structure (structure_image) values (?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
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

	public String ipShow() { // ������ ip �� ����Ű �̹��� �ҷ�����
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from ip";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) { // ���ε��� �̹��� ������ ��ΰ� ������ ��
				String filePath = rs.getString("ip_image");
				File imgFile = new File(filePath);
				FileInputStream fis = new FileInputStream(imgFile);

				byte[] encoding = new byte[(int) imgFile.length()];
				fis.read(encoding);
				String encoded = Base64.encodeBase64String(encoding);
				fis.close();

				returns = encoded;
			} else { // ���ε��� �̹��� ������ �������� ���� ��
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	public String ipUpload(String savePath, String imgFile) { // ������ ������ �̹��� ���ε�
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			String name = "ip";
			String filePath = savePath + name;

			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);

			byte decoded[] = Base64.decodeBase64(imgFile);
			fos.write(decoded);
			fos.close();

			sql = "select * from ip where ip_image=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filePath);
			rs = pstmt.executeQuery();

			if (rs.next()) { // ��ο� �̹����� ���� ��
				sql2 = "update ip set ip_image=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.executeUpdate();
			} else { // ��ο� �̹����� ���� ��
				sql2 = "insert into ip (ip_image) values (?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
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
}
