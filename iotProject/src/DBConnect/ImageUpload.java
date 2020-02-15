package DBConnect;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;

import com.oreilly.servlet.MultipartRequest;



public class ImageUpload { // 이미지 관련 업로드
	private static ImageUpload instance = new ImageUpload();

	public static ImageUpload getInstance() {
		return instance;
	}

	private DBConnector dbc = new DBConnector(); // DBConnector 객체생성
	private Connection conn;
	private String sql = "";
	private String sql2 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private ResultSet rs;
	private String returns;

	private int max = 1024 * 1024 * 10; // 파일크기 제한 - 10MB
	private String originFile = ""; // 업로드할 실제 파일명

	// 이미지 파일 참고 - https://aristatait.tistory.com/16?category=672398
	// https://hks003.tistory.com/11


	public String orgShow() { // 연구실 조직도 이미지 불러오기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from organization";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 업로드한 이미지 파일이 존재할 때
				returns = rs.getString("organization_image");
			} else { // 업로드한 이미지 파일이 존재하지 않을 때
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	public String orgUpload(String name, String savePath, String imgFile) { // 연구실 조직도 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			String filePath = savePath + name;			
			byte decode[] = Base64.decodeBase64(imgFile);
			FileOutputStream fos;
			
			fos = new FileOutputStream(filePath);			
			fos.write(decode);
			fos.close();
			
			sql = "select * from organization where organization_image=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filePath);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 경로에 이미지가 있을 때
				sql2 = "update organization set organization_image=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.executeUpdate();
			} else { // 경로에 이미지가 없을 때
				sql2 = "insert into organization (organizaion_image) values (?)";
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

	public String strShow() { // 연구실 구성도 이미지 불러오기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from structure";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 업로드한 이미지 파일이 존재할 때
				returns = rs.getString("structure_image");
			} else { // 업로드한 이미지 파일이 존재하지 않을 때
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	public String strUpload(String name, String savePath, String imgFile) { // 연구실 구성도 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			String filePath = savePath + name;			
			byte decode[] = Base64.decodeBase64(imgFile);
			FileOutputStream fos;
			
			fos = new FileOutputStream(filePath);			
			fos.write(decode);
			fos.close();

			sql = "select * from structure where structure_image=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filePath);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 이미지(경로)가 있을 때
				sql2 = "update structure set structure_image=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.executeUpdate();
			} else { // 이미지(경로)가 없을 때
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

	public String ipShow() { // 연구실 ip 및 출입키 이미지 불러오기
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from ip";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 업로드한 이미지 파일이 존재할 때
				returns = rs.getString("ip_image");
			} else { // 업로드한 이미지 파일이 존재하지 않을 때
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}

	public String ipUpload(String name, String savePath, String imgFile) { // 연구실 구성도 이미지 업로드
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());

			String filePath = savePath + name;			
			byte decode[] = Base64.decodeBase64(imgFile);
			FileOutputStream fos;
			
			fos = new FileOutputStream(filePath);			
			fos.write(decode);
			fos.close();
			
			sql = "select * from ip where ip_image=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filePath);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 이미지(경로)가 있을 때
				sql2 = "update ip set ip_image=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, filePath);
				pstmt2.executeUpdate();
			} else { // 이미지(경로)가 없을 때
				sql2 = "insert into ip (ip_image) values ( ?)";
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
